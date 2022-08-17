package tanc.dao;

import tanc.model.Order;
import tanc.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    private Connection connection;
    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public boolean populateOrderTable(Order order) {
        boolean result = false;

        try {

            query = "INSERT INTO orders (product_id, user_id, order_quantity, order_date) VALUES (?,?,?,?)";
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, order.getId());
            preparedStatement.setInt(2, order.getUserId());
            preparedStatement.setInt(3, order.getQuantity());
            preparedStatement.setString(4, order.getDate());

            preparedStatement.executeUpdate();
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Order> usersOrderList(int id) {
        List<Order> orderList = new ArrayList<>();

        try {

            query = "SELECT * FROM orders WHERE user_id=? order by orders.order_id desc";
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();
                ProductDao productDao = new ProductDao(this.connection);
                int p_id = resultSet.getInt("product_id");

                Product product = productDao.getSingleProduct(p_id);
                order.setOrderId(resultSet.getInt("order_id"));
                order.setId(resultSet.getInt(p_id));
                order.setName(product.getName());
                order.setCategory(product.getCategory());
                order.setPrice(product.getPrice() * resultSet.getInt("order_quantity"));
                order.setQuantity(resultSet.getInt("order_quantity"));
                order.setDate(resultSet.getString("order_date"));

                orderList.add(order);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderList;
    }

    public void cancelOrder(int id) {
        try {

            query = "DELETE FROM orders WHERE order_id=?";
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
