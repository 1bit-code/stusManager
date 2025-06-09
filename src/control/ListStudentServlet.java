package control;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import entity.Student;
import model.SearchStudent;

public class ListStudentServlet extends HttpServlet {
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

        // 获取查询参数
        String searchName = request.getParameter("searchName");

        // 创建SearchStudent对象
        SearchStudent searchStudent = new SearchStudent();
        List<Student> studentList;

        // 根据是否有搜索条件来获取学生列表
        if (searchName != null && !searchName.trim().isEmpty()) {
            studentList = searchStudent.searchStudentsByName(searchName);
        } else {
            studentList = searchStudent.getAllStudents();
        }

        // 将学生列表存储到request属性中
        request.setAttribute("studentList", studentList);

        // 转发到学生列表页面
        request.getRequestDispatcher("/jsp/studentlist.jsp").forward(request, response);
    }
}