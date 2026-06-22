<%@ page import="static org.example.util.NamesUtil.MAIN_PAGE_OPERATOR" %>
<%@ page import="static org.example.util.NamesUtil.SHOW_ORDER_POINT_ORDERS" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="static org.example.util.NamesUtil.*" %>
<%@ page import="org.example.model.UserOrder.OrderStatus" %>
<%@ page import="org.example.dto.dto.UserOrderDTO" %>
<%@ page import="org.example.dto.dto.UserOrderProductDTO" %>
<%@ page import="org.example.dto.dto.UserOrderProductDTO" %>
<%@ page import="org.example.dto.dto.UserOrderDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <form action="<%=request.getContextPath() + MAIN_PAGE_OPERATOR%>" method="post">
        <input type="submit" value="Вернуться в личный кабинет">
    </form>
    <form action="<%=request.getContextPath() + SHOW_ORDER_POINT_ORDERS%>" method="post">
        <input type="submit" value="Назад">
    </form>
    <title>Страница заказа</title>

</head>
<body>
<h1>Страница заказа</h1>
<%
    UserOrderDTO userOrderDTO = (UserOrderDTO) request.getAttribute("userOrderDTO");
%>
Номер заказа: <%=userOrderDTO.getOrderId()%>
<br>
Статус заказа: <%=userOrderDTO.getOrderStatus().getDescription()%>
<br>
<table border="2">
    <tr>
        <th width="100">Номер товара</th>
        <th width="100">Название товара</th>
        <th width="100">Цена товара</th>
        <th width="60">Заказано товара</th>
        <%if (userOrderDTO.getOrderStatus().getDescription().equals(OrderStatus.READY.getDescription())) {%>
        <th width="100">Изменить
            количество товара
        </th>
        <%}%>
    </tr>

    <%
        BigDecimal TotalSum = BigDecimal.ZERO;
        BigDecimal actualTotalSum = BigDecimal.ZERO;
        for (UserOrderProductDTO userOrderProductDTO : userOrderDTO.getUserOrderProducts()) {
    %>
    <tr height="30">
        <td><%=userOrderProductDTO.getProductDTO().getId()%>
        </td>
        <td><%=userOrderProductDTO.getProductDTO().getProductName()%>
        </td>
        <td><%=userOrderProductDTO.getProductDTO().getPrice()%>
        </td>
        <td>
            <%=userOrderProductDTO.getProductCount()%>
        </td>
        <%if (userOrderDTO.getOrderStatus() == OrderStatus.READY) {%>
        <td>
            <%=userOrderProductDTO.getActualProductCount()%>
            <form action="<%=request.getContextPath() + CHANGE_ORDER%>" method="post">
                <input type="submit" name="plus" value="+">
                <input type="hidden" name="count" value="1">
                <input type="hidden" name="userOrderId" value="<%=userOrderProductDTO.getUserOrderId()%>">
                <input type="hidden" name="productId" value="<%=userOrderProductDTO.getProductDTO().getId()%>">
            </form>

            <form action="<%=request.getContextPath() + CHANGE_ORDER%>" method="post">
                <input type="submit" name="minus" value="-">
                <input type="hidden" name="count" value="-1">
                <input type="hidden" name="userOrderId" value="<%=userOrderProductDTO.getUserOrderId()%>">
                <input type="hidden" name="productId" value="<%=userOrderProductDTO.getProductDTO().getId()%>">
            </form>
        </td>
        <%
            }
            BigDecimal productSum = userOrderProductDTO.getProductDTO().getPrice()
                    .multiply(new BigDecimal(userOrderProductDTO.getProductCount()));
            TotalSum = TotalSum.add(productSum);
            BigDecimal actualProductSum = userOrderProductDTO.getProductDTO().getPrice()
                    .multiply(new BigDecimal(userOrderProductDTO.getActualProductCount()));
            actualTotalSum = actualTotalSum.add(actualProductSum);
        %>
    </tr>
    <%}%>
</table>
<div>Сумма заказа: <%=TotalSum%>
    <br>
    <%if (userOrderDTO.getOrderStatus() == OrderStatus.CREATED) {%>
    <form action="<%=request.getContextPath() + CHANGE_USER_ORDER_STATUS%>" method="post">
        <input type="hidden" name="userOrderId" value="<%=userOrderDTO.getOrderId()%>">
        <input type="hidden" name="orderStatus" value="READY">
        <input type="submit" value="Заказ готов к выдаче">
    </form>
    <%}%>

    <%if (userOrderDTO.getOrderStatus() == OrderStatus.READY) {%>
    Итоговая сумма заказа: <%=actualTotalSum%>
    <form action="<%=request.getContextPath() + CHANGE_USER_ORDER_STATUS%>" method="post">
        <input type="hidden" name="userOrderId" value="<%=userOrderDTO.getOrderId()%>">
        <input type="hidden" name="orderStatus" value="REFUSED">
        <input type="submit" value="Отказ">
    </form>
    <form action="<%=request.getContextPath() + CHANGE_USER_ORDER_STATUS%>" method="post">
        <input type="hidden" name="userOrderId" value="<%=userOrderDTO.getOrderId()%>">
        <input type="hidden" name="orderStatus" value="CLOSED">
        <input type="submit" value="Закрыть заказ">
    </form>
    <%}%>
</div>
</body>
</html>
