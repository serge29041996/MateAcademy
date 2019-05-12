<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Admin goods page</title>
</head>
<body>
<a href="/admin_page/users">Работа с пользователями</a>
<a href="/admin_page/goods">Работа с товарами</a>
<form action="/admin_page/goods" method="post">
    <button type="submit" name="result" value="addGood">Добавить товар</button>
    <c:if test="${numberGoods == 0}">
        <h3>Нет товаров на сайте</h3>
    </c:if>
    <c:if test="${numberGoods > 0}">
        <h3>Пользователи сайта:</h3>
        <table>
            <tr>
                <th>Название</th>
                <th>Описание</th>
                <th>Цена</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="good" items="${goods}">
                <tr>
                    <td>${good.name}</td>
                    <td>${good.description}</td>
                    <td>${good.price}</td>
                    <td><button type="submit" name="result" value="update_${good.id}">Обновить</button></td>
                    <td><button type="submit" name="result" value="delete_${good.id}">Удалить</button></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</form>
</body>
</html>
