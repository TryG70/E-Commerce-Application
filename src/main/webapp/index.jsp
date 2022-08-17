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
    <script>
        $(document).ready(function () {
            $(".wishlist").click(function (e) {
                e.preventDefault();
                let userId = this.getAttribute("data-bs-user-id");
                let productId = this.getAttribute("data-bs-product-id");
                console.log(userId);
                console.log(productId);

                $.ajax({
                    type : "POST",
                    url : "${pageContext.request.contextPath}/AddToWishlistServlet",
                    data: {user_id : userId , product_id : productId},

                    success: function (data) {
                        console.log($(".wishlist"));


                        sessionStorage.setItem("add"+productId , ""+productId);
                        sessionStorage.removeItem("delete"+productId);
                        let added = sessionStorage.getItem("add"+productId );
                        console.log(added)
                        if (added != null){
                            $("#deleteform"+productId).css("display" , "block")
                            $("#addform"+productId).css("display" , "none");
                        }
                    }
                })
            })
            $(".deletewishlist").click(function (e) {
                e.preventDefault();
                let userId = this.getAttribute("data-bs-user-id");
                let productId = this.getAttribute("data-bs-product-id");
                console.log(userId);
                console.log(productId);

                $.ajax({
                    type : "POST",
                    url : "${pageContext.request.contextPath}/RemoveFromWishList",
                    data: {user_id : userId , product_id : productId},

                    success: function (data) {
                        sessionStorage.setItem("delete"+productId , ""+productId);
                        sessionStorage.removeItem("add"+productId);
                        let deleted = sessionStorage.getItem("delete"+productId );
                        console.log(deleted);
                        if (deleted != null){
                            $("#deleteform"+productId).css("display" , "none")
                            $("#addform"+productId).css("display" , "block");
                        }

                    }
                })
            })
        })
    </script>
</head>
<body>
    <%@ include file="footerAndHeader/navBar.jsp"%>
    <div class="container products__body">
        <div class="card-header mt-3">All Products</div>
        <div class="row">
            <%
                if(!(products.isEmpty())) {
                    for (Product product: products) {%>
                        <div class="col-md-4 my-3">
                        <div class="card w-100" style="width: 18rem;">
                            <img style="width: auto; height: 300px" src="product-pictures/<%= product.getImage()%>" class="card-img-top product__image__card" alt="Card image cap">
                            <div class="card-body">
                                <h5 class="card-title"><%=product.getName()%></h5>
                                <h6 class="price">Price: $<%=product.getPrice()%></h6>
                                <h6 class="category">Category: <%=product.getCategory()%></h6>
                                <div class="mt-3 d-flex justify-content-between">
                                    <a href="add-to cart?id=<%=product.getId()%>" class="btn btn-red">Add to Cart</a>
                                    <a href="order-now?quantity=1&id=<%=product.getId()%>" class="btn btn-primary">Buy Now</a>
                                    <form id="addform<%=product.getId()%>" action="${pageContext.request.contextPath}/WishlistServlet" method="POST">
                                        <input type="hidden" name="product_id" value="<%=product.getId()%>">
                                        <input type="hidden" name="user_id" value="<%= (request.getSession().getAttribute("id"))%>">
                                        <div class="col-3"><button class="wishlist" id="<%=product.getId()%>"  data-bs-user-id="<%= (request.getSession().getAttribute("id"))%>" data-bs-product-id="<%=product.getId()%>"  style="border: none; outline: none; background-color: transparent" type="submit"> <i class="bi bi-heart"></i></button></div>

                                    </form>
                                    <form id="deleteform<%=product.getId()%>" style="display: none" action="${pageContext.request.contextPath}/DeleteProductServlet" method="POST">
                                        <input type="hidden" name="product_id" value="<%=product.getId()%>">
                                        <input type="hidden" name="user_id" value="<%= (request.getSession().getAttribute("id"))%>">
                                        <div class="col-3"><button class="deletewishlist" id="delete<%=product.getId()%>"  data-bs-user-id="<%= (request.getSession().getAttribute("id"))%>" data-bs-product-id="<%=product.getId()%>"  style="border: none; outline: none; background-color: transparent; color: red;" type="submit"> <i class="bi bi-heart-fill"></i></button></div>
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