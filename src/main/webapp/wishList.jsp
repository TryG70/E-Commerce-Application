<%@ page import="tanc.model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="tanc.model.Cart" %>
<%@ page import="java.util.List" %>
<%@ page import="tanc.dao.ProductDao" %>
<%@ page import="tanc.connection.DatabaseConnection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="tanc.model.Product" %>
<%@ page import="tanc.dao.UserDao" %>
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

//    if (carts != null) {
//        ProductDao productDao = null;
//        try {
//            productDao = new ProductDao(DatabaseConnection.getConnection());
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        productCart = productDao.getCartProducts(carts);
//        double total = productDao.getCartTotalPrice(carts);
//        request.setAttribute("carts", carts);
//        request.setAttribute("total", total);
//    }
    UserDao userDao = new UserDao(DatabaseConnection.getConnection());
    ArrayList<Product> wishList = (ArrayList<Product>) userDao.getWishlists(TryGod.getId());
    request.setAttribute("wishlist", wishList);
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
<%--    <div class="d-flex py-3">--%>
<%--        <h4>Total Price: $${(total>0)?decimalFormat.format(total):0}</h4>--%>
<%--        <a class="mx-3 btn btn-primary" href="add-to cart">Add to WishList</a>--%>
<%--    </div>--%>
    <table class="table table-loght">
        <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Category</th>
            <th scope="col">Price</th>
            <th scope="col">Cancel</th>
        </tr>
        </thead>
        <tbody>
        <%

            for (Product product : wishList) {%>
                <tr>
                    <td><%=product.getName()%></td>
                    <td><%=product.getCategory()%></td>
                    <td>$<%=decimalFormat.format(product.getPrice())%></td>
                    <td>
                        <form action="RemoveFromWishList" method="post" class="form-inline">
                            <input type="hidden" name="product_id" value="<%=product.getId()%>" class="form-input">
                            <input type="hidden" name="user_id" value="<%=((User) request.getSession().getAttribute("TryGod")).getId()%>" class="form-input">

                            <button type="submit" class="btn btn-primary btn-sm">Cancel</button>
                        </form>
                    </td>
                </tr>
        <%}
        %>

        </tbody>
    </table>
</div>

<br/>
<%@ include file="footerAndHeader/footer.jsp"%>
</body>
</html>
