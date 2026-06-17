<%@ page import="java.util.List" %>
<%@ page import="org.example.dto.dto.ProductCategoryDTO" %>
<%@ page import="org.example.dto.dto.SellerDTO" %>
<%@ page import="static org.example.util.NamesUtil.ADD_PRODUCT" %>
<%@ page import="static org.example.util.NamesUtil.MAIN_PAGE_ADMINISTRATOR" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавить товар в каталог</title>
    <form action="<%=request.getContextPath() + MAIN_PAGE_ADMINISTRATOR%>" method="post">
        <input type="submit" value="Вернуться в личный кабинет">
    </form>
    <h1>Добавить товар в каталог</h1>
    <form action="<%=request.getContextPath() + ADD_PRODUCT%>" method="post">
        <div>
            Название продукта:
            <input type="text" name="productName">
        </div>
        <div>
            Категория продукта:
            <select name="productCategoryId">
                <%
                    List<ProductCategoryDTO> list = (List<ProductCategoryDTO>) request.getAttribute("productCategoryDTOList");
                    for (ProductCategoryDTO productCategoryDTO : list) {
                %>
                <option value="<%=productCategoryDTO.getId()%>"><%=productCategoryDTO.getCategory()%>
                </option>
                <%
                    }
                %>
            </select>
        </div>
        <div>
            Цена:
            <input name="price">
        </div>

        <div>
            Продавец:
            <select name="seller">
                <%
                    List<SellerDTO> listSeller = (List<SellerDTO>) request.getAttribute("sellerDTOList");
                    for (SellerDTO sellerDTO : listSeller) {
                %>
                <option value="<%=sellerDTO.getId()%>"><%=sellerDTO.getSellerName()%>
                </option>
                <%
                    }
                %>
            </select>
        </div>
        <input type="submit" value="Подтвердить">
    </form>
</head>
<body>
</body>
</html>
