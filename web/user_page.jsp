<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Goods</title>
</head>
<body>
<a href="user_page/apply_purchase">Оплатить товары в корзине</a>
<c:if test="${numberGoods > 0}">
    <table>
        <td>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th></th>
        </td>
        <c:forEach var="good" items="${goods}">
            <td>
            <th>${good.name}</th>
            <th>${good.description}</th>
            <th>${good.price}</th>
            <th><a href="user_page/add_to_basket?id=<c:out value="${good.id}"/>">Добавить в корзину</a></th>
            </td>
        </c:forEach>
    </table>
</c:if>
<c:if test="${numberGoods == 0}">
    <h3>В данный момент в магазине нет товаров. Приходите позже</h3>
</c:if>
</body>
</html>
