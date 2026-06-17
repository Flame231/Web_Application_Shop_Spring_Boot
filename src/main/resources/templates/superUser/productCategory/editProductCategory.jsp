<%@ page import="org.example.model.ProductCategory" %>
<%@ page import="org.example.dao.productCategory.ProductCategoryDAO" %>
<%@ page import="org.example.dao.productCategory.ProductCategoryDAOImpl" %>
<%@ page import="static org.example.util.NamesUtil.MAIN_PAGE_ADMINISTRATOR" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редактировать категорию</title>
</head>

<form action="<%=request.getContextPath() + MAIN_PAGE_ADMINISTRATOR%>" method="post">
    <input type="submit" value="Вернуться в личный кабинет">
</form>
<body>
<%
    ProductCategoryDAO productCategoryDAO = new ProductCategoryDAOImpl();
    Integer productCategoryId = Integer.parseInt(request.getParameter("productCategoryId"));
    ProductCategory productCategory = productCategoryDAO.get(productCategoryId);

%>
<form action="UpdateProductCategory" method="post">
    Номер категории:
    <%=productCategory.getId()%>
    Название категории:
    <input type="text" name="productCategoryName" value="<%=productCategory.getCategory()%>">
    <input type="submit" value="Подтвердить">
    <input type="hidden" name="productCategoryId" value="<%=productCategory.getId()%>">
</form>
</body>
</html>
