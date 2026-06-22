<%@ page import="java.util.List" %>
<%@ page import="static org.example.util.NamesUtil.SHOW_ORDER_POINT_ORDER_PAGE" %>
<%@ page import="static org.example.util.NamesUtil.*" %>
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
    <title>Готовые заказы на пункте выдачи</title>

</head>
<body>
<h1>Готовые заказы на пункте выдачи</h1>

<%
    List<UserOrderDTO> userOrderDTOList = (List<UserOrderDTO>) request.getAttribute("userOrderDTOList");
    if (userOrderDTOList != null && !userOrderDTOList.isEmpty()) {
%>

<div>Ваш пункт выдачи: <%=userOrderDTOList.get(0).getOrderPoint()%>
</div>
<%for (UserOrderDTO userOrderDTO : userOrderDTOList) {%>
<br>
<div>
    <a href="<%=request.getContextPath() + SHOW_ORDER_POINT_ORDER_PAGE%>?userOrderId=<%=userOrderDTO.getOrderId()%>">Номер
        заказа: <%=userOrderDTO.getOrderId()%>
    </a>
</div>
<br>
<table border="2">
    <tr>
        <th>Название товара</th>
        <th>Цена товара</th>
        <th>Количество товара</th>
    </tr>
    <%for (UserOrderProductDTO userOrderProductDTO : userOrderDTO.getUserOrderProducts()) {%>
    <tr>
        <td> <a href="<%=request.getContextPath() + PRODUCT_PAGE+"?productId="+ userOrderProductDTO.getProductDTO().getId()%>"><%= userOrderProductDTO.getProductDTO().getProductName() %><a/>
        </td>
        <td><%=userOrderProductDTO.getProductDTO().getPrice()%>
        </td>
        <td><%=userOrderProductDTO.getProductCount()%>
        </td>
        <%}%>
    </tr>
</table>
<div>Сумма заказа: <%=userOrderDTO.getOrderSum()%>
</div>
<%
    }
} else {
%>
Нет активных заказов
<%}%>

</body>
</html>
