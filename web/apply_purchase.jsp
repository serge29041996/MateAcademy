<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8" content="text/html">
    <title>Apply purchase</title>
</head>
<body>
<h3>Введите код для подтверждения покупки:</h3>
<form method="post" action="user_page/apply_purchase">
    <label for="code">Код</label>
    <input type="password" name="code" id="code" value="<c:out value="${code}"/>"/>
    <br>
    <button type="submit">Подтвердить покупку</button>
</form>
<h3>${result}</h3>
</body>
</html>