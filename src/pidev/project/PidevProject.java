/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.project;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import model.Client;
import model.Fournisseur;
import model.Livreur;
import util.MaConnexion;
import service.ClientService;
import service.Connect;
import service.Countries;
import service.FournisseurService;
import service.HistoriqueService;
import service.LivreurService;
import service.PasswordService;
import service.UtilisateurService;

/**
 *
 * @author hasse
 */
public class PidevProject {

    public static void main(String[] args) throws Exception {

        String attribute = "nom";
        String newValue = "foulen";
        Connection cnx = MaConnexion.getInstance().getCnx();
        System.out.println("connexion établie avec succes");
        System.out.println("______________________________________________________");
        UtilisateurService us = new UtilisateurService();
        ClientService cs = new ClientService();
        FournisseurService fs = new FournisseurService();
        LivreurService ls = new LivreurService();
        Client cl = new Client(50, "riadh", "chibeni", "riadh@gmail.com", 52385909, "C:\\Users\\hasse\\OneDrive\\Bureau\\hassen\\1367356-meghan-markle-dans-la-saison-7-de-suits.jpg", "Tunisie", "tunis", "cmlhZGg=", "client");

        Fournisseur fr = new Fournisseur(7, "Riadh", "Chibeni", "RiadhChibni@gmail.com", 52385909, "imageriadh", "Tunis", "Tunis", "riadhchb", "fournisseur");
        Livreur lv = new Livreur(7, "James", "King", "JamesKing@hotmail.com", 253612589, "imagejames", "usa", "Texas", "James", "livreur", "Phones");

        Connect m = new Connect();
        //boolean verify = m.verify("MalekFitouri@gmail.com", "malek");
        PasswordService ps = new PasswordService();
        HistoriqueService hs=new HistoriqueService();
        

        // us.banUtilisateur("riadh@gmail.com", 5);
        //System.out.println(us.compareDate("rachel@gmail.com"));
        //ls.ajouterLivreur(lv);
        //ls.modifierLivreur(lv);
        //ls.supprimerLivreur(lv);
        //fs.ajouterFournisseur(fr);
        //fs.modifierFournisseur(fr);
        //fs.supprimerFournisseur(fr);
        //cs.ajouterClient(cl);
        //cs.modifierClient(cl);
        //cs.supprimerClient(cl);
        /* MacAddress mc = new MacAddress();
        System.out.println(mc.getMacAddress().toString());*/ /* Mail m = new Mail();
        System.out.println(m.verify("viper@gmail.com", "steph123 "));*/ //System.out.println(cs.getIdByEmail("viper@gmail.com"));
        //System.out.println(cs.selectClientByEmail("viper@gmail.com"));
        // cs.afficherClient();
        //ForgetPassword fp = new ForgetPassword();
        //fp.modifypassword("raidh@gmail.com", "password");
        /* int code = fp.generateCode();
        fp.insertCodeInDB("raidh@gmail.com", code);
        MailingService ms = new MailingService();
        ms.sendMail("hassenbenadel37@gmail.com", code);*/ //System.out.println(fp.VerifyCode(757, "raidh@gmail.com"));
        //HistoriqueService hs = new HistoriqueService();
        //System.out.println(hs.getIpAdress());
        //ms.sendMail("hassenbenadel37@gmail.com");
    }

}
