<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <c:if test="${action == 'add'}">
        <title>Add information about good</title>
    </c:if>
    <c:if test="${action == 'update'}">
        <title>Update information about good</title>
    </c:if>
</head>
<body>
<c:if test="${action == 'add'}">
    <h3>Введите информацию про товар</h3>
</c:if>
<c:if test="${action == 'update'}">
    <h3>Обновите информацию про товар</h3>
</c:if>
<form method="post" action="/admin_page/good_action">
    <label for="name">Название:</label>
    <input type="text" name="name" id="name" value="<c:out value="${name}" />"/>
    <br>
    <label for="description">Описание:</label>
    <textarea name="description" id="description">
        ${description}
    </textarea>
    <br>
    <label for="price">Цена:</label>
    <input type="text" name="price" id="price" value="<c:out value="${price}" />"/>
    <br>
    <c:if test="${action == 'add'}">
        <button type="submit" name="option" value="add">Создать</button>
    </c:if>
    <c:if test="${action == 'update'}">
        <button type="submit" name="option" value="update">Обновить</button>
    </c:if>
    <button type="submit" name="option" value="return">Вернутся к списку товаров</button>
</form>
<h3>${result}</h3>
</body>
</html>
