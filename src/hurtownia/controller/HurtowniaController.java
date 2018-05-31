package hurtownia.controller;

import com.mysql.cj.xdevapi.SqlDataResult;
import hurtownia.dbUtil.dbConnection;
import hurtownia.model.NapojeModel;
import hurtownia.model.SokiModel;
import hurtownia.model.ZamNapojeModel;
import hurtownia.model.ZamSokiModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class HurtowniaController {

    private dbConnection dc;
    private ObservableList<SokiModel> sokiList;
    private ObservableList<NapojeModel> napojeList;
    private ObservableList<ZamSokiModel> zamSokiList;
    private ObservableList<ZamNapojeModel> zamNapojeList;


    @FXML
    private TabPane tabPane;

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

    @FXML
    private ContextMenu contextMenu = new ContextMenu();
    @FXML
    private MenuItem updateMenu = new MenuItem("Update");
    @FXML
    private MenuItem deletion = new MenuItem("Delete");

    private String sqlSoki = "SELECT * FROM mydb.soki";
    private String sqlNapoje = "SELECT * FROM mydb.napoje";
    private String sqlZamSoki = "SELECT k.Imię,k.Nazwisko,sk.Nazwa,s.Ilość,(s.Ilość * sk.Cena) FROM mydb.klienci k JOIN mydb.zamówienia_has_soki s ON s.Klienci_ID_Klienta = k.ID_Klienta JOIN mydb.soki sk ON s.`Soki_ID.soku` = sk.`ID.soku`";
    private String sqlZamNapoje = "SELECT k.Imię,k.Nazwisko,np.Nazwa,n.Ilość,(n.Ilość * np.Cena) FROM mydb.klienci k \n" +
            "\tJOIN mydb.zamówienia_has_napoje n ON n.Klienci_ID_klienta = k.ID_klienta\n" +
            "    JOIN mydb.napoje np ON n.`Napoje_ID.napoju` = np.ID_napoju";

    public void initialize() throws SQLException {
        this.dc = new dbConnection();
        contextMenu.getItems().add(updateMenu);
        contextMenu.getItems().add(deletion);
        zamsoki.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(zamsoki, event.getScreenX(), event.getScreenY());
                updateMenu.setOnAction(event1 -> modifyData1());
                deletion.setOnAction(event1 -> {
                    try {
                        deleteData1();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        zamnapoje.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(zamsoki, event.getScreenX(), event.getScreenY());
                updateMenu.setOnAction(event1 -> modifyData2());
                deletion.setOnAction(event1 -> {
                    try {
                        deleteData2();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
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
            insert.setTitle("Dodaj nowe zamówienie");
            insert.setResizable(false);
            insert.show();
        }  catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void modifyData1() {
        try {
            Stage modify = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/hurtownia/view/modify.fxml").openStream());

            ModifyController modifyController = (ModifyController) loader.getController();
            ZamSokiModel dane = new ZamSokiModel(zamsoki.getSelectionModel().getSelectedItem().getImie(),zamsoki.getSelectionModel().getSelectedItem().getNazwisko() ,zamsoki.getSelectionModel().getSelectedItem().getSok() , zamsoki.getSelectionModel().getSelectedItem().getIlosc(), zamsoki.getSelectionModel().getSelectedItem().getKoszt());
            String imie = dane.getImie();
            String nazwisko = dane.getNazwisko();
            String sok = dane.getSok();
            String ilosc = dane.getIlosc();
            modifyController.setWhichTable(1);
            modifyController.setImie_stare(imie);
            modifyController.setNazwisko_stare(nazwisko);
            modifyController.setSok_stary(sok);
            modifyController.setIlosc_stary(ilosc);


            Scene scene = new Scene(root);
            modify.setScene(scene);
            modify.setTitle("Modyfikacja zamówienia");
            modify.setResizable(false);
            modify.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void modifyData2() {
        try {
            Stage modify = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/hurtownia/view/modify.fxml").openStream());

            ModifyController modifyController = (ModifyController) loader.getController();
            ZamNapojeModel dane = new ZamNapojeModel(zamnapoje.getSelectionModel().getSelectedItem().getImie(),zamnapoje.getSelectionModel().getSelectedItem().getNazwisko() ,zamnapoje.getSelectionModel().getSelectedItem().getNapoj() , zamnapoje.getSelectionModel().getSelectedItem().getIlosc(), zamnapoje.getSelectionModel().getSelectedItem().getKoszt());
            String imie = dane.getImie();
            String nazwisko = dane.getNazwisko();
            String napoj = dane.getNapoj();
            String ilosc = dane.getIlosc();
            modifyController.setWhichTable(2);
            modifyController.setImie_stare(imie);
            modifyController.setNazwisko_stare(nazwisko);
            modifyController.setNapoj_stary(napoj);
            modifyController.setIlosc_stary(ilosc);

            Scene scene = new Scene(root);
            modify.setScene(scene);
            modify.setTitle("Hurtownia sokow i napojow");
            modify.setResizable(false);
            modify.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteData1() throws SQLException{
            String sql = "Select k.ID_klienta from mydb.klienci k where k.Imię = ? and k.Nazwisko = ?";
            String sql1 = "Select s.`ID.soku` from mydb.soki s where s.Nazwa = ?";
            String sqlDelete = "Delete from mydb.zamówienia_has_soki where Klienci_ID_klienta = ? and  `Soki_ID.soku` = ? and Ilość = ?";
            ZamSokiModel dane = new ZamSokiModel(zamsoki.getSelectionModel().getSelectedItem().getImie(),zamsoki.getSelectionModel().getSelectedItem().getNazwisko() ,zamsoki.getSelectionModel().getSelectedItem().getSok() , zamsoki.getSelectionModel().getSelectedItem().getIlosc(), zamsoki.getSelectionModel().getSelectedItem().getKoszt());
            String imie = dane.getImie();
            String nazwisko = dane.getNazwisko();
            String sok = dane.getSok();
            String ilosc = dane.getIlosc();
            try {
                Connection con = dbConnection.getConnection();
                PreparedStatement prs1 = con.prepareStatement(sql);
                prs1.setString(1, imie);
                prs1.setString(2, nazwisko);
                ResultSet rs1 = prs1.executeQuery();
                PreparedStatement prs2 = con.prepareStatement(sql1);
                prs2.setString(1, sok);
                ResultSet rs2 = prs2.executeQuery();
                rs1.next();
                rs2.next();
                int key = rs1.getInt(1);
                int sokKey = rs2.getInt(1);
                PreparedStatement delete = con.prepareStatement(sqlDelete);
                delete.setString(1, String.valueOf(key));
                delete.setString(2, String.valueOf(sokKey));
                delete.setString(3, ilosc);
                delete.execute();
            } catch (SQLException e) {
                System.err.println("Error" + e);
            }
    }

    public void deleteData2 () throws SQLException{
        String sql = "Select k.ID_klienta from mydb.klienci k where k.Imię = ? and k.Nazwisko = ?";
        String sql1 = "Select n.ID_napoju from mydb.napoje n where n.Nazwa = ?";
        String sqlDelete = "Delete from mydb.zamówienia_has_napoje where Klienci_ID_klienta = ? and  `Napoje_ID.napoju` = ? and Ilość = ?";
        ZamNapojeModel dane = new ZamNapojeModel(zamnapoje.getSelectionModel().getSelectedItem().getImie(),zamnapoje.getSelectionModel().getSelectedItem().getNazwisko() ,zamnapoje.getSelectionModel().getSelectedItem().getNapoj() , zamnapoje.getSelectionModel().getSelectedItem().getIlosc(), zamnapoje.getSelectionModel().getSelectedItem().getKoszt());
        String imie = dane.getImie();
        String nazwisko = dane.getNazwisko();
        String napoj = dane.getNapoj();
        String ilosc = dane.getIlosc();
        try {
            Connection con = dbConnection.getConnection();
            PreparedStatement prs1 = con.prepareStatement(sql);
            prs1.setString(1, imie);
            prs1.setString(2, nazwisko);
            ResultSet rs1 = prs1.executeQuery();
            PreparedStatement prs2 = con.prepareStatement(sql1);
            prs2.setString(1, napoj);
            ResultSet rs2 = prs2.executeQuery();
            rs1.next();
            rs2.next();
            int key = rs1.getInt(1);
            int napojKey = rs2.getInt(1);
            PreparedStatement delete = con.prepareStatement(sqlDelete);
            delete.setString(1, String.valueOf(key));
            delete.setString(2, String.valueOf(napojKey));
            delete.setString(3, ilosc);
            delete.execute();
        } catch (SQLException e) {
            System.err.println("Error" + e);
        }
    }

    public void modify(ActionEvent event) {
    }
}
