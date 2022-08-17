package tanc.dao;

import tanc.model.Product;
import tanc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Product> getWishlists(int id){
        PreparedStatement wishlistProduct = null;
        List<Product> wishlist = new ArrayList<>();
        try {
            wishlistProduct = connection.prepareStatement("SELECT products.id , products.name, products.category, products.price FROM wishlist INNER JOIN products ON wishlist.product_id = products.id WHERE user_id = ?");
            wishlistProduct.setInt(1, id);
            ResultSet resultSet = wishlistProduct.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setCategory(resultSet.getString("category"));
                product.setPrice(Double.parseDouble(resultSet.getString("price")));
                wishlist.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return wishlist;
    }

    public boolean removeWishList(int user_id, int product_id){
        boolean isDeleted = false;
        String query = "DELETE FROM wishlist WHERE user_id = ? AND product_id = ?";
        PreparedStatement singleProduct = null;
        try {
            singleProduct = connection.prepareStatement(query);
            singleProduct.setInt(1, user_id);
            singleProduct.setInt(2, product_id);
            int resultSet = singleProduct.executeUpdate();
            isDeleted = resultSet >0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isDeleted;
    }
}
