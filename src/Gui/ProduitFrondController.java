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
public class ProduitFrondController implements Initializable {
    final FileChooser fc = new FileChooser();
    List<Produit> p = new ArrayList<>();
    List<Categorie> C = new ArrayList<>();
    Produit produit = new Produit();
    private clickListenerProduit myListener;
    private clickListenerCatego myListenerC;
    String path;
    int userId;

    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll2;
    @FXML
    private GridPane grid2;
    public AnchorPane CategorieEtProduit;
    @FXML
    private AnchorPane CatEtProd;
    @FXML
    public AnchorPane prodList;
    @FXML
    private Text nomUtilisateur;
    @FXML
    private ImageView imageUtilisateur;

     private List<Produit> getData(){
        Produit produit;
        Connection cnx = MaConnexion.getInstance().getCnx();
        String req ="SELECT * FROM Produit";
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
     
      private List<Categorie> getDataC(){
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
         setData();
    }     
    
    public void setData() {
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
        p.addAll(getData()); //teba3 laffichage w teba3 kol chy 
      //select to edit
        if(p.size() > 0) {
            myListener = new clickListenerProduit() {
                public void onClickListener(Produit produit) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ProduitFrondItem.fxml"));
                    try {
                        Parent root = loader.load();
                        setData();
                        grid2.getScene().setRoot(root);
                    } catch (IOException ex) {
                        Logger.getLogger(ProduitFrondController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }
        //fin
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
                grid2.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            Logger.getLogger(ProduitFxInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Categorie
         C.addAll(getDataC()); //teba3 laffichage w teba3 kol chy
         //select to edit
        if(C.size() > 0) {
            myListenerC = new clickListenerCatego() {
                @Override
                public void onClickListener(Categorie categorie) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ProduitParCat.fxml"));
                    try {
                        Parent root = loader.load();
                        ProduitParCatController otherController = loader.getController();
                        otherController.setData(categorie, myListenerC);
                        grid2.getScene().setRoot(root);
                    } catch (IOException ex) {
                        Logger.getLogger(ProduitFrondController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }
        //fin
        //affichage  
          
        int columnC = 0;
        int rowC = 1;
        try {
            for(int i = 0; i < C.size()/2; i++) {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("/Gui/CategorieItem.fxml"));
                AnchorPane anchorPane = fxmlloader.load();
                CategorieItemController itemController = fxmlloader.getController();
                itemController.setData(C.get(i),myListenerC);
                grid.add(anchorPane, columnC++, rowC);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            Logger.getLogger(ProduitFrondController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void refresh(ActionEvent event) {
         AnchorPane cp;
            try {
                cp = FXMLLoader.load(getClass().getResource("ProduitFrond.fxml"));
                CatEtProd.getChildren().removeAll();
                CatEtProd.getChildren().setAll(cp);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
    
    
    
    
}
