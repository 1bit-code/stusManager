<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>

        <head>
            <title>导入结果</title>
            <style>
                .result-container {
                    max-width: 800px;
                    margin: 30px auto;
                    padding: 20px;
                    border: 1px solid #ddd;
                    border-radius: 5px;
                }

                .success {
                    color: #4CAF50;
                    font-weight: bold;
                }

                .errors {
                    margin-top: 20px;
                    padding: 15px;
                    background-color: #f8f8f8;
                    border-left: 4px solid #f44336;
                }

                .error-item {
                    margin: 5px 0;
                    color: #f44336;
                }

                .back-link {
                    display: block;
                    margin-top: 20px;
                    text-align: center;
                }
            </style>
        </head>

        <body>
            <div class="result-container">
                <h1>导入结果</h1>

                <p class="success">成功导入 ${successCount} 条记录</p>

                <c:if test="${not empty errorMessages}">
                    <div class="errors">
                        <h3>错误信息 (共 ${fn:length(errorMessages)} 条):</h3>
                        <c:forEach items="${errorMessages}" var="error">
                            <div class="error-item">${error}</div>
                        </c:forEach>
                    </div>
                </c:if>

                <a href="import.html" class="back-link">继续导入</a>
                <a href="list.html" class="back-link">查看学生列表</a>
            </div>
        </body>

        </html>