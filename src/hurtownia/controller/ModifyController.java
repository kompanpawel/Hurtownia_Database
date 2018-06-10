package hurtownia.controller;

import hurtownia.dbUtil.dbConnection;
import hurtownia.model.InsertModel;
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

    public void setVariablesSoki(String imie, String nazw, String sok, String ilosc) { ///przekazanie wartości dla modyfikacji
        setImie_stare(imie);
        setNazwisko_stare(nazw);
        setSok_stary(sok);
        setIlosc_stary(ilosc);
        setWhichTable(1);
    }

    public void setVariablesNapoje(String imie, String nazw, String napoj, String ilosc) { ///przekazanie wartości dla modyfikacji
        setImie_stare(imie);
        setNazwisko_stare(nazw);
        setNapoj_stary(napoj);
        setIlosc_stary(ilosc);
        setWhichTable(2);
    }

    public void fillComboBox() throws SQLException { ///wypełnienie wyboru klientów
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
    public void choice() throws SQLException {///wybór klienta
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

    public void modifyZam() { ///modyfikacja zamówienia
        String sqlSokID = "Select `ID.soku` from mydb.soki where Nazwa = ?";
        String sqlNapojID = "Select ID_napoju from mydb.napoje where Nazwa = ?";
        String sqlKey = "Select k.ID_klienta from mydb.klienci k where k.Imię = ? and k.Nazwisko = ?";
        if (choiceKlient.getValue().toString().equals("Jestem nowym użytkownikiem")) {
            String sqlMaxKey = "Select max(k.ID_klienta) from mydb.klienci k ";
            if (whichTable == 1) {
                try {
                    Connection con = dbConnection.getConnection();
                    ResultSet rs = con.createStatement().executeQuery(sqlMaxKey);
                    PreparedStatement prs = con.prepareStatement(sqlKey);
                    prs.setString(1, imie_stare);
                    prs.setString(2, nazwisko_stare);
                    ResultSet rs1 = prs.executeQuery();
                    PreparedStatement prs1 = con.prepareStatement(sqlSokID);
                    prs1.setString(1, sok_stary);
                    ResultSet rs2 = prs1.executeQuery();
                    rs.next();
                    rs1.next();
                    rs2.next();
                    int key = rs.getInt(1);
                    int oldKey = rs1.getInt(1);
                    int sokKey = rs2.getInt(1);
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
                    stmt2.setString(5, String.valueOf(sokKey));
                    stmt2.setString(6, ilosc_stary);
                    stmt2.execute();

                } catch (SQLException e) {
                    System.err.println("Error" + e);
                }
            } else if(whichTable == 2) {
                try {
                    Connection con = dbConnection.getConnection();
                    ResultSet rs = con.createStatement().executeQuery(sqlMaxKey);
                    PreparedStatement prs = con.prepareStatement(sqlKey);
                    prs.setString(1, imie_stare);
                    prs.setString(2, nazwisko_stare);
                    ResultSet rs1 = prs.executeQuery();
                    PreparedStatement prs1 = con.prepareStatement(sqlNapojID);
                    prs1.setString(1, napoj_stary);
                    ResultSet rs2 = prs1.executeQuery();
                    rs.next();
                    rs1.next();
                    rs2.next();
                    int key = rs.getInt(1);
                    int oldKey = rs1.getInt(1);
                    int napojKey = rs2.getInt(1);
                    key += 1;
                    String sqlInsert = "Insert into mydb.klienci (ID_klienta,Imię,Nazwisko) values (?,?,?)";
                    String sqlUpdate = "Update mydb.zamówienia_has_napoje set Klienci_ID_klienta = ?, `Napoje_ID.napoju`= ?, Ilość = ? where Klienci_ID_klienta = ? and `Napoje_ID.napoju`= ? and Ilość = ?";
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
                    stmt2.setString(5, String.valueOf(napojKey));
                    stmt2.setString(6, ilosc_stary);
                    stmt2.execute();

                } catch (SQLException e) {
                    System.err.println("Error" + e);
                }
            }
        } else {
            if(whichTable == 1) {
                try {
                    Connection con = dbConnection.getConnection();
                    PreparedStatement prs = con.prepareStatement(sqlKey);
                    prs.setString(1, imie_stare);
                    prs.setString(2, nazwisko_stare);
                    ResultSet rs1 = prs.executeQuery();
                    PreparedStatement prs1 = con.prepareStatement(sqlSokID);
                    prs1.setString(1, sok_stary);
                    ResultSet rs2 = prs1.executeQuery();
                    PreparedStatement prs2 = con.prepareStatement(sqlKey);
                    prs2.setString(1, this.imie.getText());
                    prs2.setString(2, this.nazwisko.getText());
                    ResultSet rs3 = prs2.executeQuery();
                    rs1.next();
                    rs2.next();
                    rs3.next();
                    int oldKey = rs1.getInt(1);
                    int sokKey = rs2.getInt(1);
                    int newKey = rs3.getInt(1);
                    String sqlUpdate = "Update mydb.zamówienia_has_soki set Klienci_ID_klienta = ?, `Soki_ID.soku`= ?, Ilość = ? where Klienci_ID_klienta = ? and `Soki_ID.soku`= ? and Ilość = ?";
                    PreparedStatement stmt2 = con.prepareStatement(sqlUpdate);
                    stmt2.setString(1, String.valueOf(newKey));
                    stmt2.setString(2, this.id.getText());
                    stmt2.setString(3, this.ilosc.getText());
                    stmt2.setString(4, String.valueOf(oldKey));
                    stmt2.setString(5, String.valueOf(sokKey));
                    stmt2.setString(6, ilosc_stary);
                    stmt2.execute();

                } catch (SQLException e) {
                    System.err.println("Error" + e);
                }
            } else if(whichTable == 2) {
                try {
                    Connection con = dbConnection.getConnection();
                    PreparedStatement prs = con.prepareStatement(sqlKey);
                    prs.setString(1, imie_stare);
                    prs.setString(2, nazwisko_stare);
                    ResultSet rs1 = prs.executeQuery();
                    PreparedStatement prs1 = con.prepareStatement(sqlNapojID);
                    prs1.setString(1, napoj_stary);
                    ResultSet rs2 = prs1.executeQuery();
                    PreparedStatement prs2 = con.prepareStatement(sqlKey);
                    prs2.setString(1, this.imie.getText());
                    prs2.setString(2, this.nazwisko.getText());
                    ResultSet rs3 = prs2.executeQuery();
                    rs1.next();
                    rs2.next();
                    rs3.next();
                    int oldKey = rs1.getInt(1);
                    int napojKey = rs2.getInt(1);
                    int newKey = rs3.getInt(1);
                    String sqlUpdate = "Update mydb.zamówienia_has_soki set Klienci_ID_klienta = ?, `Napoje_ID.napoju`= ?, Ilość = ? where Klienci_ID_klienta = ? and `Napoje_ID.napoju`= ? and Ilość = ?";
                    PreparedStatement stmt2 = con.prepareStatement(sqlUpdate);
                    stmt2.setString(1, String.valueOf(newKey));
                    stmt2.setString(2, this.id.getText());
                    stmt2.setString(3, this.ilosc.getText());
                    stmt2.setString(4, String.valueOf(oldKey));
                    stmt2.setString(5, String.valueOf(napojKey));
                    stmt2.setString(6, ilosc_stary);
                    stmt2.execute();

                } catch (SQLException e) {
                    System.err.println("Error" + e);
                }
            }
        }
        Stage stage = (Stage) this.button.getScene().getWindow();
        stage.close();
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
