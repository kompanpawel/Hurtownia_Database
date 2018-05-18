package hurtownia.controller;

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

    public void initialize() {
        this.dc = new dbConnection();
    }

    @FXML
    public void update(ActionEvent event) throws SQLException {
        loadSoki(event);
    }

    @FXML
    public void loadSoki(ActionEvent event) throws SQLException{
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
