<%@ page import="static org.example.util.NamesUtil.ADD_SELLER" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавить продавца</title>
</head>
<body>

<form action="<%=request.getContextPath() + ADD_SELLER%>" method="post">
    Название продавца:
    <input type="text" name="sellerName">
    Адрес продавца:
    <input type="text" name="sellerAddress">
    <input type="submit" value="Подтвердить">
</form>
</body>
</html>