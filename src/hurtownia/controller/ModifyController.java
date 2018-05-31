package hurtownia.controller;

import hurtownia.dbUtil.dbConnection;
import hurtownia.model.InsertModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModifyController {

    int whichTable;
    private dbConnection dC;
    private ObservableList<InsertModel> klientList;
    private String imie_stare;
    private String nazwisko_stare;
    private String sok_stary;
    private String napoj_stary;
    private String ilosc_stary;


    @FXML
    private ComboBox<InsertModel> choiceKlient;

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
        fillComboBox();
    }

    public void fillComboBox() throws SQLException {
        try {
            Connection con = dbConnection.getConnection();
            this.klientList = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery(sqlKlient);
            this.klientList.add(new InsertModel("Jestem", "nowym użytkownikiem"));
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
    public void choice() throws SQLException {
        if (choiceKlient.getValue().toString().equals("Jestem nowym użytkownikiem")) {
            this.imie.setText("");
            this.nazwisko.setText("");
        }
        try {
            Connection con = dbConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from mydb.klienci where Imię = ? and Nazwisko = ?");
            String[] d = ((InsertModel) choiceKlient.getSelectionModel().getSelectedItem()).getImie_nazwisko().split(" ");
            pst.setString(1, d[0]);
            pst.setString(2, d[1]);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                this.imie.setText(rs.getString("Imię"));
                this.nazwisko.setText(rs.getString("Nazwisko"));
            }
        } catch (SQLException e) {
            System.err.println("Error" + e);
        }
    }

    public void modifyZam() {
        if (choiceKlient.getValue().toString().equals("Jestem nowym użytkownikiem")) {
            String sqlKey = "Select max(k.ID_klienta) from mydb.klienci k ";

            if (whichTable == 1) {
                try {
                    String sql = "Select k.ID_klienta from mydb.klienci k where k.Imię = ? and k.Nazwisko = ?";
                    Connection con = dbConnection.getConnection();
                    ResultSet rs = con.createStatement().executeQuery(sqlKey);
                    PreparedStatement prs = con.prepareStatement(sql);
                    prs.setString(1, imie_stare);
                    prs.setString(2, nazwisko_stare);
                    ResultSet rs1 = prs.executeQuery();
                    rs.next();
                    rs1.next();
                    int key = rs.getInt(1);
                    int oldKey = rs1.getInt(1);
                    key += 1;
                    String sqlInsert = "Insert into mydb.klienci (ID_klienta,Imię,Nazwisko) values (?,?,?)";
                    String sqlUpdate = "Update mydb.zamówienia_has_soki set Klienci_ID_klienta = ?, `Soki_ID.soku`= ?, Ilość = ? where Klienci_ID_klienta = ? and `Soki_ID.soku`= ? and Ilość = ?";
                    PreparedStatement stmt = con.prepareStatement(sqlInsert);
                    stmt.setString(1, String.valueOf((key)));
                    stmt.setString(2, this.imie.getText());
                    stmt.setString(3, this.nazwisko.getText());
                    stmt.execute();
                    PreparedStatement stmt2 = con.prepareStatement(sqlUpdate);
                    stmt2.setString(1, String.valueOf(key));
                    stmt2.setString(2, this.id.getText());
                    stmt2.setString(3, this.ilosc.getText());
                    stmt2.setString(4, String.valueOf(oldKey));
                    stmt2.setString(5, sok_stary);
                    stmt2.setString(6, ilosc_stary);
                    stmt2.execute();

                } catch (SQLException e) {
                    System.err.println("Error" + e);
                }
            }
        }
    }

    public int getWhichTable() {
        return whichTable;
    }

    public void setWhichTable(int whichTable) {
        this.whichTable = whichTable;
    }

    public String getImie_stare() {
        return imie_stare;
    }

    public void setImie_stare(String imie_stare) {
        this.imie_stare = imie_stare;
    }

    public String getNazwisko_stare() {
        return nazwisko_stare;
    }

    public void setNazwisko_stare(String nazwisko_stare) {
        this.nazwisko_stare = nazwisko_stare;
    }

    public String getSok_stary() {
        return sok_stary;
    }

    public void setSok_stary(String sok_stary) {
        this.sok_stary = sok_stary;
    }

    public String getNapoj_stary() {
        return napoj_stary;
    }

    public void setNapoj_stary(String napoj_stary) {
        this.napoj_stary = napoj_stary;
    }

    public String getIlosc_stary() {
        return ilosc_stary;
    }

    public void setIlosc_stary(String ilosc_stary) {
        this.ilosc_stary = ilosc_stary;
    }
}
