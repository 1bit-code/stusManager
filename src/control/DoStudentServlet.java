package control;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import entity.Student;
import model.UpdateStudent;

public class DoStudentServlet extends HttpServlet {
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
            // 获取表单数据
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String sex = request.getParameter("sex");
            int age = Integer.parseInt(request.getParameter("age"));
            String grade = request.getParameter("grade");
            double score = Double.parseDouble(request.getParameter("score"));

            // 创建Student对象
            Student student = new Student();
            student.setId(id);
            student.setName(name);
            student.setSex(sex);
            student.setAge(age);
            student.setGrade(grade);
            student.setScore(score);

            // 创建UpdateStudent对象
            UpdateStudent updateStudent = new UpdateStudent();

            // 验证更新数据
            String validationError = updateStudent.validateUpdateData(student);
            if (validationError != null) {
                request.setAttribute("error", validationError);
                request.setAttribute("student", student);
                request.getRequestDispatcher("/jsp/studentupdate.jsp").forward(request, response);
                return;
            }

            // 检查是否会导致重复数据
            if (updateStudent.checkDuplicate(student)) {
                request.setAttribute("error", "修改后的学生信息与其他学生重复！");
                request.setAttribute("student", student);
                request.getRequestDispatcher("/jsp/studentupdate.jsp").forward(request, response);
                return;
            }

            // 更新学生信息
            boolean success = updateStudent.updateStudent(student);

            if (success) {
                // 更新成功，重定向到学生列表页面
                response.sendRedirect(request.getContextPath() + "/ListStudentServlet.do");
            } else {
                // 更新失败，返回修改页面并显示错误信息
                request.setAttribute("error", "更新学生信息失败！");
                request.setAttribute("student", student);
                request.getRequestDispatcher("/jsp/studentupdate.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            // 数字格式转换错误
            request.setAttribute("error", "年龄或成绩格式不正确！");
            // 尝试重新构建student对象以保留用户输入
            Student student = new Student();
            student.setId(Integer.parseInt(request.getParameter("id")));
            student.setName(request.getParameter("name"));
            student.setSex(request.getParameter("sex"));
            student.setGrade(request.getParameter("grade"));
            request.setAttribute("student", student);
            request.getRequestDispatcher("/jsp/studentupdate.jsp").forward(request, response);
        } catch (Exception e) {
            // 其他错误
            request.setAttribute("error", "系统错误：" + e.getMessage());
            request.getRequestDispatcher("/jsp/studentupdate.jsp").forward(request, response);
        }
    }
}