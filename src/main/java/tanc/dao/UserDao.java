package tanc.dao;

import tanc.model.Order;
import tanc.model.Product;
import tanc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private Connection connection;
    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public User userLogin (String email, String password) {
        User user = null;
        try {
            query = "SELECT * FROM users WHERE email=? AND password=?";
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public int populateUserTable(String name, String email, String password) throws SQLException {

        query = "INSERT INTO users(name, email, password) VALUES(?,?,?)";
        preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, password);

        int count = preparedStatement.executeUpdate();
        return count;
    }

    public boolean populateWishListTable(int user, int product) {
        boolean result = false;

        try {

            query = "INSERT INTO wishList (user_id, product_id) VALUES (?,?)";
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, user);
            preparedStatement.setInt(2, product);

            preparedStatement.executeUpdate();
            result = true;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
