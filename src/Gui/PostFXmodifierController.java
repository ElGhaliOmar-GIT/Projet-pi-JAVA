/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import interfaces.clickListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import model.Post;
import service.SPost;
import util.MaConnexion;

/**
 * FXML Controller class
 *
 * @author El Ghali Omar
 */
public class PostFXmodifierController implements Initializable {
    
    final FileChooser fc = new FileChooser();
    String path;
    private Post post;
    private clickListener myListener;

    @FXML
    private TextField titre;
    @FXML
    private TextArea description;
    @FXML
    private TextArea contenu;
    @FXML
    private Button modifierPost;
    @FXML
    private ImageView imageBrowsed;
    @FXML
    private Label choisirImageLabel;
    @FXML
    private TextField hiddenId;
    @FXML
    private TextField hiddenPath;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Label titreWarning;
    @FXML
    private Label descWarning;
    @FXML
    private Label contenuWarning;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void validerModification(ActionEvent event) {
        boolean isTitre = false, isDesc = false, isContenu = false;
        String sTitre = titre.getText();
        String sDesc = description.getText();
        String sContenu = contenu.getText();
        if(titre.getText().isEmpty()) {
            titreWarning.setVisible(true);
            isTitre = false;
        } else {
            titreWarning.setVisible(false);
            isTitre = true;
        }
        if(description.getText().isEmpty()) {
            descWarning.setVisible(true);
            isDesc = false;
        } else {
            descWarning.setVisible(false);
            isDesc = true;
        }
        if(contenu.getText().isEmpty()) {
            contenuWarning.setVisible(true);
            isContenu = false;
        } else {
            contenuWarning.setVisible(false);
            isContenu = true;
        }
        if(isContenu && isDesc && isTitre) {
            int opt=JOptionPane.showConfirmDialog(null, "voulez-vous confirmer la modification ?" , "Modification", JOptionPane.YES_NO_OPTION);
            if(opt==0) {
                Post post = new Post();
                post.setId(Integer.parseInt(hiddenId.getText()));
                post.setTitre(titre.getText());
                post.setDescription(description.getText());
                post.setContenu(contenu.getText());
                path = hiddenPath.getText();
                String sPath = path;
                sPath = sPath.replace("\\", "\\\\");
                post.setImage(sPath);
                SPost sp = new SPost();
                sp.modifier(post);

                /* Redirect to myList : BEGIN */
                AnchorPane cp;
                try {
                    cp = FXMLLoader.load(getClass().getResource("BlogFXmyList.fxml"));
                    anchor.getChildren().removeAll();
                    anchor.getChildren().setAll(cp);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
           }
        }
    }

    @FXML
    private void browseImage(MouseEvent event) throws FileNotFoundException {
        File file = fc.showOpenDialog(null);
        path = file.getAbsolutePath();
        FileInputStream input = new FileInputStream(path);
        Image image = new Image(input);
        imageBrowsed.setImage(image);
        hiddenPath.setText(path);
    }

    @FXML
    private void annulerModificationPost(ActionEvent event) {
        /* Redirect to myList : BEGIN */
        AnchorPane cp;
        try {
            cp = FXMLLoader.load(getClass().getResource("BlogFXmyList.fxml"));
            anchor.getChildren().removeAll();
            anchor.getChildren().setAll(cp);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        /* END */
    }
    
    @FXML
    private void supprimerPost(ActionEvent event) {
        int opt=JOptionPane.showConfirmDialog(null, "voulez-vous confirmer la supprition ?" , "Supprition", JOptionPane.YES_NO_OPTION);
        if(opt==0) {
            Post post = new Post();
            post.setId(Integer.parseInt(hiddenId.getText()));
            SPost sp = new SPost();
            sp.supprimer(post.getId());

            /* Redirect to myList : BEGIN */
            AnchorPane cp;
            try {
                cp = FXMLLoader.load(getClass().getResource("BlogFXmyList.fxml"));
                anchor.getChildren().removeAll();
                anchor.getChildren().setAll(cp);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void setData(Post post, clickListener myListener) throws FileNotFoundException {
        this.post = post;
        this.myListener = myListener;
        hiddenId.setText(""+post.getId());
        setNewData();
    }
    
    private void setNewData() throws FileNotFoundException {
        Post post = new Post();
        SPost sp = new SPost();
        post = sp.afficherParId(Integer.parseInt(hiddenId.getText()));
        titre.setText(post.getTitre());
        description.setText(post.getDescription());
        contenu.setText(post.getContenu());
        hiddenPath.setText(post.getImage());
        // Image : Begin
        String path = post.getImage();
        FileInputStream input = new FileInputStream(path);
        Image images = new Image(input);
        imageBrowsed.setImage(images);
        // Image : End
    }
}
