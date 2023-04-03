package ma.fstt.trackingl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ma.fstt.model.*;
import ma.fstt.model.Product;
import ma.fstt.model.ProductDAO;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
public class HomeController implements Initializable {

    //Side Menu--------------------------------------------------------------------------------
    @FXML
    private Button DashBoardBtn;
    @FXML
    private Button AddProdBtn;
    @FXML
    private  Button DeliveryBtn;
    @FXML
    private  Button ExitBtn;
    @FXML
    private  Button logoutBnt;


    public void switchForm(ActionEvent event){
        if(event.getSource()== DashBoardBtn){
            DashBoardMenu.setVisible(true);
            ProductMenu.setVisible(false);
            DeliveryMenu.setVisible(false);
            DashBoardBtn.setStyle("-fx-background-color: white");
            AddProdBtn.setStyle("-fx-background-color: transparent");
            DeliveryBtn.setStyle("-fx-background-color: transparent");
        }
        else if (event.getSource() == AddProdBtn){
            DashBoardMenu.setVisible(false);
            ProductMenu.setVisible(true);
            DeliveryMenu.setVisible(false);
            DashBoardBtn.setStyle("-fx-background-color: transparent");
            AddProdBtn.setStyle("-fx-background-color: white");
            DeliveryBtn.setStyle("-fx-background-color: transparent");
            //ProdStatusList();
        }
        else if (event.getSource() == DeliveryBtn){
            DashBoardMenu.setVisible(false);
            ProductMenu.setVisible(false);
            DeliveryMenu.setVisible(true);
            DashBoardBtn.setStyle("-fx-background-color: transparent");
            AddProdBtn.setStyle("-fx-background-color: transparent");
            DeliveryBtn.setStyle("-fx-background-color: white");
        }
    }

    public void ExitFun(ActionEvent event){
        Stage stage = (Stage) ExitBtn.getScene().getWindow();
        stage.close();
    }

