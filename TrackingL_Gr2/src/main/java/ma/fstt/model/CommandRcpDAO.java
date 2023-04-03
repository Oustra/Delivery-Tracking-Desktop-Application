package ma.fstt.model;

import javafx.scene.chart.XYChart;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CommandRcpDAO extends BaseDAO<CommandRcp>{
    public CommandRcpDAO() throws SQLException {
        super();
    }

    @Override
    public void save(CommandRcp object) throws SQLException {
        String request = "INSERT INTO `commande_receipt`(`id`, `total`, `date`) VALUES (?,?,?)";

        // mapping objet table
        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setLong(1 , object.getId());
        this.preparedStatement.setString(2 , object.getTotal());
        this.preparedStatement.setString(3 , object.getDate());
        this.preparedStatement.execute();
    }

    @Override
    public void update(CommandRcp object) throws SQLException {

    }

    @Override
    public void delete(CommandRcp object) throws SQLException {

    }

    @Override
    public List<CommandRcp> getAll() throws SQLException {
        return null;
    }

    @Override
    public CommandRcp getOne(Long id) throws SQLException {
        return null;
    }

    public String CountTincome() throws SQLException {
        DateTimeFormatter dtfr = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String dateNow = now.format(dtfr);
        String request = "SELECT total FROM commande_receipt WHERE date='"+dateNow+"'";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(request);
        Double Sum=0.0;
        while ( resultSet.next()){
            Sum +=Double.valueOf(resultSet.getString(1));
        }
        return String.valueOf(Sum);
    }

    public  String CountTotal() throws SQLException {
        String request = "SELECT total FROM commande_receipt ";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(request);
        Double Sum=0.0;
        while ( resultSet.next()){
            Sum +=Double.valueOf(resultSet.getString(1));
        }
        return String.valueOf(Sum);
    }

    public XYChart.Series Chart() throws SQLException {
        String request = "SELECT date, SUM(CAST(total AS DECIMAL)) FROM commande_receipt GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 9";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(request);
        XYChart.Series chart = new XYChart.Series();
        while (resultSet.next()) {
            chart.getData().add(new XYChart.Data(resultSet.getString(1), resultSet.getBigDecimal(2)));
        }
        return chart;
    }

}
