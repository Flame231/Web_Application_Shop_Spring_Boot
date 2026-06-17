<%@ page import="org.example.dto.dto.ProductDTO" %>
<%@ page import="static org.example.util.NamesUtil.*" %>
<%@ page import="org.example.model.user.Role" %>
<%@ page import="org.example.dto.dto.ProductDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Страница товара</title>
    <%Role role = (Role) request.getSession().getAttribute("userRole");%>
    <form action="<%=request.getContextPath() + "/" + role.name().toLowerCase()+ "/MainPage"%>" method="post">
        <input type="submit" value="Вернуться в личный кабинет">
    </form>


    <form action="<%=request.getContextPath() + SHOW_CATALOG%>" method="post">
        <% if (request.getParameter("currentPage") != null) {
            int currentPage = Integer.parseInt(request.getParameter("currentPage"));%>
        <input type="hidden" name="currentPage" value="<%=currentPage%>">
        <%}%>
        <input type="submit" value="Назад">
    </form>

    <h1>Страница товара</h1>
</head>
<body>
<%
    ProductDTO productDTO = (ProductDTO) request.getAttribute("productDTO");%>
<div>Номер товара: <%=productDTO.getId()%>
</div>
<br>
<div>Название товара: <%=productDTO.getProductName()%>
</div>
<br>
<div>Категория: <%=productDTO.getProductCategory().getCategory()%>
</div>
<br>
<div>Цена: <%=productDTO.getPrice()%>
</div>
<br>
<div>Продавец: <%=productDTO.getSeller().getSellerName()%>, <%=productDTO.getSeller().getSellerAddress()%>
</div>
<br>
<div>Обновлено: <%=productDTO.getUpdateDateTime()%>
</div>
<br>
</body>
</html>
