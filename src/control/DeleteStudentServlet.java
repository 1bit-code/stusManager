package control;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.DeleteStudent;

public class DeleteStudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置请求和响应的字符编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            // 获取要删除的学生ID
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
                request.setAttribute("error", "未指定要删除的学生ID！");
                request.getRequestDispatcher("/ListStudentServlet.do").forward(request, response);
                return;
            }

            int id = Integer.parseInt(idStr);

            // 创建DeleteStudent对象
            DeleteStudent deleteStudent = new DeleteStudent();

            // 检查学生是否存在
            if (!deleteStudent.checkStudentExists(id)) {
                request.setAttribute("error", "要删除的学生不存在！");
                request.getRequestDispatcher("/ListStudentServlet.do").forward(request, response);
                return;
            }

            // 删除学生信息
            boolean success = deleteStudent.deleteStudent(id);

            if (success) {
                // 删除成功，重定向到学生列表页面
                response.sendRedirect(request.getContextPath() + "/ListStudentServlet.do");
            } else {
                // 删除失败，返回列表页面并显示错误信息
                request.setAttribute("error", "删除学生信息失败！");
                request.getRequestDispatcher("/ListStudentServlet.do").forward(request, response);
            }

        } catch (NumberFormatException e) {
            // 数字格式转换错误
            request.setAttribute("error", "学生ID格式不正确！");
            request.getRequestDispatcher("/ListStudentServlet.do").forward(request, response);
        } catch (Exception e) {
            // 其他错误
            request.setAttribute("error", "系统错误：" + e.getMessage());
            request.getRequestDispatcher("/ListStudentServlet.do").forward(request, response);
        }
    }
}
