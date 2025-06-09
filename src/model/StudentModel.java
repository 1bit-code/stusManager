package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbutil.Dbconn;
import entity.Student;

public class StudentModel {
    private Dbconn dbconn = new Dbconn();

    // 获取所有学生信息
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<Student>();
        String sql = "SELECT * FROM student";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbconn.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

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

    // 根据ID获取学生信息
    public Student getStudentById(int id) {
        Student student = null;
        String sql = "SELECT * FROM student WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dbconn.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

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

    // 添加学生信息
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO student (name, sex, age, grade, score) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            conn = dbconn.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getSex());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getGrade());
            pstmt.setDouble(5, student.getScore());

            int result = pstmt.executeUpdate();
            success = result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbconn.closeAll();
        }
        return success;
    }

    // 更新学生信息
    public boolean updateStudent(Student student) {
        String sql = "UPDATE student SET name = ?, sex = ?, age = ?, grade = ?, score = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            conn = dbconn.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getSex());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getGrade());
            pstmt.setDouble(5, student.getScore());
            pstmt.setInt(6, student.getId());

            int result = pstmt.executeUpdate();
            success = result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbconn.closeAll();
        }
        return success;
    }

    // 删除学生信息
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM student WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            conn = dbconn.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int result = pstmt.executeUpdate();
            success = result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbconn.closeAll();
        }
        return success;
    }
}