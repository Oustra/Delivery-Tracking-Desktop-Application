package ma.fstt.trackingl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ma.fstt.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeUserController implements Initializable {

    @FXML
    private Button Exit;
    @FXML
    private Button LogOut;
    @FXML
    private Button Add;
    @FXML
    private Button Pay;
    @FXML
    private Button Rcp;
    @FXML
    private Label TotalField;

    @FXML
    private TextField NameTxt;
    @FXML
    private TextField BrandTxt;
    @FXML
    private TableView<Product> TableProdview ;
    @FXML
    private TableColumn <Product ,String> NomProd ;
    @FXML
    private TableColumn <Product ,String> BrandProd ;
    @FXML
    private TableColumn <Product ,String> PriceProdCol ;
    @FXML
    private TableView<Command> TableCmdview ;
    @FXML
    private TableColumn <Command ,String> BrandCol ;
    @FXML
    private TableColumn <Command ,String> ProdCol ;
    @FXML
    private TableColumn <Command ,String> QuantityCol ;
    @FXML
    private TableColumn <Command ,String> PriceCmdCol ;
    @FXML
    private TableColumn <Command ,String> DateCol ;

    @FXML
    private  Button FoodBtn;
    @FXML
    private  Button PurchaseBtn;
    @FXML
    private AnchorPane FoodView;
    @FXML
    private AnchorPane PurchaseView;
    @FXML
    private Spinner<Integer> Quantity;
    public void switchForm(ActionEvent event){
        if(event.getSource()== FoodBtn){
            FoodView.setVisible(true);
            PurchaseView.setVisible(false);
            DeliveryView.setVisible(false);
            FoodBtn.setStyle("-fx-background-color: white");
            PurchaseBtn.setStyle("-fx-background-color: transparent");
            DeliveryBtn.setStyle("-fx-background-color: transparent");
        }
        else if (event.getSource() == PurchaseBtn){
            FoodView.setVisible(false);
            PurchaseView.setVisible(true);
            DeliveryView.setVisible(false);
            FoodBtn.setStyle("-fx-background-color: transparent");
            PurchaseBtn.setStyle("-fx-background-color: white");
            DeliveryBtn.setStyle("-fx-background-color: transparent");

        } else if (event.getSource()== DeliveryBtn) {
            FoodView.setVisible(false);
            PurchaseView.setVisible(false);
            DeliveryView.setVisible(true);
            FoodBtn.setStyle("-fx-background-color: transparent");
            PurchaseBtn.setStyle("-fx-background-color: transparent");
            DeliveryBtn.setStyle("-fx-background-color: white");
        }
    }
    private SpinnerValueFactory spinner;
    public  void FoodSpinner(){
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0);
        Quantity.setValueFactory(spinner);

    }
    public void  Product_Select(MouseEvent mouseEvent){
        Product prod = TableProdview.getSelectionModel().getSelectedItem();
        int num = TableProdview.getSelectionModel().getSelectedIndex();
        if((num - 1)<-1){
            return;
        }
        NameTxt.setText(prod.getProduct_name());
        BrandTxt.setText(prod.getBrand());
        LastSelectedPrice= prod.getPrice();
    }

    public void UpdateTable(){
        NomProd.setCellValueFactory(new PropertyValueFactory<Product,String>("product_name"));

        BrandProd.setCellValueFactory(new PropertyValueFactory<Product,String>("brand"));

        PriceProdCol.setCellValueFactory(new PropertyValueFactory<Product,String>("price"));

        TableProdview.setItems(this.getDataProductsView());

    }

    public static ObservableList<Product> getDataProductsView(){

        ProductDAO productDAO = null;

        ObservableList<Product> listfx = FXCollections.observableArrayList();

        try {
            productDAO = new ProductDAO();
            for (Product ettemp : productDAO.getAvaible())//Get only Avaible products
                listfx.add(ettemp);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx ;
    }



    public void ExitFun(ActionEvent event){
        Stage stage = (Stage) Exit.getScene().getWindow();
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
                LogOut.getScene().getWindow().hide();
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

    private static String LastSelectedPrice;
    private static String TotalPrix;
    @FXML
    private Label TotalPriceField;

    public void onAddCmdButtonClick() {
        Alert alert = null;
        // accees a la bdd
        if ( NameTxt.getText().isEmpty() || BrandTxt.getText().isEmpty() ||  Quantity.getValue()==0){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please chose Product and Quantity");
            alert.showAndWait();
        }
        else {
            try {
                Alert alert2= new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Confirmation");
                alert2.setHeaderText(null);
                alert2.setContentText("Add to Cart ?");
                Optional<ButtonType> option = alert2.showAndWait();

                if (option.get().equals(ButtonType.OK)){
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    LocalDateTime now = LocalDateTime.now();
                    String dateNow = now.format(dtf);
                    CommandDAO commandDAO = new CommandDAO();
                    //Calc Total price
                    Command cmd = new Command(0l , NameTxt.getText() , BrandTxt.getText(),String.valueOf(Quantity.getValue()),LastSelectedPrice,dateNow);
                    commandDAO.save(cmd);
                    TotalPrix= commandDAO.TotalPrice();
                    TotalPriceField.setText(TotalPrix);
                    UpdateTableCmd();
                    NameTxt.setText("");
                    BrandTxt.setText("");
                    spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0);
                    Quantity.setValueFactory(spinner);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void UpdateTableCmd(){
        BrandCol.setCellValueFactory(new PropertyValueFactory<Command,String>("brand"));;
        ProdCol.setCellValueFactory(new PropertyValueFactory<Command,String>("productName")); ;
        QuantityCol.setCellValueFactory(new PropertyValueFactory<Command,String>("quantityProd")); ;
        PriceCmdCol.setCellValueFactory(new PropertyValueFactory<Command,String>("price")); ;
        DateCol.setCellValueFactory(new PropertyValueFactory<Command,String>("date_cmd")); ;
        TableCmdview.setItems(this.getDataCmdView());

    }

    public static ObservableList<Command> getDataCmdView(){

        CommandDAO commandDAO = null;

        ObservableList<Command> listfx = FXCollections.observableArrayList();

        try {
            commandDAO = new CommandDAO();
            for (Command ettemp : commandDAO.getAll())
                listfx.add(ettemp);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx ;
    }

    public void Pay(){
        Alert alert = null;
        // accees a la bdd
        if ( TableCmdview.getItems().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please Add some products to Cart");
            alert.showAndWait();

        } else {

            try {
                if (AdresseTxt.getText().isEmpty()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please Entre your Address");
                    alert.showAndWait();
                }
                else{
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Confirmation");
                    alert2.setHeaderText(null);
                    alert2.setContentText("You want to Pay ?");
                    Optional<ButtonType> option = alert2.showAndWait();
                    if (option.get().equals(ButtonType.OK)) {

                        LivreurDAO livreurDAO = new LivreurDAO();
                        long idLv = livreurDAO.getAvaible();
                        if (idLv != -1) {

                            DateTimeFormatter dtfr = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                            LocalDateTime now = LocalDateTime.now();
                            String dateNow = now.format(dtfr);
                            //New Receipt commande
                            CommandRcpDAO commandRcpDAO = new CommandRcpDAO();
                            CommandRcp cmdr = new CommandRcp(0l, TotalPrix, dateNow);
                            commandRcpDAO.save(cmdr);
                            //Delete all commands
                            CommandDAO commandDAO = new CommandDAO();
                            commandDAO.Empty();
                            //Some changes
                            UpdateTableCmd();
                            try {
                                CommandDAO c = new CommandDAO();
                                TotalPrix = c.TotalPrice();
                                TotalPriceField.setText(TotalPrix);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                            //Creat new delivery
                            DeliveryDAO deliveryDAO = new DeliveryDAO();
                            Delivery del = new Delivery(0l, AdresseTxt.getText(), "In progress", idLv);
                            deliveryDAO.save(del);
                            AdresseTxt.setText("");
                            UpdateTableDlv();
                            //successfull message
                            alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle("GOOD");
                            alert2.setHeaderText("Thank you Plz check Delivery");
                            alert2.showAndWait();
                        }

                        else{
                            alert2 = new Alert(Alert.AlertType.ERROR);
                            alert2.setTitle("SORRY");
                            alert.setHeaderText(null);
                            alert.setContentText("No Deliverer found plz wait");
                            alert2.showAndWait();
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void Clear() throws SQLException {
        Alert alert = null;
        // accees a la bdd
        if ( TableCmdview.getItems().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Nothing to clear");
            alert.showAndWait();

            }
        else {
            //Delete all commands
            Alert alert2= new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Confirmation");
            alert2.setHeaderText(null);
            alert2.setContentText("You want to Delete All Commands ?");
            Optional<ButtonType> option = alert2.showAndWait();

            if (option.get().equals(ButtonType.OK)){
                CommandDAO com = new CommandDAO();
                com.Empty();
                //Some changes
                UpdateTableCmd();
                try {
                    CommandDAO c = new CommandDAO();
                    TotalPrix= c.TotalPrice();
                    TotalPriceField.setText(TotalPrix);
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //Delivery Page
    @FXML
    private Button DeliveryBtn;
    @FXML
    private AnchorPane DeliveryView;
    @FXML
    private TextArea AdresseTxt;
    @FXML
    private TextField DelivererTxt;
    @FXML
    private TextField StatusTxt;
    @FXML
    private TextField TeleTxt;
    @FXML
    private TableView<Delivery> TableDlvview ;
    @FXML
    private TableColumn <Delivery ,String> AdressCol ;
    @FXML
    private TableColumn <Delivery ,String> DelivererCol ;
    @FXML
    private TableColumn <Delivery ,String> StatusCol ;
    @FXML
    private TableColumn <Delivery ,String> Telecol;
    @FXML
    private Button TrackBtn;


    public void UpdateTableDlv(){

        AdressCol.setCellValueFactory(new PropertyValueFactory<Delivery,String>("adresse")); ;
        DelivererCol.setCellValueFactory(new PropertyValueFactory<Delivery,String>("nom"));;
        Telecol.setCellValueFactory(new PropertyValueFactory<Delivery,String>("telephone"));;
        StatusCol.setCellValueFactory(new PropertyValueFactory<Delivery,String>("status"));;
        TableDlvview.setItems(this.getDataDlvView());

    }

    public static ObservableList<Delivery> getDataDlvView(){

        DeliveryDAO deliveryDAO = null;

        ObservableList<Delivery> listfx = FXCollections.observableArrayList();

        try {
            deliveryDAO = new DeliveryDAO();
            for (Delivery ettemp : deliveryDAO.getAll())
                listfx.add(ettemp);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx ;
    }
    private static Long LastSelectedDel;
    public void  Delivery_Select(MouseEvent mouseEvent){
        Delivery del = TableDlvview.getSelectionModel().getSelectedItem();
        int num = TableDlvview.getSelectionModel().getSelectedIndex();
        if((num - 1)<-1){
            return;
        }
        DelivererTxt.setText(del.getNom());
        StatusTxt.setText(del.getStatus());
        TeleTxt.setText(del.getTelephone());
        LastSelectedDel = del.getId_delivery();
    }

    public void OnDeliberedClick() {
        if (!(DelivererTxt.getText().isEmpty() || StatusTxt.getText().isEmpty() || TeleTxt.getText().isEmpty())){
            try {
                Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("This order has been Delivered ?");
                Optional<ButtonType> option3 = alert.showAndWait();

                if (option3.get().equals(ButtonType.OK)){
                    LivreurDAO livreurDAO = new LivreurDAO();
                    livreurDAO.DeliveredCmd(LastSelectedDel);
                    UpdateTableDlv();
                    Alert alert2= new Alert(Alert.AlertType.INFORMATION);
                    DelivererTxt.setText("");
                    StatusTxt.setText("");
                    TeleTxt.setText("");
                    alert2.setTitle("Confirmation");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Thank you");
                    alert2.showAndWait();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            Alert alert3= new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("Error");
            alert3.setHeaderText(null);
            alert3.setContentText("Select a Delivery !");
            alert3.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateTableCmd();
        UpdateTable();
        UpdateTableDlv();
        FoodBtn.setOnAction(event -> {
            switchForm(event);
        });
        PurchaseBtn.setOnAction(event -> {
            switchForm(event);
        });
        Exit.setOnAction(event -> {
            ExitFun(event);
        });
        LogOut.setOnAction(event -> {
            LogOut(event);
        });
        TableProdview.setOnMouseClicked(mouseEvent -> {
            Product_Select(mouseEvent);
        });
        TableDlvview.setOnMouseClicked(mouseEvent -> {
            Delivery_Select(mouseEvent);
        });
        FoodSpinner();
        try {
            CommandDAO c = new CommandDAO();
            TotalPrix= c.TotalPrice();
            TotalPriceField.setText(TotalPrix);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
