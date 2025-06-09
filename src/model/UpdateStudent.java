package model;

import dbutil.Dbconn;
import entity.Student;

public class UpdateStudent {
    private Dbconn dbconn = new Dbconn();

    // 更新学生全部信息
    public boolean updateStudent(Student student) {
        String sql = "UPDATE student SET name = '"
                + student.getName() + "', sex = '"
                + student.getSex() + "', age = "
                + student.getAge() + ", grade = '"
                + student.getGrade() + "', score = "
                + student.getScore() + " WHERE id = "
                + student.getId();

        int result = dbconn.executeUpdate(sql);
        return result > 0;
    }

    // 更新学生成绩
    public boolean updateStudentScore(int id, double score) {
        String sql = "UPDATE student SET score = " + score + " WHERE id = " + id;
        int result = dbconn.executeUpdate(sql);
        return result > 0;
    }

    // 更新学生年级
    public boolean updateStudentGrade(int id, String grade) {
        String sql = "UPDATE student SET grade = '" + grade + "' WHERE id = " + id;
        int result = dbconn.executeUpdate(sql);
        return result > 0;
    }

    // 批量更新学生年级
    public boolean batchUpdateGrade(String oldGrade, String newGrade) {
        String sql = "UPDATE student SET grade = '" + newGrade + "' WHERE grade = '" + oldGrade + "'";
        int result = dbconn.executeUpdate(sql);
        return result > 0;
    }

    // 验证更新数据的有效性
    public String validateUpdateData(Student student) {
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            return "姓名不能为空";
        }

        if (student.getSex() == null || (!student.getSex().equals("男") && !student.getSex().equals("女"))) {
            return "性别只能是'男'或'女'";
        }

        if (student.getAge() < 0 || student.getAge() > 150) {
            return "年龄必须在0-150之间";
        }

        if (student.getGrade() == null || student.getGrade().trim().isEmpty()) {
            return "年级不能为空";
        }

        if (student.getScore() < 0 || student.getScore() > 100) {
            return "成绩必须在0-100之间";
        }

        return null; // 返回null表示验证通过
    }

    // 检查更新是否会导致重复数据
    public boolean checkDuplicate(Student student) {
        String sql = "SELECT COUNT(*) as count FROM student WHERE name = '"
                + student.getName() + "' AND age = "
                + student.getAge() + " AND id != "
                + student.getId();

        try {
            java.sql.ResultSet rs = dbconn.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbconn.closeAll();
        }
        return false;
    }
}