<%@ page import="java.util.List" %>
<%@ page import="org.example.dto.dto.ProductCategoryDTO" %>
<%@ page import="org.example.dto.dto.SellerDTO" %>
<%@ page import="static org.example.util.NamesUtil.PRODUCT_CATEGORY_ID_PARAMETER" %>
<%@ page import="static org.example.util.NamesUtil.MAIN_PAGE_ADMINISTRATOR" %>
<%@ page import="static org.example.util.NamesUtil.*" %>
<%@ page import="org.example.dto.dto.ProductDTO" %>
<%@ page import="org.example.dto.dto.ProductDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%

        ProductDTO productDTO = (ProductDTO) request.getAttribute("productDTO");

        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
    %>
    <form action="<%=request.getContextPath() + MAIN_PAGE_ADMINISTRATOR%>" method="post">
        <input type="submit" value="Вернуться в личный кабинет">
    </form>
    <title>Редактировать продукт</title>
    <h1>Редактировать продукт</h1>
    <form action="<%=request.getContextPath() + EDIT_CATALOG%>" method="post">
        <input type="hidden" name="currentPage" value="<%=currentPage%>">
        <input type="submit" value="Назад">
    </form>
    <form action="<%=request.getContextPath() + UPDATE_PRODUCT%>" method="post">
        Номер продукта:
        <%=productDTO.getId()%>
        <input type="hidden" name="productId" value="<%=productDTO.getId()%>">
        <div>
            Название продукта:
            <input type="text" name="productName" value="<%=productDTO.getProductName()%>">
        </div>

        <div>
            Категория продукта:
            <select name="<%=PRODUCT_CATEGORY_ID_PARAMETER%>">
                <option value="<%=productDTO.getProductCategory().getId()%>" selected>
                    <%=productDTO.getProductCategory().getCategory()%>
                </option>
                <%
                    List<ProductCategoryDTO> list = (List<ProductCategoryDTO>) request.getAttribute("productCategoryDTOList");
                    for (ProductCategoryDTO productCategoryDTO : list) {
                        if (!productCategoryDTO.getId().equals(productDTO.getProductCategory().getId())) {
                %>
                <option value="<%=productCategoryDTO.getId()%>"><%=productCategoryDTO.getCategory()%>
                </option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        <div>
            Цена:
            <input name="price" value="<%=productDTO.getPrice()%>">
        </div>

        <div>
            Продавец:
            <select name="seller">
                <option value="<%=productDTO.getSeller().getId()%>"
                        selected><%=productDTO.getSeller().getSellerName()%>
                </option>
                <%
                    List<SellerDTO> listSeller = (List<SellerDTO>) request.getAttribute("sellerDTOList");
                    for (SellerDTO sellerDTO : listSeller) {
                        if (!productDTO.getSeller().getId().equals(sellerDTO.getId())) {
                %>
                <option value="<%=sellerDTO.getId()%>"><%=sellerDTO.getSellerName()%>
                </option>
                <%
                        }
                    } %>

            </select>
        </div>
        <input type="submit" value="Подтвердить">
    </form>
</head>
<body>
</body>
</html>
