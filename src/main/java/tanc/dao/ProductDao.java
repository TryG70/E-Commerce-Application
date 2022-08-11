package tanc.dao;

import tanc.model.Cart;
import tanc.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}