    public void LogOut(ActionEvent event){
        try {
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("You want to Logout ?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)){
                logoutBnt.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("Login-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //DashBoard Menu----------------------------------------------------------------------------
    @FXML
    private AnchorPane DashBoardMenu;
    @FXML
    private Label ActiveDeliv;
    @FXML
    private Label TodayIncome;
    @FXML
    private Label TotalIncome;
    @FXML
    private AreaChart IncomeChart;

    public void DashBoardDelivery() throws SQLException {
        try {
            LivreurDAO L = new LivreurDAO();
            String Num = L.CountActif();
            ActiveDeliv.setText(Num);}
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  void DashBoardTodayIn()throws SQLException{
        try {
            CommandRcpDAO C = new CommandRcpDAO();
            String TDay = C.CountTincome();
            TodayIncome.setText(TDay);}
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void DashBoardTotalIn(){
        try {
            CommandRcpDAO C = new CommandRcpDAO();
            String TDay = C.CountTotal();
            TotalIncome.setText(TDay);}
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void DashBoardChart(){
        try {
            CommandRcpDAO C = new CommandRcpDAO();
            XYChart.Series chart = C.Chart();
            IncomeChart.getData().add(chart);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //ADD Prod Menu----------------------------------------------------------------------------

    @FXML
    private AnchorPane ProductMenu;
    @FXML
    private TextField NameTxt;
    @FXML
    private TextField BrandTxt;
    @FXML
    private TextField PriceTxt;
    @FXML
    private TextField Search;
    @FXML
    private ComboBox StatusField;
    @FXML
    private Button AddProd;
    @FXML
    private Button UpdateProd;
    @FXML
    private Button DeleteProd;
    @FXML
    private Button ClearProd;
    @FXML
    private TableView<Product> TableProd ;
    @FXML
    private TableColumn<Product ,Long> ID_Prod ;
    @FXML
    private TableColumn <Product ,String> NomProd ;
    @FXML
    private TableColumn <Product ,String> BrandProd ;
    @FXML
    private TableColumn <Product ,String> StatProd ;
    @FXML
    private TableColumn <Product ,String> PriceProdCol ;


    public void onAddProdButtonClick(ActionEvent event) {
        Alert alert = null;
        // accees a la bdd
        if ( NameTxt.getText().isEmpty() || BrandTxt.getText().isEmpty() ||  StatusField.getSelectionModel().getSelectedItem()==null ||PriceTxt.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
        else {
            try {
                ProductDAO productDAO = new ProductDAO();

                Product prod = new Product(0l , NameTxt.getText() , BrandTxt.getText(), (String) StatusField.getSelectionModel().getSelectedItem(),PriceTxt.getText());

                productDAO.save(prod);

                UpdateTable();

                onClearProdButtonClick();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onClearProdButtonClick(){
        NameTxt.setText("");
        BrandTxt.setText("");
        StatusField.getSelectionModel().clearSelection();
        PriceTxt.setText("");
    }

    public void  Product_Select(MouseEvent mouseEvent){
        Product prod = TableProd.getSelectionModel().getSelectedItem();
        int num = TableProd.getSelectionModel().getSelectedIndex();
        if((num - 1)<-1){
            return;
        }
        NameTxt.setText(prod.getProduct_name());
        BrandTxt.setText(prod.getBrand());
        PriceTxt.setText(prod.getPrice());
        LastSelectedProd= prod.getId_products();
    }

    private String[] statusList = {"Available", "Not Available"};
    public void ProdStatusList(){
        List<String> ListS = new ArrayList<>();
        for (String data: statusList){
            ListS.add(data);
        }
        ObservableList statusData = FXCollections.observableArrayList(ListS);
        StatusField.setItems(statusData);
    }

    public void UpdateTable(){
        ID_Prod.setCellValueFactory(new PropertyValueFactory<Product,Long>("id_products"));

        NomProd.setCellValueFactory(new PropertyValueFactory<Product,String>("product_name"));

        BrandProd.setCellValueFactory(new PropertyValueFactory<Product,String>("brand"));

        StatProd.setCellValueFactory(new PropertyValueFactory<Product,String>("status"));

        PriceProdCol.setCellValueFactory(new PropertyValueFactory<Product,String>("price"));

        TableProd.setItems(this.getDataProducts());

    }

    public static ObservableList<Product> getDataProducts(){

        ProductDAO productDAO = null;

        ObservableList<Product> listfx = FXCollections.observableArrayList();

        try {
            productDAO = new ProductDAO();
            for (Product ettemp : productDAO.getAll())
                listfx.add(ettemp);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx ;
    }

    private static long LastSelectedProd;
    public void onUpdateProdButtonClick(){
        Alert alert = null;
        // accees a la bdd
        if ( NameTxt.getText().isEmpty() || BrandTxt.getText().isEmpty() ||  StatusField.getSelectionModel().getSelectedItem()==null ||PriceTxt.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
        else {
            try {
                ProductDAO productDAO = new ProductDAO();

                Product prod = new Product(LastSelectedProd , NameTxt.getText() , BrandTxt.getText(), (String) StatusField.getSelectionModel().getSelectedItem(),PriceTxt.getText());

                productDAO.update(prod);

                UpdateTable();

                onClearProdButtonClick();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onDeleteProdButtonClick(){
        try {
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("You want to Delete this products ?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)){
                ProductDAO productDAO = new ProductDAO();
                Product prod = new Product(LastSelectedProd ,"","","","");
                productDAO.delete(prod);
                UpdateTable();
                onClearProdButtonClick();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    //Delivery Menu---------------------------------------------------------------------------
    @FXML
    private AnchorPane DeliveryMenu;
    @FXML
    private TextField NameDelTxt;
    @FXML
    private TextField TelTxt;
    @FXML
    private Button SaveDel;
    @FXML
    private Button UpdateDel;
    @FXML
    private Button DeleteDel;
    @FXML
    private Button ClearDel;
    @FXML
    private TableView<Livreur> TableDel ;
    @FXML
    private TableColumn<Livreur ,Long> ID_Del ;
    @FXML
    private TableColumn <Livreur ,String> NomDel ;
    @FXML
    private TableColumn <Livreur ,String> TelDel ;
    @FXML
    private TableColumn <Livreur ,String> StatDelcol ;


    public void onAddDelButtonClick(ActionEvent event) {
        Alert alert = null;
        // accees a la bdd
        if ( NameDelTxt.getText().isEmpty() || TelTxt.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
        else {
            try {
                LivreurDAO livreurDAO = new LivreurDAO();

                Livreur liv = new Livreur(0l , NameDelTxt.getText() , TelTxt.getText(),"Available");

                livreurDAO.save(liv);

                UpdateTableDel();

                onClearDelButtonClick();

                DashBoardDelivery();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onClearDelButtonClick(){
        NameDelTxt.setText("");
        TelTxt.setText("");
    }

    public void  Deliver_Select(MouseEvent mouseEvent){
        Livreur liv = TableDel.getSelectionModel().getSelectedItem();
        int num = TableDel.getSelectionModel().getSelectedIndex();
        if((num - 1)<-1){
            return;
        }
        NameDelTxt.setText(liv.getNom());
        TelTxt.setText(liv.getTelephone());
        LastSelectedDel= liv.getId_livreur();
    }

    public void UpdateTableDel(){
        ID_Del.setCellValueFactory(new PropertyValueFactory<Livreur,Long>("id_livreur"));

        NomDel.setCellValueFactory(new PropertyValueFactory<Livreur,String>("nom"));

        TelDel.setCellValueFactory(new PropertyValueFactory<Livreur,String>("telephone"));

        StatDelcol.setCellValueFactory(new PropertyValueFactory<Livreur,String>("status"));

        TableDel.setItems(this.getDataDel());

    }

    public static ObservableList<Livreur> getDataDel(){

        LivreurDAO livreurDAO = null;

        ObservableList<Livreur> listfx = FXCollections.observableArrayList();

        try {
            livreurDAO = new LivreurDAO();
            for (Livreur ettemp : livreurDAO.getAll())
                listfx.add(ettemp);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx ;
    }

    private static long LastSelectedDel;
    public void onUpdateDelButtonClick(){
        Alert alert = null;
        // accees a la bdd
        if ( NameDelTxt.getText().isEmpty() || TelTxt.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
        else {
            try {
                LivreurDAO livreurDAO = new LivreurDAO();

                Livreur liv = new Livreur(LastSelectedDel, NameDelTxt.getText() , TelTxt.getText(),"Available");

                livreurDAO.update(liv);

                UpdateTableDel();

                onClearDelButtonClick();

                DashBoardDelivery();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void onDeleteDelButtonClick(){
        try {
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("You want to Delete this Deliverer ?");
            Optional<ButtonType> option2 = alert.showAndWait();

            if (option2.get().equals(ButtonType.OK)){
                LivreurDAO livreurDAO = new LivreurDAO();
                Livreur liv = new Livreur(LastSelectedDel ,"","","");
                livreurDAO.delete(liv);
                UpdateTableDel();
                onClearDelButtonClick();
                DashBoardDelivery();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private Button Delivered;
    public void OnDeliveredButton(){
        try {
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Confirm Delivery ?");
            Optional<ButtonType> option3 = alert.showAndWait();

            if (option3.get().equals(ButtonType.OK)){
                LivreurDAO livreurDAO = new LivreurDAO();
                livreurDAO.DeliveredCmd(LastSelectedDel);
                UpdateTableDel();
                DashBoardDelivery();
                Alert alert2= new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Confirmation");
                alert2.setHeaderText(null);
                alert2.setContentText("Deliverer is now Available and Delivery has been Deleted");
                alert2.showAndWait();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //Initialize Function---------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Side Menu Events
        DashBoardBtn.setOnAction(event -> {
            switchForm(event);
        });
        AddProdBtn.setOnAction(event -> {
            switchForm(event);
        });
        DeliveryBtn.setOnAction(event -> {
            switchForm(event);
        });
        ExitBtn.setOnAction(event -> {
            ExitFun(event);
        });
        logoutBnt.setOnAction(event -> {
            LogOut(event);
        });

        //Add Product Events
        UpdateTable();
        AddProd.setOnAction(event -> {
            onAddProdButtonClick(event);
        });
        TableProd.setOnMouseClicked(mouseEvent -> {
            Product_Select(mouseEvent);
        });
        ProdStatusList();

        //Delivery Events
        UpdateTableDel();
        //DashBoard
        try {
            DashBoardDelivery();
            DashBoardTotalIn();
            DashBoardTodayIn();
            DashBoardChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
