package ma.fstt.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandDAO extends BaseDAO<Command>{
    public CommandDAO() throws SQLException {
    }

    @Override
    public void save(Command object) throws SQLException {
        String request = "INSERT INTO `commande`(`id_cmd`, `brand`, `productName`, `quantityProd`, `price`, `date_cmd`) VALUES (?,?,?,?,?,?)";

        // mapping objet table
        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setLong(1 , object.getId_cmd());
        this.preparedStatement.setString(2 , object.getBrand());
        this.preparedStatement.setString(3 , object.getProductName());
        this.preparedStatement.setString(4 , object.getQuantityProd());
        this.preparedStatement.setString(5 , String.valueOf( Double.valueOf(object.getPrice())* Double.valueOf(object.getQuantityProd()) ) );
        this.preparedStatement.setString( 6, object.getDate_cmd());

        this.preparedStatement.execute();
    }

    public String TotalPrice() throws SQLException {
        String request = "SELECT price FROM commande";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(request);
        Double Sum=0.0;
        while ( resultSet.next()){
            Sum +=Double.valueOf(resultSet.getString(1));
        }
        return String.valueOf(Sum);
    }

    @Override
    public void update(Command object) throws SQLException {

    }

    @Override
    public void delete(Command object) throws SQLException {

    }

    @Override
    public List<Command> getAll() throws SQLException {
        List<Command> mylist = new ArrayList<Command>();

        String request = "select * from commande ";

        this.statement = this.connection.createStatement();

        this.resultSet =   this.statement.executeQuery(request);

        // Parcours de la table products
        while ( this.resultSet.next()){

            // mapping table objet
            mylist.add(new Command(this.resultSet.getLong(1) ,
                    this.resultSet.getString(2) ,
                    this.resultSet.getString(3),
                    this.resultSet.getString(4),
                    this.resultSet.getString(5),
                    this.resultSet.getString(6)
            ));

        }
        return mylist;
    }

    public void Empty() throws SQLException {
        String request = "DELETE FROM `commande`";
        statement = connection.createStatement();
        statement.executeUpdate(request);
    }


    @Override
    public Command getOne(Long id) throws SQLException {
        return null;
    }
}
