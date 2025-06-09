package control;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import entity.Student;
import model.SelectStudent;

public class ShowStudentServlet extends HttpServlet {
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
            // 获取要显示的学生ID
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
                request.setAttribute("error", "未指定要查看的学生ID！");
                request.getRequestDispatcher("/ListStudentServlet.do").forward(request, response);
                return;
            }

            int id = Integer.parseInt(idStr);

            // 创建SelectStudent对象
            SelectStudent selectStudent = new SelectStudent();

            // 检查学生是否存在
            if (!selectStudent.isStudentExist(id)) {
                request.setAttribute("error", "要查看的学生不存在！");
                request.getRequestDispatcher("/ListStudentServlet.do").forward(request, response);
                return;
            }

            // 获取学生信息
            Student student = selectStudent.getStudentById(id);

            if (student != null) {
                // 将学生信息存储到request属性中
                request.setAttribute("student", student);

                // 获取额外的统计信息（如班级平均分等）
                String grade = student.getGrade();
                double studentScore = student.getScore();

                // 计算该生在班级中的排名
                String rankSql = "grade = '" + grade + "' AND score > " + studentScore;
                int higherScoreCount = new model.SearchStudent().searchStudents(rankSql).size();
                int rank = higherScoreCount + 1;

                // 将额外信息存储到request属性中
                request.setAttribute("rank", rank);

                // 转发到详情页面
                request.getRequestDispatcher("/jsp/studentdetail.jsp").forward(request, response);
            } else {
                // 获取学生信息失败
                request.setAttribute("error", "获取学生信息失败！");
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