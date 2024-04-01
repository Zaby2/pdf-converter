<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>

    <title>Title</title>
</head>
<body>
<form method="POST" action="v1" enctype="multipart/form-data" accept-charset="UTF-8">
    File to upload: <input type="file" name="file" required><br />
    File name: <input type="text" name="fileName" required><br />
    Author: <input type="text" name="author" required><br />
    <input type="submit" value="Upload">
    Press here to upload the file!
</form>
<html>
<head>
    <title>Документы</title>
</head>
<body>
<h2>Список документов</h2>

<table border="1">
    <tr>
        <th>Название</th>
        <th>Дата</th>
        <th>Автор</th>
        <th>Ключевые слова</th>
        <th>Распознанный текст</th>
    </tr>
    <c:forEach var="document" items="${documents}">
        <tr>
            <td><c:out value="${document.documentName}"></c:out></td>
            <td>${document.creationDate}</td>
            <td>${document.author}</td>
            <td>${document.keyWords}</td>
            <td>${document.recognizedText}</td>
            <td>
                <a href="download?id=${document.documentId}">Download</a>

                <a href="delete?id=${document.documentId}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

</body>
</html>