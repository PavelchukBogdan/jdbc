import db.entity.Product;
import db.sevice.DbHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Main -
 *
 * @author Павельчук Богдан (pavelchuk.b)
 * @since 15.07.2020
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        DbHandler dbHandler = new DbHandler();

        Connection connection = dbHandler.getConnection();
        dbHandler.printInfo(connection);
        Product product = dbHandler.addProduct(connection,"вешалка");
        System.out.println("создался продукт с именем " + product.getName() + " с категорией " + product.getCategory().getName() );


    }
}
