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
import interfaces.clickListenerC;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import model.Commentaire;
import model.Post;
import service.ClientService;
import service.FournisseurService;
import service.LivreurService;
import service.SCommentaire;

/**
 * FXML Controller class
 *
 * @author El Ghali Omar
 */
public class CommentaireItemController implements Initializable {

    private Commentaire commentaire;
    private clickListenerC myListener;
    
    @FXML
    private Label nomUtilisateur;
    @FXML
    private TextArea contenuCommentaire;
    @FXML
    private Button modifierCommentaireBTN;
    @FXML
    private Button supprimerCommentaireBTN;
    @FXML
    private TextField hiddenId;
    @FXML
    private AnchorPane anch;
    @FXML
    private ImageView imageU;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifierCommentaire(ActionEvent event) {
        int opt=JOptionPane.showConfirmDialog(null, "voulez-vous confirmer la modification ?" , "Modification", JOptionPane.YES_NO_OPTION);
        if(opt==0) {
            SCommentaire sc = new SCommentaire();
            commentaire.setId(Integer.parseInt(hiddenId.getText()));
            commentaire.setContenu(contenuCommentaire.getText());
            sc.modifier(commentaire);

            myListener.onClickListener(commentaire);
        }
    }

    @FXML
    private void supprimerCommentaire(ActionEvent event) {
        int opt=JOptionPane.showConfirmDialog(null, "voulez-vous confirmer la supprition ?" , "Supprition", JOptionPane.YES_NO_OPTION);
        if(opt==0) {
            SCommentaire sc = new SCommentaire();
            sc.supprimer(commentaire.getId());

            myListener.onClickListener(commentaire);
        }
    }
    
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(commentaire);
    }
    
    public void setData(Commentaire commentaire, clickListenerC myListener, int connectedUserId, String nomUtil) throws FileNotFoundException {
        this.commentaire = commentaire;
        this.myListener = myListener;
        hiddenId.setText(""+commentaire.getId());
        contenuCommentaire.setText(commentaire.getContenu());
        nomUtilisateur.setText(commentaire.getCommentateur());
        if(connectedUserId == commentaire.getUserId()) {
            modifierCommentaireBTN.setVisible(true);
            supprimerCommentaireBTN.setVisible(true);
            contenuCommentaire.setEditable(true);
        } else {
            modifierCommentaireBTN.setVisible(false);
            supprimerCommentaireBTN.setVisible(false);
            contenuCommentaire.setEditable(false);
        }
    }
    
    public void setData(Commentaire commentaire, int connectedUserId) throws FileNotFoundException {
        this.commentaire = commentaire;
        hiddenId.setText(""+commentaire.getId());
        contenuCommentaire.setText(commentaire.getContenu());
        nomUtilisateur.setText(commentaire.getCommentateur());
        if(connectedUserId == commentaire.getUserId()) {
            modifierCommentaireBTN.setVisible(true);
            supprimerCommentaireBTN.setVisible(true);
            contenuCommentaire.setEditable(true);
        } else {
            modifierCommentaireBTN.setVisible(false);
            supprimerCommentaireBTN.setVisible(false);
            contenuCommentaire.setEditable(false);
        }
    }
}
