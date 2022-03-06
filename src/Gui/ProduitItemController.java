/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import interfaces.clickListenerProduit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Categorie;
import model.Produit;
import util.MaConnexion;

/**
 * FXML Controller class
 *
 * @author hadir
 */
public class ProduitItemController implements Initializable {

    @FXML
    private ImageView idPhoto;
    @FXML
    private Label idNomProduit;
    @FXML
    private Label idPrix;
    @FXML
    private Label idCategorie;
    @FXML
    private TextField idProduit;
    
    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(produit);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
}
     private Produit produit;
    private clickListenerProduit myListener;
    
    public void setData(Produit produit,clickListenerProduit myListener) throws FileNotFoundException {
        this.produit = produit;
        this.myListener= myListener;
        
        /* get categorie */
        String Categ = null;
        Connection cnx = MaConnexion.getInstance().getCnx();
        String req ="SELECT type FROM Categorie where idC = "+produit.getReference()+"";
        try {
            Statement st=cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()) {
                Categ = (rs.getString("type"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END */
        idProduit.setText(""+produit.getIdP());
        idCategorie.setText(Categ);
        idNomProduit.setText(produit.getNomProduit());
        idPrix.setText(""+produit.getPrix());
        // Image : Begin
        String path = produit.getImage();
        FileInputStream input = new FileInputStream(path);
        Image images = new Image(input);
        idPhoto.setImage(images);
        // Image : End
    }    
    
}
