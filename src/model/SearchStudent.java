package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbutil.Dbconn;
import entity.Student;

public class SearchStudent {
    private Dbconn dbconn = new Dbconn();

    // 获取所有学生信息
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<Student>();
        String sql = "SELECT * FROM student";
        ResultSet rs = dbconn.executeQuery(sql);

        try {
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setSex(rs.getString("sex"));
                student.setAge(rs.getInt("age"));
                student.setGrade(rs.getString("grade"));
                student.setScore(rs.getDouble("score"));
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbconn.closeAll();
        }

        return studentList;
    }

    // 根据条件查询学生信息
    public List<Student> searchStudents(String condition) {
        List<Student> studentList = new ArrayList<Student>();
        String sql = "SELECT * FROM student WHERE " + condition;
        ResultSet rs = dbconn.executeQuery(sql);

        try {
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setSex(rs.getString("sex"));
                student.setAge(rs.getInt("age"));
                student.setGrade(rs.getString("grade"));
                student.setScore(rs.getDouble("score"));
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbconn.closeAll();
        }

        return studentList;
    }

    // 根据姓名模糊查询
    public List<Student> searchStudentsByName(String name) {
        List<Student> studentList = new ArrayList<Student>();
        String sql = "SELECT * FROM student WHERE name LIKE '%" + name + "%'";
        ResultSet rs = dbconn.executeQuery(sql);

        try {
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setSex(rs.getString("sex"));
                student.setAge(rs.getInt("age"));
                student.setGrade(rs.getString("grade"));
                student.setScore(rs.getDouble("score"));
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbconn.closeAll();
        }

        return studentList;
    }
}