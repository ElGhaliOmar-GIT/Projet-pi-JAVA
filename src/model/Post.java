/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author El Ghali Omar
 */
public class Post {
    private int id;
    private int userId;
    private String titre;
    private String image;
    private String description;
    private String contenu;

    public Post() {
    }
    // Lel affichage khw
    public Post(int id, int userId, String titre, String image, String description, String contenu) {
        this.id = id;
        this.userId = userId;
        this.titre = titre;
        this.image = image;
        this.description = description;
        this.contenu = contenu;
    }
    // bech nasn3ou el classe
    public Post(int userId, String titre, String image, String description, String contenu) {
        this.userId = userId;
        this.titre = titre;
        this.image = image;
        this.description = description;
        this.contenu = contenu;
    }
    
    public Post(String titre, String image, String description, String contenu, int id) {
        this.id = id;
        this.titre = titre;
        this.image = image;
        this.description = description;
        this.contenu = contenu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", userId=" + userId + ", titre=" + titre + ", image=" + image + ", description=" + description + ", contenu=" + contenu +'}';
    }
}
