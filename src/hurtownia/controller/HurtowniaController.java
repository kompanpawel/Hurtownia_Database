package hurtownia.controller;

import com.mysql.cj.xdevapi.SqlDataResult;
import hurtownia.dbUtil.dbConnection;
import hurtownia.model.NapojeModel;
import hurtownia.model.SokiModel;
import hurtownia.model.ZamNapojeModel;
import hurtownia.model.ZamSokiModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class HurtowniaController {

    private dbConnection dc;
    private ObservableList<SokiModel> sokiList;
    private ObservableList<NapojeModel> napojeList;
    private ObservableList<ZamSokiModel> zamSokiList;
    private ObservableList<ZamNapojeModel> zamNapojeList;


    @FXML
    private TableView<SokiModel> soki;

    @FXML
    private TableView<NapojeModel> napoje;

    @FXML
    private TableView<ZamSokiModel> zamsoki;

    @FXML
    private TableView<ZamNapojeModel> zamnapoje;

    @FXML
    private TableColumn<SokiModel, String> idcol;
    @FXML
    private TableColumn<SokiModel, String> nazwacol;
    @FXML
    private TableColumn<SokiModel, String> prodcol;
    @FXML
    private TableColumn<SokiModel, String> pojcol;
    @FXML
    private TableColumn<SokiModel, String> cenacol;
    @FXML
    private TableColumn<SokiModel, String> owocecol;
    @FXML
    private TableColumn<SokiModel, String> rodzajcol;

    @FXML
    private TableColumn<NapojeModel, String> idcol1;
    @FXML
    private TableColumn<NapojeModel, String> nazwacol1;
    @FXML
    private TableColumn<NapojeModel, String> prodcol1;
    @FXML
    private TableColumn<NapojeModel, String> pojcol1;
    @FXML
    private TableColumn<NapojeModel, String> cenacol1;
    @FXML
    private TableColumn<NapojeModel, String> rodzajcol1;

    @FXML
    private TableColumn<ZamSokiModel, String> imiecol;
    @FXML
    private TableColumn<ZamSokiModel, String> nazcol;
    @FXML
    private TableColumn<ZamSokiModel, String> sokcol;
    @FXML
    private TableColumn<ZamSokiModel, String> ilecol;
    @FXML
    private TableColumn<ZamSokiModel, String> kosztcol;

    @FXML
    private TableColumn<ZamNapojeModel, String> imiecol1;
    @FXML
    private TableColumn<ZamNapojeModel, String> nazcol1;
    @FXML
    private TableColumn<ZamNapojeModel, String> napojcol;
    @FXML
    private TableColumn<ZamNapojeModel, String> ilecol1;
    @FXML
    private TableColumn<ZamNapojeModel, String> kosztcol1;


    private String sqlSoki = "SELECT * FROM mydb.soki";
    private String sqlNapoje = "SELECT * FROM mydb.napoje";
    private String sqlZamSoki = "SELECT k.Imię,k.Nazwisko,sk.Nazwa,s.Ilość,(s.Ilość * sk.Cena) FROM mydb.klienci k JOIN mydb.zamówienia z ON k.ID_klienta = z.Klienci_ID_klienta JOIN mydb.zamówienia_has_soki s ON z.ID_zamówienia = s.`Zamówienia_ID.zamówienia` JOIN mydb.soki sk ON s.`Soki_ID.soku` = sk.`ID.soku`";
    private String sqlZamNapoje = "SELECT k.Imię,k.Nazwisko,np.Nazwa,n.Ilość,(n.Ilość * np.Cena) FROM mydb.klienci k \n" +
            "\tJOIN mydb.zamówienia z ON k.ID_klienta = z.Klienci_ID_klienta \n" +
            "\tJOIN mydb.zamówienia_has_napoje n ON z.ID_zamówienia = n.`Zamówienia_ID.zamówienia`\n" +
            "    JOIN mydb.napoje np ON n.`Napoje_ID.napoju` = np.ID_napoju";

    public void initialize() {
        this.dc = new dbConnection();
    }

    @FXML
    public void update() throws SQLException {
        loadSoki();
        loadNapoje();
        loadSokiZam();
        loadNapojeZam();
    }

    @FXML
    public void loadSoki() throws SQLException{
        try {
            Connection con = dbConnection.getConnection();
            this.sokiList = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery(sqlSoki);
            while (rs.next()) {
                this.sokiList.add(new SokiModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }

        } catch (SQLException e) {
            System.err.println("Error"+e);
        }

        this.idcol.setCellValueFactory(new PropertyValueFactory<SokiModel, String>("ID"));
        this.nazwacol.setCellValueFactory(new PropertyValueFactory<SokiModel, String>("nazwa"));
        this.prodcol.setCellValueFactory(new PropertyValueFactory<SokiModel, String>("producent"));
        this.pojcol.setCellValueFactory(new PropertyValueFactory<SokiModel, String>("pojemnosc"));
        this.cenacol.setCellValueFactory(new PropertyValueFactory<SokiModel, String>("cena"));
        this.owocecol.setCellValueFactory(new PropertyValueFactory<SokiModel, String>("owoce"));
        this.rodzajcol.setCellValueFactory(new PropertyValueFactory<SokiModel, String>("rodzaj"));

        this.soki.setItems(null);
        this.soki.setItems(this.sokiList);
    }

    public void loadNapoje() throws SQLException {
        try {
            Connection con = dbConnection.getConnection();
            this.napojeList = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery(sqlNapoje);
            while (rs.next()) {
                this.napojeList.add(new NapojeModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }

        } catch (SQLException e) {
            System.err.println("Error"+e);
        }

        this.idcol1.setCellValueFactory(new PropertyValueFactory<NapojeModel, String>("ID"));
        this.nazwacol1.setCellValueFactory(new PropertyValueFactory<NapojeModel, String>("nazwa"));
        this.prodcol1.setCellValueFactory(new PropertyValueFactory<NapojeModel, String>("producent"));
        this.pojcol1.setCellValueFactory(new PropertyValueFactory<NapojeModel, String>("pojemnosc"));
        this.cenacol1.setCellValueFactory(new PropertyValueFactory<NapojeModel, String>("cena"));
        this.rodzajcol1.setCellValueFactory(new PropertyValueFactory<NapojeModel, String>("rodzaj"));

        this.napoje.setItems(null);
        this.napoje.setItems(this.napojeList);
    }

    @FXML
    public void loadSokiZam() throws SQLException {
        try {
            Connection con = dbConnection.getConnection();
            this.zamSokiList = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery(sqlZamSoki);
            while (rs.next()) {
                this.zamSokiList.add(new ZamSokiModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                }
            }  catch (SQLException e) {
            System.err.println("Error"+e);
        }
        this.imiecol.setCellValueFactory(new PropertyValueFactory<ZamSokiModel, String>("imie"));
        this.nazcol.setCellValueFactory(new PropertyValueFactory<ZamSokiModel, String>("nazwisko"));
        this.sokcol.setCellValueFactory(new PropertyValueFactory<ZamSokiModel, String>("sok"));
        this.ilecol.setCellValueFactory(new PropertyValueFactory<ZamSokiModel, String>("ilosc"));
        this.kosztcol.setCellValueFactory(new PropertyValueFactory<ZamSokiModel, String>("koszt"));

        this.zamsoki.setItems(null);
        this.zamsoki.setItems(this.zamSokiList);
    }

    @FXML
    public void loadNapojeZam() throws SQLException {
        try {
            Connection con = dbConnection.getConnection();
            this.zamNapojeList = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery(sqlZamNapoje);
            while (rs.next()) {
                this.zamNapojeList.add(new ZamNapojeModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        }  catch (SQLException e) {
            System.err.println("Error"+e);
        }
        this.imiecol1.setCellValueFactory(new PropertyValueFactory<ZamNapojeModel, String>("imie"));
        this.nazcol1.setCellValueFactory(new PropertyValueFactory<ZamNapojeModel, String>("nazwisko"));
        this.napojcol.setCellValueFactory(new PropertyValueFactory<ZamNapojeModel, String>("napoj"));
        this.ilecol1.setCellValueFactory(new PropertyValueFactory<ZamNapojeModel, String>("ilosc"));
        this.kosztcol1.setCellValueFactory(new PropertyValueFactory<ZamNapojeModel, String>("koszt"));

        this.zamnapoje.setItems(null);
        this.zamnapoje.setItems(this.zamNapojeList);
    }
    @FXML
    public void insertData() {
        try {
            Stage insert = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/hurtownia/view/insert.fxml").openStream());

            InsertController insertController = (InsertController) loader.getController();

            Scene scene = new Scene(root);
            insert.setScene(scene);
            insert.setTitle("Hurtownia sokow i napojow");
            insert.setResizable(false);
            insert.show();
        }  catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void modifyData() {

    }

    public void deleteData() {

    }

}
