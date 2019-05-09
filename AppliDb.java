
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.sql.*;
import javafx.scene.control.ComboBox;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jesus
 */
public class AppliDb extends Application
{

    private static int rs;
    public static void main(String[] args)throws ClassNotFoundException,SQLException,IllegalAccessException,InstantiationException
    {
            launch(args);
    }
    public static int  insertData(String nom,String adresse) throws Exception
    {
            Class.forName("com.mysql.jdbc.Driver");
            String hote = "jdbc:mysql://localhost:3306/stock_db";
            String user = "root";
            String pssw = "";
            String requete = ("INSERT INTO Client(nomClient,adresse,type) VALUES('"+nom+"','"+adresse+"','"+"vip"+"')");
             try
             {
                Connection conn = DriverManager.getConnection(hote,user,pssw);
                Statement stm = conn.createStatement();
                int rs = stm.executeUpdate(requete);
                System.out.println(rs);
             }
             catch(SQLException e)
             {
                 e.printStackTrace();
             }
             return rs;
    }
    
    public static String selectData()throws Exception
    {
            Class.forName("com.mysql.jdbc.Driver");
            String hote = "jdbc:mysql://localhost:3306/stock_db";
            String user = "root";
            String pssw = "";
            String requete = ("SELECT * FROM Client");
            try
            {
                Connection conn = DriverManager.getConnection(hote,user,pssw);
                Statement etat = conn.createStatement();
                ResultSet rs = etat.executeQuery(requete);
                String nom = null,adresse,type;
                int id;
                while(rs.next())
                {
                    nom = rs.getString("nomClient");
                    adresse = rs.getString("adresse");
                    type = rs.getString("type");
                    id = rs.getInt("id");
                    System.out.println(id+" "+nom+" "+adresse+" "+" "+type);
                }
                return nom;
            }
            catch(Exception e){}
            return null;
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("AdminApp");
        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,700,400);
        primaryStage.setScene(scene);
        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(12));
        hbox1.setSpacing(10);
        Label label1 = new Label("Login");
        Label label2 = new Label("PassWord");
        TextField text1 = new TextField();
        TextField text2 = new TextField();
        Button btn1 = new Button("Valider");
        Button btn2 = new Button("Select");
        hbox1.getChildren().addAll(label1,text1,label2,text2,btn1,btn2);
        
        VBox vbox1 = new VBox();
        ListView<String> liste = new ListView<>();
        liste.getItems().add("Bonjour a tous");
        vbox1.getChildren().addAll(liste);
        vbox1.setPadding(new Insets(10));
        root.setCenter(vbox1);
        root.setTop(hbox1);
        VBox vbox2 = new VBox();
        ComboBox<String> cmb = new ComboBox<String>();
        cmb.getItems().add("1");
        cmb.getItems().add("2");
        cmb.getItems().add("3");
        cmb.getItems().add("4");
        cmb.getItems().add("5");
        vbox2.getChildren().addAll(cmb);
        vbox2.setPadding(new Insets(10));
        root.setBottom(vbox2);
        primaryStage.show();
        
        //Event
        
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String login = text1.getText();
                String pssword = text2.getText();
                String valeur = cmb.getValue();
                System.out.println(login);
                System.out.println(pssword);
                try
                {
                    int value = insertData(login,pssword);
                    System.out.println(value);
                }
                catch (Exception ex)
                {
                    Logger.getLogger(AppliDb.class.getName()).log(Level.SEVERE, null, ex);
                }
                liste.getItems().addAll(login,pssword,valeur);
            }
        });
        btn2.setOnAction((ActionEvent e) -> {
            try
            {
                System.out.println("LA LISTE POUR CLIENT");
                System.out.println("--------------------");
                String chaine = selectData();
            }
            catch(Exception ex)
            {
                Logger.getLogger(AppliDb.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
}
