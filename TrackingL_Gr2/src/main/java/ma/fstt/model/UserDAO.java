package ma.fstt.model;

import java.sql.SQLException;
import java.util.List;

public class UserDAO extends BaseDAO<User>{
    public UserDAO() throws SQLException {
        super();
    }

    @Override
    public void save(User object) throws SQLException {

    }

    @Override
    public void update(User object) throws SQLException {

    }

    @Override
    public void delete(User object) throws SQLException {

    }

    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }

    @Override
    public User getOne(Long id) throws SQLException {
        return null;
    }

    public boolean isLoginUser(User us) throws SQLException {

        String request = "SELECT * FROM user WHERE name=? AND password=?";

        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setString(1, us.getName());
        this.preparedStatement.setString(2, us.getPassword());

        this.resultSet = this.preparedStatement.executeQuery();

        if (this.resultSet.next())
            return true;
        return false;
    }
}
