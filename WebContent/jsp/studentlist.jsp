<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="java.util.List" %>
        <%@ page import="entity.Student" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>学生列表 - 学生管理系统</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        margin: 20px;
                        background-color: #f5f5f5;
                    }

                    .container {
                        max-width: 1200px;
                        margin: 0 auto;
                        background-color: white;
                        padding: 20px;
                        border-radius: 5px;
                        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                    }

                    h1 {
                        color: #333;
                        text-align: center;
                        margin-bottom: 30px;
                    }

                    .search-box {
                        margin-bottom: 20px;
                        padding: 15px;
                        background-color: #f8f9fa;
                        border-radius: 5px;
                    }

                    .search-box input[type="text"] {
                        padding: 8px;
                        width: 200px;
                        border: 1px solid #ddd;
                        border-radius: 4px;
                    }

                    .search-box button {
                        padding: 8px 15px;
                        background-color: #007bff;
                        color: white;
                        border: none;
                        border-radius: 4px;
                        cursor: pointer;
                    }

                    .search-box button:hover {
                        background-color: #0056b3;
                    }

                    .add-button {
                        display: inline-block;
                        padding: 8px 15px;
                        background-color: #28a745;
                        color: white;
                        text-decoration: none;
                        border-radius: 4px;
                        margin-bottom: 20px;
                    }

                    .add-button:hover {
                        background-color: #218838;
                    }

                    table {
                        width: 100%;
                        border-collapse: collapse;
                        margin-top: 20px;
                    }

                    th,
                    td {
                        padding: 12px;
                        text-align: left;
                        border-bottom: 1px solid #ddd;
                    }

                    th {
                        background-color: #f8f9fa;
                        color: #333;
                    }

                    tr:hover {
                        background-color: #f5f5f5;
                    }

                    .action-links a {
                        margin-right: 10px;
                        text-decoration: none;
                    }

                    .view-link {
                        color: #17a2b8;
                    }

                    .edit-link {
                        color: #ffc107;
                    }

                    .delete-link {
                        color: #dc3545;
                    }

                    .error-message {
                        color: #dc3545;
                        padding: 10px;
                        margin-bottom: 20px;
                        background-color: #f8d7da;
                        border: 1px solid #f5c6cb;
                        border-radius: 4px;
                    }

                    .no-data {
                        text-align: center;
                        padding: 20px;
                        color: #666;
                    }
                </style>
            </head>

            <body>
                <div class="container">
                    <h1>学生管理系统</h1>

                    <!-- 错误信息显示 -->
                    <% if(request.getAttribute("error") !=null) { %>
                        <div class="error-message">
                            <%= request.getAttribute("error") %>
                        </div>
                        <% } %>

                            <!-- 搜索框 -->
                            <div class="search-box">
                                <form action="ListStudentServlet.do" method="post">
                                    <input type="text" name="searchName" placeholder="输入学生姓名搜索"
                                        value="<%= request.getParameter(" searchName") !=null ?
                                        request.getParameter("searchName") : "" %>">
                                    <button type="submit">搜索</button>
                                </form>
                            </div>

                            <!-- 添加学生按钮 -->
                            <a href="InsertStudentServlet.do" class="add-button">添加学生</a>

                            <!-- 学生列表 -->
                            <table>
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>姓名</th>
                                        <th>性别</th>
                                        <th>年龄</th>
                                        <th>年级</th>
                                        <th>成绩</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% List<Student> studentList = (List<Student>)request.getAttribute("studentList");
                                            if(studentList != null && !studentList.isEmpty()) {
                                            for(Student student : studentList) {
                                            %>
                                            <tr>
                                                <td>
                                                    <%= student.getId() %>
                                                </td>
                                                <td>
                                                    <%= student.getName() %>
                                                </td>
                                                <td>
                                                    <%= student.getSex() %>
                                                </td>
                                                <td>
                                                    <%= student.getAge() %>
                                                </td>
                                                <td>
                                                    <%= student.getGrade() %>
                                                </td>
                                                <td>
                                                    <%= student.getScore() %>
                                                </td>
                                                <td class="action-links">
                                                    <a href="ShowStudentServlet.do?id=<%= student.getId() %>"
                                                        class="view-link">查看</a>
                                                    <a href="UpStudentServlet.do?id=<%= student.getId() %>"
                                                        class="edit-link">修改</a>
                                                    <a href="javascript:void(0);"
                                                        onclick="confirmDelete(<%= student.getId() %>)"
                                                        class="delete-link">删除</a>
                                                </td>
                                            </tr>
                                            <% } } else { %>
                                                <tr>
                                                    <td colspan="7" class="no-data">暂无学生信息</td>
                                                </tr>
                                                <% } %>
                                </tbody>
                            </table>
                </div>

                <script>
                    function confirmDelete(id) {
                        if (confirm('确定要删除这个学生吗？此操作不可恢复。')) {
                            window.location.href = 'DeleteStudentServlet.do?id=' + id;
                        }
                    }
                </script>
            </body>

            </html>