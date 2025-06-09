package dbutil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dbconn {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/students?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    // 获取数据库连接
    public Connection getConnection() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动加载失败！");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("数据库连接失败！");
            e.printStackTrace();
        }
        return conn;
    }

    // 关闭数据库连接
    public void closeAll() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("关闭数据库连接失败！");
            e.printStackTrace();
        }
    }

    // 执行查询
    public ResultSet executeQuery(String sql) {
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("执行查询失败！");
            e.printStackTrace();
        }
        return rs;
    }

    // 执行更新（包括插入、更新、删除）
    public int executeUpdate(String sql) {
        int result = 0;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("执行更新失败！");
            e.printStackTrace();
        }
        return result;
    }

    // 测试数据库连接
    public static void main(String[] args) {
        Dbconn dbConn = new Dbconn();
        try {
            Connection conn = dbConn.getConnection();
            System.out.println("数据库连接成功！");

            // 检查students数据库是否存在
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = conn.getMetaData().getCatalogs();
            boolean dbExists = false;
            while (rs.next()) {
                if ("students".equalsIgnoreCase(rs.getString(1))) {
                    dbExists = true;
                    break;
                }
            }
            rs.close();

            if (dbExists) {
                System.out.println("students数据库存在");

                // 检查student表是否存在
                ResultSet tables = meta.getTables(null, null, "student", new String[] { "TABLE" });
                if (tables.next()) {
                    System.out.println("student表存在");

                    // 检查表结构
                    Statement stmt = conn.createStatement();
                    ResultSet descRs = stmt.executeQuery("DESCRIBE student");
                    System.out.println("\nstudent表结构：");
                    while (descRs.next()) {
                        System.out.println(descRs.getString("Field") + "\t" +
                                descRs.getString("Type") + "\t" +
                                descRs.getString("Null") + "\t" +
                                descRs.getString("Key") + "\t" +
                                descRs.getString("Default"));
                    }
                    descRs.close();
                    stmt.close();
                } else {
                    System.out.println("student表不存在");
                }
                tables.close();
            } else {
                System.out.println("students数据库不存在");
            }

            dbConn.closeAll();
        } catch (Exception e) {
            System.out.println("数据库连接测试失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}