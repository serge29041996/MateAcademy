<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <meta charset="utf-8" content="text/html">
  <title>Sign in page</title>
</head>
<body>
  <h3>Введите данные для авторизации:</h3>
  <form method="post" action="sign_in">
    <label for="login">Логин:</label>
    <input type="text" name="login" id="login" value="<c:out value="${login}" />"/>
    <br>
    <label for="password">Пароль:</label>
    <input type="password" name="password" id="password" value="<c:out value="${password}" />"/>
    <br>
    <button type="submit">Войти</button>
  </form>
  <h3>${result}</h3>
</body>
</html>