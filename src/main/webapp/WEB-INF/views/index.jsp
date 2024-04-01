<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Документы</title>
    <!-- Подключение Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Подключение Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif; /* Изменение шрифта на всей странице */
            background-image: url('https://wallbox.ru/resize/1440x900/wallpapers/main2/201727/belyj.jpg'); /* URL изображения с горами */
            background-size: cover; /* Заполнение фона изображением */
            background-position: center; /* Центрирование изображения */
        }
        .container-fluid {
            background-color: rgba(255, 255, 255, 0.8); /* Полупрозрачный белый фон для контента */
            padding: 20px;
            border-radius: 8px; /* Скругление углов контейнера */
            box-shadow: 0 4px 8px rgba(0,0,0,0.1); /* Тень для контейнера */
            margin-top: 20px; /* Отступ сверху */
        }
        h2 {
            color: #333; /* Темно-серый цвет для заголовков */
        }
        .table {
            width: 100%;
            table-layout: fixed;
        }
        .table td, .table th {
            white-space: normal;
            word-wrap: break-word;
        }
        .btn-primary {
            background-color: #007bff; /* Синий цвет кнопки */
        }
        .btn-success {
            background-color: #28a745; /* Зеленый цвет кнопки */
        }
        .btn-danger {
            background-color: #dc3545; /* Красный цвет кнопки */
        }
    </style>
</head>
<body>

<div class="container-fluid">
    <h2>Загрузка файла</h2>
    <form method="POST" action="v1" enctype="multipart/form-data" accept-charset="UTF-8" class="mb-3">
        <div class="form-group">
            <label for="file">File to upload:</label>
            <input type="file" class="form-control-file" id="file" name="file" required>
        </div>
        <div class="form-group">
            <label for="fileName">File name:</label>
            <input type="text" class="form-control" id="fileName" name="fileName" required>
        </div>
        <div class="form-group">
            <label for="author">Author:</label>
            <input type="text" class="form-control" id="author" name="author" required>
        </div>
        <button type="submit" class="btn btn-primary">Upload</button>
    </form>

    <h2>Список документов</h2>
    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Название</th>
            <th scope="col">Дата</th>
            <th scope="col">Автор</th>
            <th scope="col">Ключевые слова</th>
            <th scope="col">Распознанный текст</th>
            <th scope="col">Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="document" items="${documents}">
            <tr>
                <td><c:out value="${document.documentName}"/></td>
                <td>${document.creationDate}</td>
                <td>${document.author}</td>
                <td>${document.keyWords}</td>
                <td>${document.recognizedText}</td>
                <td>
                    <a href="download?id=${document.documentId}" class="btn btn-success btn-sm">Download</a>
                    <a href="delete?id=${document.documentId}" class="btn btn-danger btn-sm">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Подключение Bootstrap JS и зависимостей -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
