<%@ page import="java.text.DecimalFormat" %>
<%@ page import="tanc.model.User" %>
<%@ page import="tanc.model.Order" %>
<%@ page import="tanc.dao.OrderDao" %>
<%@ page import="tanc.connection.DatabaseConnection" %>
<%@ page import="java.util.List" %>
<%@ page import="tanc.model.Cart" %>
<%@ page import="java.util.ArrayList" %><%
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        request.setAttribute("decimalFormat", decimalFormat);
        User TryGod = (User) request.getSession().getAttribute("TryGod");
        List<Order> orders = null;
        if (TryGod != null) {
            request.setAttribute("TryGod", TryGod);
            OrderDao orderDao  = new OrderDao(DatabaseConnection.getConnection());
            orders = orderDao.usersOrderList(TryGod.getId());
        }else{
            response.sendRedirect("login.jsp");
        }
        ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
        if (cart_list != null) {
            request.setAttribute("cart_list", cart_list);
        }

%><!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">    <title>E-Commerce Cart</title>
</head>
<body>
<%@include file="/footerAndHeader/navBar.jsp"%><div class="container">
    <div class="card-header my-3">All Orders</div>
    <table class="table table-light">
        <thead>
        <tr>
            <th scope="col">Date</th>
            <th scope="col">Name</th>
            <th scope="col">Category</th>
            <th scope="col">Quantity</th>
            <th scope="col">Price</th>
            <th scope="col">Cancel</th>
        </tr>
        </thead>
        <tbody>
        <%
            if(orders != null){
                for(Order o:orders){%>
                    <tr>
                        <td><%=o.getDate() %></td>
                        <td><%=o.getName() %></td>
                        <td><%=o.getCategory() %></td>
                        <td><%=o.getQuantity()%></td>
                        <td><%=decimalFormat.format(o.getPrice()) %></td>
                        <td><a class="btn btn-sm btn-danger" href="cancel-order?id=<%=o.getOrderId()%>">Cancel Order</a></td>
                    </tr>
                <%}
            }
        %>
        </tbody>
    </table>
</div>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script></body>
</body>
</html>