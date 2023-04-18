package com.icebornedb.iceborne.kernel;


import java.sql.*;
public class KernelMain {
    private Connection db_conn;
    private static KernelMain INSTANCE;

    private KernelMain() {}

    public ResultSet getTodayClass() throws SQLException {
        int week = MyDate.TODAY.getWeekday();
        System.out.println(week);
        Statement stmt = db_conn.createStatement();

        String sqlSelect = String.format(
                """
                        select course.cname, course.teacher, course_entry.order from course join course_entry
                        where course.cno = course_entry.cno
                        and course_entry.weekno = %d;"""
                , week);
        return stmt.executeQuery(sqlSelect);
    }

    public ResultSet getAllCourses() throws SQLException {
        return db_conn.createStatement().executeQuery("select * from course;");
    }

    public ResultSet getAllCourseEntry() throws SQLException {
        return db_conn.createStatement().executeQuery("select * from course_entry");
    }

    public boolean deleteCourse(int cno) {
        try {
            db_conn.createStatement().executeQuery("delete from course where cno=" + cno + ";");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteCourseEntry(int weekno, int order) {
        try {
            db_conn.createStatement().executeQuery("delete from course_entry " +
                    "where weekno = " + weekno + " and course_entry.order = " + order + ";");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addCourse(int cno, String cname, String teacher, int grade, String type) {
        String sql = String.format("""
                insert into course values
                (%d, '%s', '%s', %d, '%s');
                """, cno, cname, teacher, grade, type);
        try{
            db_conn.createStatement().executeQuery(sql);
            return true;
        }catch (SQLException e) {
            return false;
        }
    }

    public boolean addCourseEntry(int cno, int weekno, int order) {
        String sql = String.format("""
                insert into course_entry values
                (%d, %d, %d);
                """, cno, weekno, order);
        try {
            db_conn.createStatement().executeQuery(sql);
            return true;
        }catch (SQLException e) {
            return false;
        }
    }
    public void exit() {
        try {
            db_conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static KernelMain getInstance() throws ClassNotFoundException, SQLException {
        if(INSTANCE != null) return INSTANCE;

        INSTANCE = new KernelMain();

        //initial connection.
        DBConfig config = ConfigReader.readConfig();
        Class.forName(config.getDriver());
        INSTANCE.db_conn = DriverManager.getConnection(String.format("%s%s?user=%s&password=%s", config.getUrl(), config.getDatabase(), config.getUser(), config.getPassword()));


        return INSTANCE;
    }
}
