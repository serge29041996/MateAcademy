<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <c:if test="${action == 'add'}">
        <title>Add information about user</title>
    </c:if>
    <c:if test="${action == 'update'}">
        <title>Update information about user</title>
    </c:if>
</head>
<body>
    <c:if test="${action == 'add'}">
        <h3>Введите информацию про пользователя</h3>
    </c:if>
    <c:if test="${action == 'update'}">
        <h3>Обновите информацию про пользователя</h3>
    </c:if>
    <form method="post" action="user_action">
        <label for="login">Логин:</label>
        <input type="text" name="login" id="login" value="<c:out value="${login}" />"/>
        <br>
        <label for="password">Пароль:</label>
        <input type="password" name="password" id="password" value="<c:out value="${password}" />"/>
        <br>
        <c:if test="${action == 'add'}">
            <button type="submit">Создать</button>
        </c:if>
        <c:if test="${action == 'update'}">
            <button type="submit">Обновить</button>
        </c:if>
        <button type="submit" name="result" value="return">Вернутся к списку</button>
    </form>
    <h3>${result}</h3>
</body>
</html>
