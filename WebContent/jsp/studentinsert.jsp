<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>添加学生 - 学生管理系统</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                background-color: #f5f5f5;
            }

            .container {
                max-width: 800px;
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

            .form-group {
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
                color: #555;
            }

            input[type="text"],
            input[type="number"],
            select {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-sizing: border-box;
            }

            .radio-group {
                margin-top: 5px;
            }

            .radio-group label {
                display: inline;
                margin-right: 15px;
                font-weight: normal;
            }

            .button-group {
                margin-top: 30px;
                text-align: center;
            }

            .submit-button {
                padding: 10px 20px;
                background-color: #28a745;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
            }

            .submit-button:hover {
                background-color: #218838;
            }

            .cancel-button {
                padding: 10px 20px;
                background-color: #6c757d;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
                margin-left: 10px;
                text-decoration: none;
                display: inline-block;
            }

            .cancel-button:hover {
                background-color: #5a6268;
            }

            .error-message {
                color: #dc3545;
                padding: 10px;
                margin-bottom: 20px;
                background-color: #f8d7da;
                border: 1px solid #f5c6cb;
                border-radius: 4px;
            }
        </style>
    </head>

    <body>
        <div class="container">
            <h1>添加学生</h1>

            <!-- 错误信息显示 -->
            <% if(request.getAttribute("error") !=null) { %>
                <div class="error-message">
                    <%= request.getAttribute("error") %>
                </div>
                <% } %>

                    <form action="InsertStudentServlet.do" method="post" onsubmit="return validateForm()">
                        <div class="form-group">
                            <label for="name">姓名:</label>
                            <input type="text" id="name" name="name" required>
                        </div>

                        <div class="form-group">
                            <label>性别:</label>
                            <div class="radio-group">
                                <input type="radio" id="male" name="sex" value="男" checked>
                                <label for="male">男</label>
                                <input type="radio" id="female" name="sex" value="女">
                                <label for="female">女</label>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="age">年龄:</label>
                            <input type="number" id="age" name="age" min="1" max="120" required>
                        </div>

                        <div class="form-group">
                            <label for="grade">年级:</label>
                            <select id="grade" name="grade" required>
                                <option value="">请选择年级</option>
                                <option value="一年级">一年级</option>
                                <option value="二年级">二年级</option>
                                <option value="三年级">三年级</option>
                                <option value="四年级">四年级</option>
                                <option value="五年级">五年级</option>
                                <option value="六年级">六年级</option>
                                <option value="初一">初一</option>
                                <option value="初二">初二</option>
                                <option value="初三">初三</option>
                                <option value="高一">高一</option>
                                <option value="高二">高二</option>
                                <option value="高三">高三</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="score">成绩:</label>
                            <input type="number" id="score" name="score" min="0" max="100" step="0.1" required>
                        </div>

                        <div class="button-group">
                            <button type="submit" class="submit-button">提交</button>
                            <a href="ListStudentServlet.do" class="cancel-button">取消</a>
                        </div>
                    </form>
        </div>

        <script>
            function validateForm() {
                var name = document.getElementById("name").value;
                var age = document.getElementById("age").value;
                var score = document.getElementById("score").value;
                var grade = document.getElementById("grade").value;

                if (name.trim() === "") {
                    alert("请输入姓名");
                    return false;
                }

                if (isNaN(age) || age < 1 || age > 120) {
                    alert("请输入有效的年龄（1-120）");
                    return false;
                }

                if (grade === "") {
                    alert("请选择年级");
                    return false;
                }

                if (isNaN(score) || score < 0 || score > 100) {
                    alert("请输入有效的成绩（0-100）");
                    return false;
                }

                return true;
            }
        </script>
    </body>

    </html>
