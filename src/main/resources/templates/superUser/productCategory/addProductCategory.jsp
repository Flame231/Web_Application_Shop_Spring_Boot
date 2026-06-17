<%@ page import="static org.example.util.NamesUtil.ADD_PRODUCT_CATEGORY" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редактировать категорию</title>
</head>
<body>

<form action="<%=request.getContextPath() + ADD_PRODUCT_CATEGORY%>" method="post">
    Название категории:
    <input type="text" name="productCategoryName">
    <input type="submit" value="Подтвердить">
</form>
</body>
</html>
