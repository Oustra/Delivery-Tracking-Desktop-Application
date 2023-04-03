package ma.fstt.trackingl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ma.fstt.model.Admin;
import ma.fstt.model.AdminDAO;
import ma.fstt.model.User;
import ma.fstt.model.UserDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class LoginController implements Initializable{

    //Form Admin-------------------------------------------------------------------------
    @FXML
    private Pane Admin_form;
    @FXML
    private TextField username ;

    @FXML
    private PasswordField password;

    @FXML
    private Label ErrTxt;

    @FXML
    private Button signInButton;

    @FXML
    private Hyperlink Admin_Link;

    @FXML
    private Button ExitBtn;

    //Form User-------------------------------------------------------------------------
    @FXML
    private Pane User_form;

    @FXML
    private TextField username1 ;

    @FXML
    private PasswordField password1;

    @FXML
    private Label ErrTxt1;

    @FXML
    private Button signInButton1;

    @FXML
    private Hyperlink User_Link;

    public LoginController() {
    }

    public void onSignInButtonClick(ActionEvent event) {

        // Accees a la bdd

        try {
            Alert alert;
            if (username.getText().isEmpty() || password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            else {
                AdminDAO adminDAO = new AdminDAO();
                Admin ad = new Admin( username.getText() , password.getText());

                if( adminDAO.isLogin(ad) ) {
                    Parent homePageParent = FXMLLoader.load(getClass().getResource("Home-view.fxml"));
                    Scene homePageScene = new Scene(homePageParent);
                    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    appStage.setScene(homePageScene);
                    appStage.show();
                }
                else{
                    ErrTxt.setText("Incorrect user name or password");
                }
            }

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onUserSignInButtonClick(ActionEvent event) {

        // Accees a la bdd

        try {
            Alert alert;
            if (username1.getText().isEmpty() || password1.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            else {
                UserDAO userDAO = new UserDAO();
                User us = new User( username1.getText() , password1.getText());

                if( userDAO.isLoginUser(us) ) {

                    Parent homePageParent = FXMLLoader.load(getClass().getResource("HomeUser-view.fxml"));
                    Scene homePageScene = new Scene(homePageParent);
                    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    appStage.setScene(homePageScene);
                    appStage.show();
                }
                else{
                    ErrTxt1.setText("Incorrect user name or password");
                }
            }

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleClose(ActionEvent event) {
        Stage stage = (Stage) ExitBtn.getScene().getWindow();
        stage.close();
    }

    public void switchForm(ActionEvent event){
        if(event.getSource()== User_Link){
            Admin_form.setVisible(true);
            User_form.setVisible(false);
        }
        else if (event.getSource() == Admin_Link){
            User_form.setVisible(true);
            Admin_form.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signInButton.setOnAction(event -> {
            onSignInButtonClick(event);
        });
        ExitBtn.setOnAction(event -> {
            handleClose(event);
        });
        signInButton1.setOnAction(event -> {
            onUserSignInButtonClick(event);
        });


    }

}
