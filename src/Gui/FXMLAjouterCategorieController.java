/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import model.Categorie;
import service.categorieService;

/**
 * FXML Controller class
 *
 * @author hadir
 */
public class FXMLAjouterCategorieController implements Initializable {
    final FileChooser fc = new FileChooser();
    @FXML
    private TextField idC;
    @FXML
    private TextField type;
    @FXML
    private ImageView FxPhoto;
    @FXML
    private AnchorPane interfaceDajoutC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    String path;
    @FXML
    private void ajouterPhoto(MouseEvent event) throws FileNotFoundException {
        File file = fc.showOpenDialog(null);
        path = file.getAbsolutePath();
        FileInputStream input = new FileInputStream(path);
        Image image = new Image(input);
        FxPhoto.setImage(image);
    }

    @FXML
    private void ajoutercategorie(ActionEvent event) {
        int opt=JOptionPane.showConfirmDialog(null, "voulez-vous confirmer l'ajout ?" , "Ajout", JOptionPane.YES_NO_OPTION);
        if(opt==0) {
            String types =type.getText();
            String sPath = path;
            sPath = sPath.replace("\\", "\\\\");
            Categorie c = new Categorie(types,sPath);
            System.out.println(c.getType());
            System.out.println(c.getImageC());
            categorieService Cs = new categorieService();
            Cs.ajouterCategorie(c);
            AnchorPane cp;
            try {
                cp = FXMLLoader.load(getClass().getResource("CategorieFXInterface.fxml"));
                interfaceDajoutC.getChildren().removeAll();
                interfaceDajoutC.getChildren().setAll(cp);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void annulerAjout(ActionEvent event) {
        int opt=JOptionPane.showConfirmDialog(null, "voulez-vous anuuler l'ajout ?" , "Ajout", JOptionPane.YES_NO_OPTION);
         if(opt==0) {
         AnchorPane cp;
            try {
                cp = FXMLLoader.load(getClass().getResource("CategorieFXInterface.fxml"));
                interfaceDajoutC.getChildren().removeAll();
                interfaceDajoutC.getChildren().setAll(cp);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
    }
    
}
