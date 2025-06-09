package control;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import entity.Student;
import model.InsertStudent;

public class InsertStudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // GET请求转发到添加学生页面
        request.getRequestDispatcher("/jsp/studentinsert.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置请求和响应的字符编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            // 获取表单数据
            String name = request.getParameter("name");
            String sex = request.getParameter("sex");
            int age = Integer.parseInt(request.getParameter("age"));
            String grade = request.getParameter("grade");
            double score = Double.parseDouble(request.getParameter("score"));

            // 创建Student对象
            Student student = new Student();
            student.setName(name);
            student.setSex(sex);
            student.setAge(age);
            student.setGrade(grade);
            student.setScore(score);

            // 创建InsertStudent对象
            InsertStudent insertStudent = new InsertStudent();

            // 验证学生信息
            if (!insertStudent.validateStudent(student)) {
                request.setAttribute("error", "学生信息验证失败，请检查输入数据！");
                request.getRequestDispatcher("/jsp/studentinsert.jsp").forward(request, response);
                return;
            }

            // 检查学生是否已存在
            if (insertStudent.isStudentExist(name, age)) {
                request.setAttribute("error", "该学生信息已存在！");
                request.getRequestDispatcher("/jsp/studentinsert.jsp").forward(request, response);
                return;
            }

            // 插入学生信息
            boolean success = insertStudent.insertStudent(student);

            if (success) {
                // 插入成功，重定向到学生列表页面
                response.sendRedirect(request.getContextPath() + "/ListStudentServlet.do");
            } else {
                // 插入失败，返回添加页面并显示错误信息
                request.setAttribute("error", "添加学生信息失败！");
                request.getRequestDispatcher("/jsp/studentinsert.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            // 数字格式转换错误
            request.setAttribute("error", "年龄或成绩格式不正确！");
            request.getRequestDispatcher("/jsp/studentinsert.jsp").forward(request, response);
        } catch (Exception e) {
            // 其他错误
            request.setAttribute("error", "系统错误：" + e.getMessage());
            request.getRequestDispatcher("/jsp/studentinsert.jsp").forward(request, response);
        }
    }
}