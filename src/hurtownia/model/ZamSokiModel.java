package hurtownia.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ZamSokiModel {

    private final StringProperty imie;
    private final StringProperty nazwisko;
    private final StringProperty sok;
    private final StringProperty ilosc;
    private final StringProperty koszt;

    public ZamSokiModel (String imie, String nazw, String sok, String ile, String koszt) {
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazw);
        this.sok = new SimpleStringProperty(sok);
        this.ilosc = new SimpleStringProperty(ile);
        this.koszt = new SimpleStringProperty(koszt);
    }


    public String getImie() {
        return imie.get();
    }

    public StringProperty imieProperty() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie.set(imie);
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public StringProperty nazwiskoProperty() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko.set(nazwisko);
    }

    public String getSok() {
        return sok.get();
    }

    public StringProperty sokProperty() {
        return sok;
    }

    public void setSok(String sok) {
        this.sok.set(sok);
    }

    public String getIlosc() {
        return ilosc.get();
    }

    public StringProperty iloscProperty() {
        return ilosc;
    }

    public void setIlosc(String ilosc) {
        this.ilosc.set(ilosc);
    }

    public String getKoszt() {
        return koszt.get();
    }

    public StringProperty kosztProperty() {
        return koszt;
    }

    public void setKoszt(String koszt) {
        this.koszt.set(koszt);
    }



}
