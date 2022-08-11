<%@ page import="tanc.model.User" %>
<%@ page import="tanc.dao.ProductDao" %>
<%@ page import="tanc.connection.DatabaseConnection" %>
<%@ page import="tanc.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="tanc.model.Cart" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    User TryGod = (User) request.getSession().getAttribute("TryGod");
    if(TryGod != null) {
        request.setAttribute("TryGod", TryGod);
    }
    ProductDao productdao;
    try {
        productdao = new ProductDao(DatabaseConnection.getConnection());
    } catch (SQLException | ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
    List<Product> products = productdao.getAllProducts();

    ArrayList<Cart> carts = (ArrayList<Cart>) session.getAttribute("c-list");

    if (carts != null) {
        request.setAttribute("carts", carts);
    }
%>


<!DOCTYPE html>
<html>
<head>
    <title>Welcome to Tanc Shoes</title>
    <%@ include file="footerAndHeader/header.jsp"%>
</head>
<body>
    <%@ include file="footerAndHeader/navBar.jsp"%>
    <div class="container products__body">
        <div class="card-header mt-3">All Products</div>
        <div class="row">
            <%
                if(!(products.isEmpty())) {
                    for (Product p: products) {%>
                        <div class="col-md-4 my-3">
                        <div class="card w-100" style="width: 18rem;">
                            <img style="width: auto; height: 300px" src="product-pictures/<%= p.getImage()%>" class="card-img-top product__image__card" alt="Card image cap">
                            <div class="card-body">
                                <h5 class="card-title"><%=p.getName()%></h5>
                                <h6 class="price">Price: $<%=p.getPrice()%></h6>
                                <h6 class="category">Category: <%=p.getCategory()%></h6>
                                <div class="mt-3 d-flex justify-content-between">
                                    <a href="add-to cart?id=<%=p.getId()%>" class="btn btn-red">Add to Cart</a>
                                    <a href="order-now?quantity=1&id=<%=p.getId()%>" class="btn btn-primary">Buy Now</a>
                                   <form method="POST" action="${pageContext.request.contextPath}/AddToWishlistServlet">
                                        <input type="hidden" value="<%= p.getId() %>" name="productId">
                                        <input type="hidden" value="<%= request.getSession().getAttribute("id") %>" name="userId">
                                       <button type="submit" style="background: transparent; border: none; outline: none; color: #222222" class="btn btn-primary ms-2"><i class="bi bi-heart"></i></button>
                                   </form>
                                </div>
                            </div>
                        </div>
                        </div>
                    <%}
                }
            %>
        </div>
    </div>

<br/>
<%@ include file="footerAndHeader/footer.jsp"%>
</body>
</html>