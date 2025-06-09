<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>学生管理系统</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                text-align: center;
                margin-top: 100px;
            }

            h1 {
                color: #333;
            }

            .loading {
                margin: 20px auto;
                width: 70px;
                text-align: center;
            }

            .loading>div {
                width: 18px;
                height: 18px;
                background-color: #333;
                border-radius: 100%;
                display: inline-block;
                animation: sk-bouncedelay 1.4s infinite ease-in-out both;
            }

            .loading .bounce1 {
                animation-delay: -0.32s;
            }

            .loading .bounce2 {
                animation-delay: -0.16s;
            }

            @keyframes sk-bouncedelay {

                0%,
                80%,
                100% {
                    transform: scale(0);
                }

                40% {
                    transform: scale(1.0);
                }
            }
        </style>
    </head>

    <body>
        <h1>学生管理系统</h1>
        <p>正在加载，请稍候...</p>
        <div class="loading">
            <div class="bounce1"></div>
            <div class="bounce2"></div>
            <div class="bounce3"></div>
        </div>

        <script>
            // 页面加载完成后自动跳转到学生列表页面
            window.onload = function () {
                setTimeout(function () {
                    window.location.href = "ListStudentServlet.do";
                }, 1000); // 1秒后跳转
            };
        </script>
    </body>

    </html>