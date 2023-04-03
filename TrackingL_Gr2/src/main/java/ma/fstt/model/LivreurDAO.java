package ma.fstt.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivreurDAO extends BaseDAO<Livreur>{
    public LivreurDAO() throws SQLException {
        super();
    }

    @Override
    public void save(Livreur object) throws SQLException {

        String request = "insert into livreur (nom , telephone, status) values (? , ?, ?)";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setString(1 , object.getNom());

        this.preparedStatement.setString(2 , object.getTelephone());

        this.preparedStatement.setString(3 , object.getStatus());

        this.preparedStatement.execute();

    }

    @Override
    public void update(Livreur object) throws SQLException {

        String request = "UPDATE `livreur` SET `nom`=?,`telephone`=? WHERE id_livreur = ?";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setString(1 , object.getNom());

        this.preparedStatement.setString(2 , object.getTelephone());

        this.preparedStatement.setLong(3 , object.getId_livreur());

        this.preparedStatement.execute();
    }

    @Override
    public void delete(Livreur object) throws SQLException {
        String request = "DELETE FROM `livreur` WHERE `id_livreur`=?;";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1 , object.getId_livreur());
        this.preparedStatement.execute();
    }

    @Override
    public List<Livreur> getAll()  throws SQLException {

        List<Livreur> mylist = new ArrayList<Livreur>();

        String request = "select * from livreur ";

        this.statement = this.connection.createStatement();

        this.resultSet =   this.statement.executeQuery(request);

// parcours de la table
        while ( this.resultSet.next()){

// mapping table objet
            mylist.add(new Livreur(this.resultSet.getLong(1) ,
                    this.resultSet.getString(2) , this.resultSet.getString(3), this.resultSet.getString(4)));

        }

        return mylist;
    }

    public String CountActif() throws SQLException {
        String request = "SELECT COUNT(id_livreur) AS num_livreurs FROM livreur WHERE status='Not Available'";
        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(request);
        int num = 0;
        if (this.resultSet.next()) {
            num = this.resultSet.getInt("num_livreurs");
        }
        String numString = Integer.toString(num);
        return numString;
    }

    public long getAvaible() throws SQLException {
        String request = "SELECT id_livreur FROM livreur WHERE status = 'Available' LIMIT 1";
        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(request);

        if (this.resultSet.next()) {
            changeStat(this.resultSet.getLong("id_livreur"));
            return this.resultSet.getLong("id_livreur");
        }
        return -1;
    }

    public void changeStat(long Id) throws SQLException {
        String request2 = "UPDATE livreur SET status = 'Not Available' WHERE id_livreur = " + Id + ";";
        statement = connection.createStatement();
        int rowsUpdated = statement.executeUpdate(request2);
    }

    public void DeliveredCmd(long Id) throws SQLException {
        String request1 = "UPDATE livreur SET status = ? WHERE id_livreur = ?";
        String request2 = "DELETE FROM delivery WHERE id_livreur = ?";
        PreparedStatement updateStatusStatement = connection.prepareStatement(request1);
        PreparedStatement deleteDeliveryStatement = connection.prepareStatement(request2);
        updateStatusStatement.setString(1, "Available");
        updateStatusStatement.setLong(2, Id);
        deleteDeliveryStatement.setLong(1, Id);
        connection.setAutoCommit(false);
        try {
            updateStatusStatement.executeUpdate();
            deleteDeliveryStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public Livreur getOne(Long id) throws SQLException {
        return null;
    }
}
