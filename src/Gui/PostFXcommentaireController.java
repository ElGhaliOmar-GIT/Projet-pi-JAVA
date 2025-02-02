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
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import com.restfb.types.User;
import interfaces.clickListener;
import interfaces.clickListenerC;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import model.Commentaire;
import model.Post;
import service.ClientService;
import service.FournisseurService;
import service.LivreurService;
import service.SCommentaire;
import service.SPost;
import service.UtilisateurService;
import util.MaConnexion;

/**
 * FXML Controller class
 *
 * @author El Ghali Omar
 */
public class PostFXcommentaireController implements Initializable {
    
    final FileChooser fc = new FileChooser();
    List<Commentaire> commentaires = new ArrayList<>();
    private Commentaire commentaire;
    private Post post;
    private SPost sp = new SPost();
    private clickListener myListener;
    private clickListenerC myListenerC;
    String path;
    int utilisateurConnecte;
    int testadmin;
    UtilisateurService us = new UtilisateurService();
    
    @FXML
    private AnchorPane anchor;
    @FXML
    private ImageView image;
    @FXML
    private Label titre;
    @FXML
    private Label description;
    @FXML
    private TextArea contenu;
    @FXML
    private Label nomUtilisateur;
    @FXML
    private TextArea contenuCommentaire;
    @FXML
    private Button ajouterCommentaireBTN;
    @FXML
    private TextField hiddenId;
    @FXML
    private GridPane grid;
    @FXML
    private TextField ratingTF;
    @FXML
    private Label note;
    @FXML
    private Label noteWarning;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Get User */
        ClientService cs = new ClientService();
        FournisseurService fs = new FournisseurService();
        LivreurService ls = new LivreurService();
        if (typecompte.equals("client")) {
            utilisateurConnecte = cs.getIduserByIdClient(client);
            testadmin = us.getTestAdmin(client.getEmail());
        } else if (typecompte.equals("fournisseur")) {
            utilisateurConnecte = fs.getIduserByIdFournisseur(fournisseur);
            testadmin = us.getTestAdmin(fournisseur.getEmail());
        } else if (typecompte.equals("livreur")) {
            utilisateurConnecte = ls.getIduserByIdLivreur(livreur);
            testadmin = us.getTestAdmin(livreur.getEmail());
        }
        /* END */
    }    
    
    public void setData(Post post) throws FileNotFoundException {
        this.post = post;
        hiddenId.setText(""+post.getId());
        setNewData();
    }
    
    public void setData(int postId) throws FileNotFoundException {
        hiddenId.setText(""+postId);
        setNewData();
    }
    
    public void setData(Post post, clickListener myListener) throws FileNotFoundException {
        this.post = post;
        this.myListener = myListener;
        hiddenId.setText(""+post.getId());
        setNewData();
    }
    
    private void setNewData() throws FileNotFoundException {
        
        Post post = new Post();
        post = sp.afficherParId(Integer.parseInt(hiddenId.getText()));
        titre.setText(post.getTitre());
        description.setText(post.getDescription());
        contenu.setText(post.getContenu());
        nomUtilisateur.setText(username);
        int ttRating = sp.getTotal(Integer.parseInt(hiddenId.getText()));
        note.setText(""+ttRating);
        // Image : Begin
        String path = post.getImage();
        FileInputStream input = new FileInputStream(path);
        Image images = new Image(input);
        image.setImage(images);
        // Image : End
        
        commentaires.addAll(getData());
        if(commentaires.size() > 0) {
            myListenerC = new clickListenerC() {
                @Override
                public void onClickListener(Commentaire commentaire) {
                    Post post = new Post();
                    post.setId(Integer.parseInt(hiddenId.getText()));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("PostFXcommentaire.fxml"));
                    try {
                        Parent root = loader.load();
                        PostFXcommentaireController otherController = loader.getController();
                        otherController.setData(post);
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
            for(int i = 0; i < commentaires.size()/2; i++) {
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("/Gui/CommentaireItem.fxml"));
                AnchorPane anchorPane = fxmlloader.load();
                CommentaireItemController itemController = fxmlloader.getController();
                itemController.setData(commentaires.get(i), myListenerC, utilisateurConnecte, username);
                if(column == 1) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            Logger.getLogger(PostFXcommentaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<Commentaire> getData(){
        Connection cnx = MaConnexion.getInstance().getCnx();
        String req = "SELECT * FROM commentaire WHERE idPost = "+Integer.parseInt(hiddenId.getText())+"";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()) {
                commentaires.add(new Commentaire(rs.getInt("id"), rs.getInt("idPost"), rs.getInt("userId"), rs.getString("commentateur"), rs.getString("contenu")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return commentaires;
    }

    @FXML
    private void ajouterCommentaire(ActionEvent event) throws FileNotFoundException {
        Commentaire commentaire = new Commentaire();
        commentaire.setUserId(utilisateurConnecte);
        commentaire.setCommentateur(username);
        commentaire.setIdPost(Integer.parseInt(hiddenId.getText()));
        commentaire.setContenu(contenuCommentaire.getText());
        SCommentaire sc = new SCommentaire();
        sc.ajouter(commentaire);
        
        post.setId(Integer.parseInt(hiddenId.getText()));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PostFXcommentaire.fxml"));
        try {
            Parent root = loader.load();
            PostFXcommentaireController otherController = loader.getController();
            otherController.setData(post);
            anchor.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(BlogFXmyListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void backToBlogList(ActionEvent event) {
        /* Redirect to myList : BEGIN */
        AnchorPane cp;
        try {
            cp = FXMLLoader.load(getClass().getResource("BlogFXlist.fxml"));
            anchor.getChildren().removeAll();
            anchor.getChildren().setAll(cp);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        /* END */
    }

    @FXML
    private void shareFB(ActionEvent event) {
        String accessToken = "EAAJPFAOpaD8BAEjcSLpUQ0SBAlMb1foTYjzXgXeDagfEZA2TmAjO2ZA2s3iH7tWOpqJ6XT"
                + "j0GiZBK5pwr2ZCPo7U21RFVAwGz3VDEHSj0dGvuypn80ZC7M0XJZCJAVsYgbNFC3dla8pZBbZA5oSl9Wdv"
                + "GH9f9APNzH3u2KJ1brb5nQMtpLW5OwanO1ytufLKJCTACZAC5meU4oV8cAfiag0wc";
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.UNVERSIONED);
        FacebookType response = fbClient.publish("me/feed", FacebookType.class, Parameter.with("message", post.getTitre() + " : " + post.getDescription()
                + "\nAllez voir le dernier blog sur notre application!!"));
        System.out.println("fb.com/"+response.getId());
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Le Blog est ajoute avec succes! \n fb.com/"+response.getId()+"");

        alert.showAndWait();
    }

    @FXML
    private void noterPost(ActionEvent event) {
        boolean isNote = false;
        int noteTest = Integer.parseInt(ratingTF.getText());
        if(ratingTF.getText().isEmpty() || noteTest > 10 || noteTest < 0) {
            noteWarning.setVisible(true);
            isNote = false;
        } else {
            noteWarning.setVisible(false);
            isNote = true;
        }
        if(isNote) {
            int verif = sp.verifier(utilisateurConnecte, Integer.parseInt(hiddenId.getText()));
            if(verif == 0) {
                sp.addRating(utilisateurConnecte, Integer.parseInt(hiddenId.getText()), Integer.parseInt(ratingTF.getText()));
            } else {
                sp.updateRating(utilisateurConnecte, Integer.parseInt(hiddenId.getText()), Integer.parseInt(ratingTF.getText()));
            }
            post.setId(Integer.parseInt(hiddenId.getText()));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PostFXcommentaire.fxml"));
            try {
                Parent root = loader.load();
                PostFXcommentaireController otherController = loader.getController();
                otherController.setData(post);
                anchor.getScene().setRoot(root);
            } catch (IOException ex) {
                Logger.getLogger(BlogFXmyListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
