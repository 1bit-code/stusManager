package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import dbutil.Dbconn;
import entity.Student;

public class SelectStudent {
    private Dbconn dbconn = new Dbconn();

    // 根据ID获取学生信息
    public Student getStudentById(int id) {
        Student student = null;
        String sql = "SELECT * FROM student WHERE id = " + id;
        ResultSet rs = dbconn.executeQuery(sql);

        try {
            if (rs.next()) {
                student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setSex(rs.getString("sex"));
                student.setAge(rs.getInt("age"));
                student.setGrade(rs.getString("grade"));
                student.setScore(rs.getDouble("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbconn.closeAll();
        }

        return student;
    }

    // 根据姓名获取学生信息
    public Student getStudentByName(String name) {
        Student student = null;
        String sql = "SELECT * FROM student WHERE name = '" + name + "'";
        ResultSet rs = dbconn.executeQuery(sql);

        try {
            if (rs.next()) {
                student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setSex(rs.getString("sex"));
                student.setAge(rs.getInt("age"));
                student.setGrade(rs.getString("grade"));
                student.setScore(rs.getDouble("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbconn.closeAll();
        }

        return student;
    }

    // 检查学生ID是否存在
    public boolean isStudentExist(int id) {
        String sql = "SELECT COUNT(*) as count FROM student WHERE id = " + id;
        boolean exists = false;
        ResultSet rs = dbconn.executeQuery(sql);

        try {
            if (rs.next()) {
                exists = rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbconn.closeAll();
        }

        return exists;
    }

    // 获取学生总数
    public int getStudentCount() {
        String sql = "SELECT COUNT(*) as count FROM student";
        int count = 0;
        ResultSet rs = dbconn.executeQuery(sql);

        try {
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbconn.closeAll();
        }

        return count;
    }
}