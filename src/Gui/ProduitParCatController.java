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
import interfaces.clickListenerProduit;
import interfaces.clickListenerCatego;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.Categorie;
import model.Produit;
import service.ClientService;
import service.FournisseurService;
import service.LivreurService;
import util.MaConnexion;

/**
 * FXML Controller class
 *
 * @author hadir
 */
public class ProduitParCatController implements Initializable {

    @FXML
    private AnchorPane produitParCategorie;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
      final FileChooser fc = new FileChooser();
    List<Produit> p = new ArrayList<>();
    Produit produit = new Produit();
    private clickListenerProduit myListener;
    String path;
    int userId=3;
    @FXML
    private ImageView imageUtilisateur;
    @FXML
    private Text nomUtilisateur;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Categorie categorie = new Categorie();
        categorie.setIdC(13);
        setData(categorie, myListenerC);*/
    }    

    void setData(Categorie categorie, clickListenerCatego myListenerC) {
        nomUtilisateur.setText(username);
        ClientService cs = new ClientService();
        FournisseurService fs = new FournisseurService();
        LivreurService ls = new LivreurService();
        if (typecompte.equals("client")) {
            userId = cs.getIduserByIdClient(client);
        } else if (typecompte.equals("fournisseur")) {
            userId = fs.getIduserByIdFournisseur(fournisseur);
        } else if (typecompte.equals("livreur")) {
            userId = ls.getIduserByIdLivreur(livreur);
        }
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
        System.out.println(categorie);
        p.addAll(getData(categorie.getIdC()));
         int column = 0;
        int row = 1;
        try {
            for(int i = 0; i < p.size()/2; i++) {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("/Gui/ProduitFrondItem.fxml"));
                AnchorPane anchorPane = fxmlloader.load();
                ProduitFrondItemController itemController = fxmlloader.getController();
                itemController.setData(p.get(i),myListener,userId);
                if(column == 2) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            Logger.getLogger(ProduitFxInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     private List<Produit> getData(int idC){
        Produit produit;
        Connection cnx = MaConnexion.getInstance().getCnx();
       String req ="SELECT * FROM Produit where reference="+idC+"";
        try {
            Statement st=cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()) {
                p.add(new Produit(rs.getInt("idP"),rs.getInt("reference"), rs.getString("nomProduit"), rs.getInt("Quantite"), rs.getInt("prix"), rs.getString("image")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       return p; 
       
    }

    @FXML
    private void back(ActionEvent event) {
          AnchorPane cp;
            try {
                cp = FXMLLoader.load(getClass().getResource("ProduitFrond.fxml"));
                produitParCategorie.getChildren().removeAll();
                produitParCategorie.getChildren().setAll(cp);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
}
