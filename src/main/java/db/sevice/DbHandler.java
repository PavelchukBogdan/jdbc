package db.sevice;

import db.entity.Category;
import db.entity.Product;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * DbHandler -
 *
 * @author Павельчук Богдан (pavelchuk.b)
 * @since 15.07.2020
 */

public class DbHandler {

    private static final String PROP_FILE_NAME = "db.properties";

    private static final String SELECT_QUERY = "select p.id, p.name, c.name as category_name from product p left join category c on p.category_id = c.id";
    private static final String INSERT_PRODUCT_QUERY = "insert into product(name, category_id) values ( ?, ?)";
    private static final String SELECT_CATEGORY_QUERY = "select  id from category";
    private static final String SELECT_PRODUCT_BY_ID = "select p.id, p.name, c.id as category_id,c.name as category_name from product p left join category c  on p.category_id = c.id where p.id = ?";

    public Connection getConnection() {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME);
        if(inputStream != null)

        {
            try{
                properties.load(inputStream);
            } catch(IOException e){

            }
        }

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String urlDb = properties.getProperty("url");

        Connection connection = null;
        try{
            connection = DriverManager.getConnection(urlDb, user, password);
        } catch(SQLException e){
            e.printStackTrace();
        }

        return connection;

    }

    public void printInfo(Connection connection) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);

        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            System.out.print(resultSet.getInt("id") + " " + resultSet.getString("name") + " " + resultSet.getString("category_name"));
            System.out.println();
        }

    }

    public Product addProduct(Connection connection, String productName) throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet categoryResult = statement.executeQuery(SELECT_CATEGORY_QUERY);
        List<Integer> categoryIds = new ArrayList<>();
        while(categoryResult.next()){
            categoryIds.add(categoryResult.getInt("id"));
        }
        int randomCategoryId = new Random().nextInt(categoryIds.size());
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_QUERY, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, productName);
        preparedStatement.setInt(2, categoryIds.get(randomCategoryId));
        preparedStatement.executeUpdate();
        ResultSet result = preparedStatement.getGeneratedKeys();
        if(result.next()){
            PreparedStatement selectProductStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);

            selectProductStatement.setInt(1, result.getInt(1));

            ResultSet productSet = selectProductStatement.executeQuery();
            productSet.next();
            Category category = new Category(productSet.getInt("category_id"), productSet.getString("category_name"));
            Product product = new Product(productSet.getInt("id"), productSet.getString("name"),category);
            return product;

        }

        throw  new IllegalArgumentException("Не удалось сгенерировать продукт");

    }


}











