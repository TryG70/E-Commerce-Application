package tanc.dao;

import tanc.model.Cart;
import tanc.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private Connection connection;
    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ProductDao(Connection connection) {
        this.connection = connection;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try {

            query = "SELECT * FROM products";
            preparedStatement = this.connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product row = new Product();
                row.setId(resultSet.getInt("id"));
                row.setName(resultSet.getString("name"));
                row.setCategory(resultSet.getString("category"));
                row.setPrice(resultSet.getDouble("price"));
                row.setImage(resultSet.getString("image"));

                products.add(row);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
    public List<Cart> getCartProducts(ArrayList<Cart> cartList) {
        List<Cart> products = new ArrayList<>();

        try {
            if (cartList.size() > 0) {
                for (Cart cartItem : cartList) {
                    query = "SELECT * FROM products WHERE id=?";
                    preparedStatement = this.connection.prepareStatement(query);
                    preparedStatement.setInt(1, cartItem.getId());
                    resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                        Cart newCart = new Cart();

                        newCart.setId(resultSet.getInt("id"));
                        newCart.setName(resultSet.getString("name"));
                        newCart.setCategory(resultSet.getString("category"));
                        newCart.setPrice(resultSet.getDouble("price") * cartItem.getQuantity());
                        newCart.setQuantity(cartItem.getQuantity());
                        products.add(newCart);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public Product getSingleProduct(int id) {
        Product product = null;

        try{

            query = "SELECT * FROM products WHERE id=?";
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setCategory(resultSet.getString("category"));
                product.setPrice(resultSet.getDouble("price"));
                product.setImage(resultSet.getString("image"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    public double getCartTotalPrice(ArrayList<Cart> cartList) {
        double sum = 0;

        try {

            if(cartList.size() > 0) {
                for (Cart cartItem: cartList) {
                    query = "SELECT price FROM products WHERE id=?";
                    preparedStatement = this.connection.prepareStatement(query);
                    preparedStatement.setInt(1, cartItem.getId());
                    resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()) {
                       sum += resultSet.getDouble("price") * cartItem.getQuantity();

                    }

                }
            }
            return sum;



        } catch (Exception e) {
            e.printStackTrace();
        }

        return sum;
    }

    public boolean addProductToDb(Product product) {
        try {
            query = "INSERT INTO products(name, category, price, image) VALUES (?,?,?,?)";
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getCategory());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getImage());

            return preparedStatement.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteProductToDb(String name, String category) {
        try {

            query = "DELETE FROM products WHERE name=? AND category=?";
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, category);

            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
