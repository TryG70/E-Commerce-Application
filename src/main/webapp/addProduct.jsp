<%@ page import="tanc.model.User" %>
<%@ page import="tanc.model.Cart" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% User TryGod = (User) request.getSession().getAttribute("TryGod");
    if(TryGod != null) {
        response.sendRedirect("index.jsp");
    }
    ArrayList<Cart> carts = (ArrayList<Cart>) session.getAttribute("c-list");

    if (carts != null) {
        request.setAttribute("carts", carts);
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Add product to Database Page</title>
    <%@ include file="footerAndHeader/header.jsp"%>
</head>
<body>
<%@ include file="footerAndHeader/navBar.jsp"%>
<div class="container">
    <div class="card w-50 mx-auto my-5">
        <div class="card-header text-center">Add Product to Database</div>
        <div class="card-body">
            <form action="AddProductToDatabase" method="post">

                <div class="form-group">
                    <label for="name">Name of Product</label>
                    <input type="name" id="name" class="form-control" name="name" placeholder="Kindly type in the Product name" required>
                </div>
                <div class="form-group">
                    <label for="category">Category of Product</label>
                    <input type="category" id="category" class="form-control" name="category" placeholder="Kindly type in the Category of the product" required>
                </div>
                <div class="form-group">
                    <label for="price">Price</label>
                    <input type="price" id="price" class="form-control" name="price" placeholder="Kindly type in the Product name" required>
                </div>
                <div class="form-group">
                    <label for="image">Image</label>
                    <input type="image url" id="image" class="form-control" name="image" placeholder="product image url" required>
                </div>

                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>

            </form>
        </div>
    </div>
</div>
<br/>
<%@ include file="footerAndHeader/footer.jsp"%>
</body>
</html>