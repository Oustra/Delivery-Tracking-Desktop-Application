package ma.fstt.model;

import java.sql.SQLException;
import java.util.List;
public class AdminDAO extends BaseDAO<Admin>{

    public AdminDAO() throws SQLException {
        super();
    }

    @Override
    public void save(Admin object) throws SQLException {

    }

    @Override
    public void update(Admin object) throws SQLException {

    }

    @Override
    public void delete(Admin object) throws SQLException {

    }

    @Override
    public List<Admin> getAll() throws SQLException {
        return null;
    }

    @Override
    public Admin getOne(Long id) throws SQLException {
        return null;
    }

    public boolean isLogin(Admin ad) throws SQLException {

        String request = "SELECT * FROM admins WHERE username=? AND password=?";

        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setString(1, ad.getUsername());
        this.preparedStatement.setString(2, ad.getPassword());

        this.resultSet = this.preparedStatement.executeQuery();

        if (this.resultSet.next())
            return true;
        return false;
    }

}
