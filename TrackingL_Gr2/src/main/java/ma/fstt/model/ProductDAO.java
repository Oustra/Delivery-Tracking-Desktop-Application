package ma.fstt.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends BaseDAO<Product>{
    public ProductDAO() throws SQLException {
    }

    @Override
    public void save(Product object) throws SQLException {
        String request = "INSERT INTO `products`(`id_products`,`product_name`, `brand`, `status`, `price`) VALUES (?,?,?,?,?)";

        // mapping objet table
        this.preparedStatement = this.connection.prepareStatement(request);

        // mapping
        this.preparedStatement.setLong(1 , object.getId_products());
        this.preparedStatement.setString(2 , object.getProduct_name());
        this.preparedStatement.setString(3 , object.getBrand());
        this.preparedStatement.setString(4 , object.getStatus());
        this.preparedStatement.setString(5 , object.getPrice());

        this.preparedStatement.execute();
    }

    @Override
    public void update(Product object) throws SQLException {
        String request = "UPDATE `products` SET `product_name`=?,`brand`=?,`status`=?,`price`=? WHERE `id_products`=?;";

        // mapping objet table
        this.preparedStatement = this.connection.prepareStatement(request);

        // mapping
        this.preparedStatement.setString(1 , object.getProduct_name());
        this.preparedStatement.setString(2 , object.getBrand());
        this.preparedStatement.setString(3 , object.getStatus());
        this.preparedStatement.setString(4 , object.getPrice());
        this.preparedStatement.setLong(5 , object.getId_products());

        this.preparedStatement.execute();
    }

    @Override
    public void delete(Product object) throws SQLException {
        String request = "DELETE FROM `products` WHERE `id_products`=?;";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1 , object.getId_products());
        this.preparedStatement.execute();
    }

    @Override
    public List<Product> getAll() throws SQLException {
        List<Product> mylist = new ArrayList<Product>();

        String request = "select * from products ";

        this.statement = this.connection.createStatement();

        this.resultSet =   this.statement.executeQuery(request);

        // Parcours de la table products
        while ( this.resultSet.next()){

            // mapping table objet
            mylist.add(new Product(this.resultSet.getLong(1) ,
                    this.resultSet.getString(2) ,
                    this.resultSet.getString(3),
                    this.resultSet.getString(4),
                    this.resultSet.getString(5)
            ));

        }

        return mylist;
    }

    public List<Product> getAvaible() throws SQLException {
        List<Product> mylist = new ArrayList<Product>();

        String request = "select * from products where status='Available'";

        this.statement = this.connection.createStatement();

        this.resultSet =   this.statement.executeQuery(request);

        // Parcours de la table products
        while ( this.resultSet.next()){

            // mapping table objet
            mylist.add(new Product(this.resultSet.getLong(1) ,
                    this.resultSet.getString(2) ,
                    this.resultSet.getString(3),
                    this.resultSet.getString(4),
                    this.resultSet.getString(5)
            ));

        }

        return mylist;
    }
    @Override
    public Product getOne(Long id) throws SQLException {
        return null;
    }
}
