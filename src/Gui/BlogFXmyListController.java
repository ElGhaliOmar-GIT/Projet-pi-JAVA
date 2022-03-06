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
import interfaces.clickListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import model.Post;
import service.ClientService;
import service.FournisseurService;
import service.LivreurService;
import util.MaConnexion;

/**
 * FXML Controller class
 *
 * @author El Ghali Omar
 */
public class BlogFXmyListController implements Initializable {
    
    final FileChooser fc = new FileChooser();
    List<Post> posts = new ArrayList<>();
    Post post = new Post();
    private clickListener myListener;
    int utilisateurConnecte;
    String path;
    
    @FXML
    private GridPane grid;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Label lilWarningLabel;
    @FXML
    private ImageView imageUtilisateur;
    @FXML
    private Label nomUtilisateur;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Get User */
        nomUtilisateur.setText(username);
        ClientService cs = new ClientService();
        FournisseurService fs = new FournisseurService();
        LivreurService ls = new LivreurService();
        if (typecompte.equals("client")) {
            utilisateurConnecte = cs.getIduserByIdClient(client);
        } else if (typecompte.equals("fournisseur")) {
            utilisateurConnecte = fs.getIduserByIdFournisseur(fournisseur);
        } else if (typecompte.equals("livreur")) {
            utilisateurConnecte = ls.getIduserByIdLivreur(livreur);
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
        /* END */
        
        posts.addAll(getData(utilisateurConnecte));
        if(posts.size() == 0) {
            lilWarningLabel.setVisible(true);
        } else {
            lilWarningLabel.setVisible(false);
        }
        if(posts.size() > 0) {
            myListener = new clickListener() {
                @Override
                public void onClickListener(Post post) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("PostFXmodifier.fxml"));
                    try {
                        Parent root = loader.load();
                        PostFXmodifierController otherController = loader.getController();
                        otherController.setData(post, myListener);
                        anchor.getScene().setRoot(root);
                    } catch (IOException ex) {
                        Logger.getLogger(BlogFXmyListController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for(int i = 0; i < posts.size()/2; i++) {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("/Gui/MyBlogItem.fxml"));
                AnchorPane anchorPane = fxmlloader.load();
                MyBlogItemController itemController = fxmlloader.getController();
                itemController.setData(posts.get(i), myListener);
                if(column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            Logger.getLogger(BlogFXmyListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    private void backToBlogList(ActionEvent event) {
        AnchorPane cp;
        try {
            cp = FXMLLoader.load(getClass().getResource("BlogFXlist.fxml"));
            anchor.getChildren().removeAll();
            anchor.getChildren().setAll(cp);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void goToAjouterBlogBTN(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PostFXajouter.fxml"));
        try {
            Parent root = loader.load();
            PostFXajouterController acd = loader.getController();
            anchor.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(BlogFXmyListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<Post> getData(int userId){
        Post post;
        Connection cnx = MaConnexion.getInstance().getCnx();
        String req = "SELECT * FROM post WHERE userId = "+userId+"";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()) {
                posts.add(new Post(rs.getInt("id"), rs.getInt("userId"), rs.getString("titre"), rs.getString("image"), rs.getString("description"), rs.getString("contenu")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return posts;
    }
    
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(post);
    }
}
