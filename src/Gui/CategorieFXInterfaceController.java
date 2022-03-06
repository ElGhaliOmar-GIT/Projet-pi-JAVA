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
import service.ClientService;
import service.FournisseurService;
import service.LivreurService;
import util.MaConnexion;

/**
 * FXML Controller class
 *
 * @author hadir
 */
public class CategorieFXInterfaceController implements Initializable {
    final FileChooser fc = new FileChooser();
    List<Categorie> C = new ArrayList<>();
    Categorie categorie = new Categorie();
    private clickListenerCatego myListener;
    String path;
    
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    @FXML
    private AnchorPane interfaceDaffichageCategorie;
    private AnchorPane affichageCat;
    @FXML
    private Text nomUtilisateur;
    @FXML
    private ImageView imageUtilsateur;
    
     private List<Categorie> getData(){
        Categorie categorie;
        Connection cnx = MaConnexion.getInstance().getCnx();
        String req ="SELECT * FROM Categorie";
        try {
            Statement st=cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()) {
                C.add(new Categorie(rs.getInt("idC"),rs.getString("type"),rs.getString("imageC")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       return C; 
       
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomUtilisateur.setText(username);
        ClientService cs = new ClientService();
        FournisseurService fs = new FournisseurService();
        LivreurService ls = new LivreurService();
        
        if (typecompte.equals("client")) {
            try {
                String path = client.getImage();
                FileInputStream input = new FileInputStream(path);
                Image image = new Image(input);
                imageUtilsateur.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (typecompte.equals("fournisseur")) {
            try {
                String path = fournisseur.getImage();
                FileInputStream input = new FileInputStream(path);
                Image image = new Image(input);
                imageUtilsateur.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (typecompte.equals("livreur")) {
            try {
                String path = livreur.getImage();
                FileInputStream input = new FileInputStream(path);
                Image image = new Image(input);
                imageUtilsateur.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        C.addAll(getData()); //teba3 laffichage w teba3 kol chy
         //select to edit
        if(C.size() > 0) {
            myListener = new clickListenerCatego() {
                @Override
                public void onClickListener(Categorie categorie) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLModifierSupprimerCategorie.fxml"));
                    try {
                        Parent root = loader.load();
                        FXMLModifierSupprimerCategorieController otherController = loader.getController();
                        otherController.setData(categorie, myListener);
                        interfaceDaffichageCategorie.getScene().setRoot(root);
                    } catch (IOException ex) {
                        Logger.getLogger(CategorieFXInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }
        //fin
        //affichage  
          
        int column = 0;
        int row = 1;
        try {
            for(int i = 0; i < C.size()/2; i++) {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("/Gui/CategorieItem.fxml"));
                AnchorPane anchorPane = fxmlloader.load();
                CategorieItemController itemController = fxmlloader.getController();
                itemController.setData(C.get(i),myListener);
                if(column == 2) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            Logger.getLogger(CategorieFXInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void ajouterCategorie(ActionEvent event) {
         AnchorPane cp;
            try {
                cp = FXMLLoader.load(getClass().getResource("FXMLAjouterCategorie.fxml"));
                interfaceDaffichageCategorie.getChildren().removeAll();
                interfaceDaffichageCategorie.getChildren().setAll(cp);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }

    @FXML
    private void retourauProduit(ActionEvent event) {
        AnchorPane cp;
            try {
                cp = FXMLLoader.load(getClass().getResource("ProduitFxInterface.fxml"));
                interfaceDaffichageCategorie.getChildren().removeAll();
                interfaceDaffichageCategorie.getChildren().setAll(cp);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }

    @FXML
    private void statique(ActionEvent event) {
         AnchorPane cp;
            try {
                cp = FXMLLoader.load(getClass().getResource("Statisqtique.fxml"));
                interfaceDaffichageCategorie.getChildren().removeAll();
                interfaceDaffichageCategorie.getChildren().setAll(cp);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }

    @FXML
    private void seDeconnecter(ActionEvent event) {
        try {
            AnchorPane connect = FXMLLoader.load(getClass().getResource("Connect.fxml"));
            interfaceDaffichageCategorie.getChildren().removeAll();
            interfaceDaffichageCategorie.getChildren().setAll(connect);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
