<%@ page import="tanc.model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="tanc.model.Cart" %>
<%@ page import="java.util.List" %>
<%@ page import="tanc.dao.ProductDao" %>
<%@ page import="tanc.connection.DatabaseConnection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    request.setAttribute("decimalFormat", decimalFormat);

    User TryGod = (User) request.getSession().getAttribute("TryGod");
    if(TryGod != null) {
        request.setAttribute("TryGod", TryGod);
    }
    ArrayList<Cart> carts = (ArrayList<Cart>) session.getAttribute("c-list");
    List<Cart> productCart = null;

    if (carts != null) {
        ProductDao productDao = null;
        try {
            productDao = new ProductDao(DatabaseConnection.getConnection());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        productCart = productDao.getCartProducts(carts);
        double total = productDao.getCartTotalPrice(carts);
        request.setAttribute("carts", carts);
        request.setAttribute("total", total);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Cart Page</title>
    <%@ include file="footerAndHeader/header.jsp"%>
    <style type="text/css">
        .table tbody td {
            vertical-align: middle;
        }
        .btn-increment, .btn-decrement {
            box-shadow: none;
            font-size: 25px;
        }
    </style>
</head>
<body>
    <%@ include file="footerAndHeader/navBar.jsp"%>
    <div class="container">
        <div class="d-flex py-3">
            <h4>Total Price: $${(total>0)?decimalFormat.format(total):0}</h4>
            <a class="mx-3 btn btn-primary" href="cart-check-out">Check Out</a>
        </div>
        <table class="table table-loght">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Category</th>
                    <th scope="col">Price</th>
                    <th scope="col">Buy Now</th>
                    <th scope="col">Cancel</th>
                </tr>
            </thead>
            <tbody>
            <%
                if(carts != null) {
                    for (Cart c: productCart) {%>
                        <tr>
                            <td><%=c.getName()%></td>
                            <td><%=c.getCategory()%></td>
                            <td>$<%=decimalFormat.format(c.getPrice())%></td>
                            <td>
                                <form action="order-now" method="post" class="form-inline">
                                    <input type="hidden" name="id" value="<%=c.getId()%>" class="form-input">
                                    <div class="form-group d-flex justify-content-between w-50">
                                        <a class="btn btn-md btn-decrement" href="quantity-incre-decre?action=dec&id=<%=c.getId()%>"><i class="fas fa-minus-square"></i></a>
                                        <input type="text" name="quantity" class="form-control w-50" value="<%=c.getQuantity()%>" readonly>
                                        <a class="btn btn-md btn-increment" href="quantity-incre-decre?action=inc&id=<%=c.getId()%>"><i class="fas fa-plus-square"></i></a>
                                    </div>
                                    <button type="submit" class="btn btn-primary btn-sm">Buy</button>
                                </form>
                            </td>
                            <td><a class="btn btn-md btn-danger" href="remove-item-from-cart?id=<%=c.getId()%>">Remove</a> </td>
                        </tr>
                    <%}
                }
            %>

            </tbody>
        </table>
    </div>

    <br/>
    <%@ include file="footerAndHeader/footer.jsp"%>
</body>
</html>