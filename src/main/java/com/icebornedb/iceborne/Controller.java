package com.icebornedb.iceborne;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import static com.icebornedb.iceborne.MainClass.kernel;

public class Controller {
    @FXML
    private TextArea log;
    @FXML
    private CheckBox log_close;
    @FXML
    private TableView<TableData> todayTable;

    private boolean todayActived = false;
    private void addLog(String s) {
        if(!log_close.isSelected()) {
            s = "[Log] " + s;
            log.setText(log.getText() + s + "\n");
        }
    }

    private int displayedType = -1;
    @FXML
    public void onToday() throws SQLException, ClassNotFoundException {
        displayedType = 0;
        todayTable.getColumns().clear();

        ObservableList<TableData> data = FXCollections.observableArrayList();
        TableColumn<TableData, String> classNameCol = new TableColumn<>("课程名");
        TableColumn<TableData, String> teacherCol   = new TableColumn<>("教师");
        TableColumn<TableData, Integer> orderCol    = new TableColumn<>("课次");
        classNameCol.setCellValueFactory(new PropertyValueFactory<>("cname"));
        teacherCol.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        orderCol.setCellValueFactory(new PropertyValueFactory<>("order"));
        todayTable.setEditable(true);
        todayTable.getColumns().addAll(classNameCol, teacherCol, orderCol);
        todayTable.setItems(data);

        ResultSet rs = kernel.getTodayClass();
        while(rs.next()) {
            data.add(new TableData(rs.getString(1), rs.getString(2), rs.getInt(3)));
        }
        addLog("成功查询今日课程安排!");
    }

    @FXML
    public void onShowAllCourse() throws SQLException, ClassNotFoundException {
        displayedType = 1;
        todayTable.getColumns().clear();

        ObservableList<TableData> data = FXCollections.observableArrayList();
        TableColumn<TableData, Integer> cnoCol    = new TableColumn<>("课程编号");
        TableColumn<TableData, String> cnameCol   = new TableColumn<>("课程名");
        TableColumn<TableData, String> teacherCol = new TableColumn<>("授课教师");
        TableColumn<TableData, Integer> gradeCol  = new TableColumn<>("学分");
        TableColumn<TableData, String> courseCol  = new TableColumn<>("课程类型");
        cnoCol.setCellValueFactory(new PropertyValueFactory<>("cno"));
        cnameCol.setCellValueFactory(new PropertyValueFactory<>("cname"));
        teacherCol.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course_type"));
        todayTable.setEditable(true);
        todayTable.getColumns().addAll(cnoCol, cnameCol, teacherCol, gradeCol, courseCol);
        todayTable.setItems(data);

        ResultSet rs = kernel.getAllCourses();
        while(rs.next()) {
            data.add(new TableData(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getString(5))
            );
        }
        addLog("成功查询全部课程!");
    }

    @FXML
    public void onShowAllCourseEntry() throws SQLException, ClassNotFoundException {
        displayedType = 2;
        todayTable.getColumns().clear();

        ObservableList<TableData> data = FXCollections.observableArrayList();
        TableColumn<TableData, Integer> cnoCol = new TableColumn<>("课程编号");
        TableColumn<TableData, Integer> weekno = new TableColumn<>("周次");
        TableColumn<TableData, Integer> order  = new TableColumn<>("课次");
        cnoCol.setCellValueFactory(new PropertyValueFactory<>("cno"));
        weekno.setCellValueFactory(new PropertyValueFactory<>("weekno"));
        order.setCellValueFactory(new PropertyValueFactory<>("order"));
        todayTable.setEditable(true);
        todayTable.getColumns().addAll(cnoCol, weekno, order);
        todayTable.setItems(data);

        ResultSet rs = kernel.getAllCourseEntry();
        while(rs.next()) {
            data.add(new TableData(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getInt(3)
            ));
        }

        addLog("已查询全部课程安排!");
    }

    @FXML
    public void onDelete() throws SQLException, ClassNotFoundException {
        if(displayedType != 1 && displayedType != 2) {
            addLog("当前选定的条目不是课程/课程安排条目, 或未选定条目, 无法执行删除!");
            return;
        }
        TableData a = todayTable.selectionModelProperty().get().getSelectedItem();
        if(displayedType == 1) {
            if(kernel.deleteCourse(a.getCno())) {
                addLog("删除课程成功!");
                onShowAllCourse();
            }else {
                addLog("删除课程失败: 数据库异常");
            }

        }else if(displayedType == 2) {
            if(kernel.deleteCourseEntry(a.getWeekno(), a.getOrder())) {
                addLog("删除课程安排成功!");
                onShowAllCourseEntry();
            }else {
                addLog("删除课程安排失败: 数据库异常");
            }
        }
    }

    @FXML
    private TextField cno;
    @FXML
    private TextField cname;
    @FXML
    private TextField teacher;
    @FXML
    private TextField grade;
    @FXML
    private TextField type;
    @FXML
    public void onAddCourse() throws SQLException, ClassNotFoundException {
        int int_cno, int_grade;
        String int_cname, int_teacher, int_type;
        try {
            int_cno = Integer.parseInt(cno.getText());
        }catch (Exception e) {
            addLog("添加失败: 课程号必须为整数.");
            return;
        }
        try {
            int_grade = Integer.parseInt(grade.getText());
        }catch (Exception e) {
            addLog("添加失败: 学分必须为整数.");
            return;
        }
        int_cname   = cname.getText();
        int_teacher = teacher.getText();
        int_type    = type.getText();
        if(kernel.addCourse(int_cno, int_cname, int_teacher, int_grade, int_type)) {
            addLog("已添加课程!");
            onShowAllCourse();
        }else {
            addLog("添加失败: 数据库异常");
        }
    }
    @FXML
    public void onClearCourseText() {
        cno.clear();
        cname.clear();
        teacher.clear();
        grade.clear();
        type.clear();
    }
    @FXML
    private TextField cno_;
    @FXML
    private TextField weekno;
    @FXML
    private TextField order;
    @FXML
    public void onAddCourseEntry() throws SQLException, ClassNotFoundException {
        int int_cno, int_weekno, int_order;
        try {
            int_cno = Integer.parseInt(cno_.getText());
        } catch (Exception e) {
            addLog("添加失败: 课程号必须为整数.");
            return;
        }
        try {
            int_weekno = Integer.parseInt(weekno.getText());
        } catch (Exception e) {
            addLog("添加失败: 周次必须为整数.");
            return;
        }
        try {
            int_order = Integer.parseInt(order.getText());
        } catch (Exception e) {
            addLog("添加失败: 课次必须为整数.");
            return;
        }
        onShowAllCourseEntry();
        for(TableData d : todayTable.getItems()) {
            if(int_weekno == d.getWeekno() && int_order == d.getOrder()) {
                addLog("添加失败: 课程安排冲突");
                return;
            }
        }
        if(kernel.addCourseEntry(int_cno, int_weekno, int_order)) {
            onShowAllCourseEntry();
            addLog("已添加课程安排!");
        }else {
            addLog("添加失败: 数据库异常");
        }
    }
    @FXML
    public void onClearCourseEntryText() {
        cno_.clear();
        weekno.clear();
        order.clear();
    }

    @FXML
    public void onExit() {
        System.exit(0);
    }

    @FXML
    public void onClear() {
        log.setText("");
    }
}