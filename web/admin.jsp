<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Admin page</title>
</head>
<body>
<form action="admin_page" method="post">
    <button type="submit" name="result" value="addUser">Добавить пользователя</button>
    <c:if test="${numberUsers == 0}">
        <h3>No users in website</h3>
    </c:if>
    <c:if test="${numberUsers > 0}">
        <h3>Users of website:</h3>
        <table>
            <tr>
                <th>Логин</th>
                <th>Пароль</th>
                <th>Электронная почта</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.login}</td>
                    <td>${user.password}</td>
                    <td>${user.mail}</td>
                    <td><button type="submit" name="result" value="update_${user.id}">Обновить</button></td>
                    <td><button type="submit" name="result" value="delete_${user.id}">Удалить</button></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</form>
</body>
</html>
