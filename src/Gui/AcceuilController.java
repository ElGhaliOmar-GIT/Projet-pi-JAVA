/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import static Gui.ConnectController.client;
import static Gui.ConnectController.fournisseur;
import static Gui.ConnectController.livreur;
import static Gui.ConnectController.typecompte;
import static Gui.ConnectController.username;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author El Ghali Omar
 */
public class AcceuilController implements Initializable {

    @FXML
    private ImageView imageUtilisateur;
    @FXML
    private Label nomUtilisateur;
    @FXML
    private AnchorPane anchor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomUtilisateur.setText(username);
        if (typecompte.equals("client")) {
            try {
                String path = client.getImage();
                FileInputStream input = new FileInputStream(path);
                Image image = new Image(input);
                imageUtilisateur.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (typecompte.equals("fournisseur")) {
            try {
                String path = fournisseur.getImage();
                FileInputStream input = new FileInputStream(path);
                Image image = new Image(input);
                imageUtilisateur.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (typecompte.equals("livreur")) {
            try {
                String path = livreur.getImage();
                FileInputStream input = new FileInputStream(path);
                Image image = new Image(input);
                imageUtilisateur.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void goToBlogPage(ActionEvent event) throws IOException {
        AnchorPane goToBlog = FXMLLoader.load(getClass().getResource("BlogFXlist.fxml"));
        anchor.getChildren().removeAll();
        anchor.getChildren().setAll(goToBlog);
    }

    @FXML
    private void goToProduitPage(ActionEvent event) throws IOException {
        AnchorPane goToBlog = FXMLLoader.load(getClass().getResource("ProduitFrond.fxml"));
        anchor.getChildren().removeAll();
        anchor.getChildren().setAll(goToBlog);
    }

}
