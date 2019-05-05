<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Goods</title>
</head>
<body>
<c:if test="numberGoods > 0">
    <table>
        <td>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th></th>
        </td>
        <c:forEach var="good" items="goods">
            <td>
            <th>${good.name}</th>
            <th>${good.description}</th>
            <th>${good.price}</th>
            <th><a href="user_page/apply_purchase?id=<c:out value="${good.id}"/>">Купить</a></th>
            </td>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
