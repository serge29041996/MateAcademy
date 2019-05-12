<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Admin users page</title>
</head>
<body>
<a href="/admin_page/users">Работа с пользователями</a>
<a href="/admin_page/goods">Работа с товарами</a>
<form action="/admin_page/users" method="post">
    <button type="submit" name="result" value="addUser">Добавить пользователя</button>
    <c:if test="${numberUsers == 0}">
        <h3>Нет пользователей на сайте</h3>
    </c:if>
    <c:if test="${numberUsers > 0}">
        <h3>Пользователи сайта:</h3>
        <table>
            <tr>
                <th>Логин</th>
                <th>Электронная почта</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.login}</td>
                    <td>${user.mail}</td>
                    <td><button type="submit" name="result" value="update_${user.id}">Обновить</button></td>
                    <td><button type="submit" name="result" value="delete_${user.id}">Удалить</button></td>
                    <c:if test="${user.role.value == 'admin'}">
                        <td><button type="submit" name="result" value="lowerRole_${user.id}">Понизить до роли пользователя</button></td>
                    </c:if>
                    <c:if test="${user.role.value == 'user'}">
                        <td><button type="submit" name="result" value="raiseRole_${user.id}">Повысить до роли администратора</button></td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</form>
</body>
</html>
