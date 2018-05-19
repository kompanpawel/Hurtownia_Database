package hurtownia.controller;

import hurtownia.dbUtil.dbConnection;
import hurtownia.model.InsertModel;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class InsertController {

    private dbConnection dC;
    private ObservableList <InsertModel> klientList;

    @FXML
    private ComboBox<InsertZamOption> choiceZam;

    @FXML
    private ComboBox <InsertModel> choiceKlient;

    @FXML
    private TextField imie;
    @FXML
    private TextField nazwisko;
    @FXML
    private TextField id;
    @FXML
    private TextField ilosc;

    @FXML
    private Button button;


    private String sqlKlient = "Select k.Imię, k.Nazwisko From mydb.klienci k ";

    public void initialize() throws SQLException {
        this.choiceZam.setItems(FXCollections.observableArrayList(InsertZamOption.values()));
        fillComboBox1();
    }

    public void fillComboBox1() throws SQLException {
        try {
            Connection con = dbConnection.getConnection();
            this.klientList = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery(sqlKlient);
            this.klientList.add(new InsertModel("Jestem","nowym użytkownikiem"));
            while (rs.next()) {
                this.klientList.add(new InsertModel(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            System.err.println("Error" + e);
        }
        this.choiceKlient.setItems(null);
        this.choiceKlient.setItems(klientList);
    }

    @FXML
    public void fillCombobox2() {

    }

    @FXML
    public void choice() throws SQLException {
        if(choiceKlient.getValue().toString().equals("Jestem nowym użytkownikiem")) {
            this.imie.setText("");
            this.nazwisko.setText("");
        }
        try {
            Connection con = dbConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from mydb.klienci where Imię = ? and Nazwisko = ?");
            String[] d = ((InsertModel)choiceKlient.getSelectionModel().getSelectedItem()).getImie_nazwisko().split(" ");
            pst.setString(1, d[0]);
            pst.setString(2, d[1]);
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                this.imie.setText(rs.getString("Imię"));
                this.nazwisko.setText(rs.getString("Nazwisko"));

            }
        } catch (SQLException e) {
            System.err.println("Error" + e);
        }
    }

    @FXML
    public void addZam() throws SQLException{
        if(choiceKlient.getValue().toString().equals("Jestem nowym użytkownikiem")) {
            String sqlKey = "Select max(k.ID_klienta) from mydb.klienci k ";
            String sqlZamKey = "Select max(z.ID_zamówienia) from mydb.zamówienia z";
            if(choiceZam.getValue().toString().equals("Soki")) {
                try {
                    Connection con = dbConnection.getConnection();
                    ResultSet rs = con.createStatement().executeQuery(sqlKey);
                    ResultSet rs1 = con.createStatement().executeQuery(sqlZamKey);
                    rs.next();
                    rs1.next();
                    int key = rs.getInt(1);
                    int zamKey = rs1.getInt(1);
                    key += 1;
                    zamKey += 1;
                    String sqlInsert = "Insert into mydb.klienci (ID_klienta,Imię,Nazwisko) values (?,?,?)";
                    String sqlInsert1 = "Insert into mydb.zamówienia(ID_zamówienia,Klienci_ID_klienta) values (?,?)";
                    String sqlInsert2 = "Insert into mydb.zamówienia_has_soki(`Zamówienia_ID.zamówienia`,`Soki_ID.soku`,Ilość) values (?,?,?)";
                    PreparedStatement stmt = con.prepareStatement(sqlInsert);
                    stmt.setString(1, String.valueOf((key)));
                    stmt.setString(2, this.imie.getText());
                    stmt.setString(3, this.nazwisko.getText());
                    stmt.execute();
                    PreparedStatement stmt1 = con.prepareStatement(sqlInsert1);
                    stmt1.setString(1,String.valueOf(zamKey));
                    stmt1.setString(2,String.valueOf(key));
                    stmt1.execute();
                    PreparedStatement stmt2 = con.prepareStatement(sqlInsert2);
                    stmt2.setString(1,String.valueOf(zamKey));
                    stmt2.setString(2,this.id.getText());
                    stmt2.setString(3,this.ilosc.getText());
                    stmt2.execute();

                } catch (SQLException e) {
                    System.err.println("Error" + e);
                    }
            }
            else if(choiceZam.getValue().toString().equals("Napoje")) {
                try {
                    Connection con = dbConnection.getConnection();
                    ResultSet rs = con.createStatement().executeQuery(sqlKey);
                    ResultSet rs1 = con.createStatement().executeQuery(sqlZamKey);
                    rs.next();
                    rs1.next();
                    int key = rs.getInt(1);
                    int zamKey = rs1.getInt(1);
                    key += 1;
                    zamKey += 1;
                    String sqlInsert = "Insert into mydb.klienci (ID_klienta,Imię,Nazwisko) values (?,?,?)";
                    String sqlInsert1 = "Insert into mydb.zamówienia(ID_zamówienia,Klienci_ID_klienta) values (?,?)";
                    String sqlInsert2 = "Insert into mydb.zamówienia_has_napoje(`Zamówienia_ID.zamówienia`,`Napoje_ID.napoju`,Ilość) values (?,?,?)";
                    PreparedStatement stmt = con.prepareStatement(sqlInsert);
                    stmt.setString(1, String.valueOf((key)));
                    stmt.setString(2, this.imie.getText());
                    stmt.setString(3, this.nazwisko.getText());
                    stmt.execute();
                    PreparedStatement stmt1 = con.prepareStatement(sqlInsert1);
                    stmt1.setString(1,String.valueOf(zamKey));
                    stmt1.setString(2,String.valueOf(key));
                    stmt1.execute();
                    PreparedStatement stmt2 = con.prepareStatement(sqlInsert2);
                    stmt2.setString(1,String.valueOf(zamKey));
                    stmt2.setString(2,this.id.getText());
                    stmt2.setString(3,this.ilosc.getText());
                    stmt2.execute();
                } catch (SQLException e) {
                    System.err.println("Error" + e);
                    }
            }

        }
        else {
            String sqlKey = "Select k.ID_klienta from mydb.klienci k where k.Imię = ? and k.Nazwisko = ?";
            String sqlZamKey = "Select z.ID_zamówienia from mydb.zamówienia z join mydb.klienci k on k.ID_klienta = z.Klienci_ID_klienta where k.Imię = ? and Nazwisko = ?";
            
        }
        Stage stage = (Stage) this.button.getScene().getWindow();
        stage.close();
    }
}
