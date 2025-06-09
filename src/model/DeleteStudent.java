package model;

import dbutil.Dbconn;

public class DeleteStudent {
    private Dbconn dbconn = new Dbconn();

    // 根据ID删除学生
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM student WHERE id = " + id;
        int result = dbconn.executeUpdate(sql);
        return result > 0;
    }

    // 批量删除学生
    public boolean batchDeleteStudents(int[] ids) {
        if (ids == null || ids.length == 0) {
            return false;
        }

        StringBuilder sql = new StringBuilder("DELETE FROM student WHERE id IN (");
        for (int i = 0; i < ids.length; i++) {
            sql.append(ids[i]);
            if (i < ids.length - 1) {
                sql.append(",");
            }
        }
        sql.append(")");

        int result = dbconn.executeUpdate(sql.toString());
        return result > 0;
    }

    // 根据条件删除学生
    public boolean deleteStudentsByCondition(String condition) {
        String sql = "DELETE FROM student WHERE " + condition;
        int result = dbconn.executeUpdate(sql);
        return result > 0;
    }

    // 删除指定年级的所有学生
    public boolean deleteStudentsByGrade(String grade) {
        String sql = "DELETE FROM student WHERE grade = '" + grade + "'";
        int result = dbconn.executeUpdate(sql);
        return result > 0;
    }

    // 删除前检查是否存在
    public boolean checkStudentExists(int id) {
        String sql = "SELECT COUNT(*) as count FROM student WHERE id = " + id;
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

    // 清空学生表
    public boolean clearAllStudents() {
        String sql = "DELETE FROM student";
        int result = dbconn.executeUpdate(sql);
        return result >= 0; // 返回true即使没有记录被删除
    }

    // 获取删除操作影响的行数
    public int getAffectedRows(String condition) {
        String sql = "SELECT COUNT(*) as count FROM student WHERE " + condition;
        try {
            java.sql.ResultSet rs = dbconn.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbconn.closeAll();
        }
        return 0;
    }
}
