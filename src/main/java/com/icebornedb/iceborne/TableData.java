package com.icebornedb.iceborne;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableData {
    private IntegerProperty cno;
    private StringProperty  cname;
    private StringProperty  teacher;
    private IntegerProperty grade;
    private StringProperty  course_type;
    private IntegerProperty weekno;
    private IntegerProperty order;

    // For today`s course.
    public TableData(String cname, String teacher, int order) {
        this.cname   = new SimpleStringProperty(cname);
        this.teacher = new SimpleStringProperty(teacher);
        this.order   = new SimpleIntegerProperty(order);
    }

    // For all the course.
    public TableData(int cno, String cname, String teacher, int grade, String course_type) {
        this.cno         = new SimpleIntegerProperty(cno);
        this.cname       = new SimpleStringProperty(cname);
        this.teacher     = new SimpleStringProperty(teacher);
        this.grade       = new SimpleIntegerProperty(grade);
        this.course_type = new SimpleStringProperty(course_type);
    }

    // For all the course_entry.
    public TableData(int cno, int weekno, int order) {
        this.cno    = new SimpleIntegerProperty(cno);
        this.weekno = new SimpleIntegerProperty(weekno);
        this.order  = new SimpleIntegerProperty(order);
    }
    public int getCno() {
        return cno.get();
    }

    public String getCname() {
        return cname.get();
    }

    public String getTeacher() {
        return teacher.get();
    }

    public int getGrade() {
        return grade.get();
    }

    public String getCourse_type() {
        return course_type.get();
    }

    public int getWeekno() {
        return weekno.get();
    }

    public int getOrder() {
        return order.get();
    }

    @Override
    public String toString() {
        String str = "";
        if(cno != null) {
            str += "cno = " + cno.get() + "\n";
        }
        if(cname != null) {
            str += "cname = " + cname.get() + "\n";
        }
        if(teacher != null) {
            str += "teacher = " + teacher.get() + "\n";
        }
        if(grade != null) {
            str += "grade = " + grade.get() + "\n";
        }
        if(course_type != null) {
            str += "course_type = " + course_type.get() + "\n";
        }
        if(weekno != null) {
            str += "weekno = " + order.get() + "\n";
        }
        if(order != null) {
            str += "order = " + order.get() + "\n";
        }
        return str;
    }
}
