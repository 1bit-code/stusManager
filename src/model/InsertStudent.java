package model;

import dbutil.Dbconn;
import entity.Student;

public class InsertStudent {
    private Dbconn dbconn = new Dbconn();

    // 插入学生信息
    public boolean insertStudent(Student student) {
        String sql = "INSERT INTO student (name, sex, age, grade, score) VALUES ('"
                + student.getName() + "', '"
                + student.getSex() + "', "
                + student.getAge() + ", '"
                + student.getGrade() + "', "
                + student.getScore() + ")";

        int result = dbconn.executeUpdate(sql);
        return result > 0;
    }

    // 批量插入学生信息
    public boolean batchInsertStudents(Student[] students) {
        boolean success = true;
        for (Student student : students) {
            String sql = "INSERT INTO student (name, sex, age, grade, score) VALUES ('"
                    + student.getName() + "', '"
                    + student.getSex() + "', "
                    + student.getAge() + ", '"
                    + student.getGrade() + "', "
                    + student.getScore() + ")";

            int result = dbconn.executeUpdate(sql);
            if (result <= 0) {
                success = false;
                break;
            }
        }
        return success;
    }

    // 检查学生是否已存在（根据姓名和年龄）
    public boolean isStudentExist(String name, int age) {
        String sql = "SELECT COUNT(*) as count FROM student WHERE name = '" + name + "' AND age = " + age;
        int count = 0;
        try {
            java.sql.ResultSet rs = dbconn.executeQuery(sql);
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbconn.closeAll();
        }
        return count > 0;
    }

    // 验证学生信息是否有效
    public boolean validateStudent(Student student) {
        // 验证姓名
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            return false;
        }

        // 验证性别
        if (student.getSex() == null || (!student.getSex().equals("男") && !student.getSex().equals("女"))) {
            return false;
        }

        // 验证年龄
        if (student.getAge() < 0 || student.getAge() > 150) {
            return false;
        }

        // 验证成绩
        if (student.getScore() < 0 || student.getScore() > 100) {
            return false;
        }

        return true;
    }
}