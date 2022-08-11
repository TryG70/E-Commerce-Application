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
    <title>Login / Sign up Page</title>
    <%@ include file="footerAndHeader/header.jsp"%>
</head>
<body>
<%@ include file="footerAndHeader/navBar.jsp"%>
<div class="container">
    <div class="card w-50 mx-auto my-5">
        <div class="card-header text-center">User Login</div>
        <div class="card-body">
            <form action="LoginServlet" method="post">

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" class="form-control" name="login-email" placeholder=" Kindly Enter your Email" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" class="form-control" name="login-password" placeholder="*********** " required>
                </div>

                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Login </button>
                </div>

                <li class="nav-item">
                <a class="nav-link" href="registration.jsp">Sign up</a>
                </li>

            </form>
        </div>
    </div>
</div>
<br/>
<%@ include file="footerAndHeader/footer.jsp"%>
</body>
</html>