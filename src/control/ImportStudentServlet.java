package control;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class ImportStudentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(5 * 1024 * 1024); // 5MB限制

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // 解析请求内容提取文件数据
            List<FileItem> items = upload.parseRequest(request);
            InputStream fileContent = null;

            // 获取上传的文件
            for (FileItem item : items) {
                if (!item.isFormField() && "excelFile".equals(item.getFieldName())) {
                    fileContent = item.getInputStream();
                    break;
                }
            }

            if (fileContent == null) {
                throw new ServletException("没有上传文件");
            }

            // 1. 解析Excel文件
            Workbook workbook = new XSSFWorkbook(fileContent);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // 跳过标题行
            if (rowIterator.hasNext())
                rowIterator.next();

            // 2. 准备数据库连接
            conn = getConnection();
            conn.setAutoCommit(false); // 开启事务
            String sql = "INSERT INTO students(id, name, age) VALUES(?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            int successCount = 0;
            List<String> errorMessages = new ArrayList<>();

            // 3. 遍历数据行并批量插入
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                try {
                    String id = getCellValue(row.getCell(0));
                    String name = getCellValue(row.getCell(1));
                    String ageStr = getCellValue(row.getCell(2));

                    // 数据验证
                    if (id == null || name == null || ageStr == null) {
                        throw new Exception("第" + (row.getRowNum() + 1) + "行数据不完整");
                    }

                    int age = Integer.parseInt(ageStr);

                    pstmt.setString(1, id);
                    pstmt.setString(2, name);
                    pstmt.setInt(3, age);
                    pstmt.addBatch();
                    successCount++;

                } catch (Exception e) {
                    errorMessages.add("第" + (row.getRowNum() + 1) + "行错误: " + e.getMessage());
                }
            }

            // 执行批量插入
            pstmt.executeBatch();
            conn.commit();

            // 4. 返回结果
            request.setAttribute("successCount", successCount);
            request.setAttribute("errorMessages", errorMessages);
            request.getRequestDispatcher("/importResult.jsp").forward(request, response);

        } catch (Exception e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new ServletException("导入失败: " + e.getMessage(), e);
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection getConnection() throws SQLException {
        // 根据实际配置修改
        String url = "jdbc:mysql://localhost:3306/studentdb";
        String user = "root";
        String password = "password";
        return DriverManager.getConnection(url, user, password);
    }

    private String getCellValue(Cell cell) {
        if (cell == null)
            return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }
}