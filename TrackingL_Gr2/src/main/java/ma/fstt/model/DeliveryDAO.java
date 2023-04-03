package ma.fstt.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO extends BaseDAO<Delivery>{
    public DeliveryDAO() throws SQLException {
        super();
    }

    @Override
    public void save(Delivery object) throws SQLException {
        String request = "INSERT INTO `delivery`(`id_delivery`,`adresse`, `status`, `id_livreur`) VALUES (?, ? , ?, ?)";

        // mapping objet table
        this.preparedStatement = this.connection.prepareStatement(request);

        // mapping
        this.preparedStatement.setLong(1 , object.getId_livreur());

        this.preparedStatement.setString(2 , object.getAdresse());

        this.preparedStatement.setString(3 , object.getStatus());

        this.preparedStatement.setLong(4 , object.getId_livreur());

        this.preparedStatement.execute();
    }

    @Override
    public void update(Delivery object) throws SQLException {

    }

    @Override
    public void delete(Delivery object) throws SQLException {

    }

    @Override
    public List<Delivery> getAll() throws SQLException {
        List<Delivery> mylist = new ArrayList<Delivery>();

        String request = "SELECT delivery.id_delivery, delivery.adresse, delivery.status, livreur.nom, livreur.telephone FROM delivery INNER JOIN livreur ON delivery.id_livreur = livreur.id_livreur";

        this.statement = this.connection.createStatement();

        this.resultSet =   this.statement.executeQuery(request);

        // parcours de la table
        while ( this.resultSet.next()){
        // mapping table objet
            mylist.add(new Delivery(this.resultSet.getLong(1) ,
                    this.resultSet.getString(2) ,
                    this.resultSet.getString(3),
                    this.resultSet.getString(4),
                    this.resultSet.getString(5)));
        }

        return mylist;
    }

    public String getDelName(Delivery dlv) throws SQLException {
        String request = "SELECT nom FROM livreur WHERE id_livreur = " + dlv.getId_livreur()+ ";";;
        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(request);
        String Name = "";
        if (this.resultSet.next()) {
            Name= this.resultSet.getString("nom");
        }
        return Name;
    }

    @Override
    public Delivery getOne(Long id) throws SQLException {
        return null;
    }
}
