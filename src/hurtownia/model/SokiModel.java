package hurtownia.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SokiModel {

    private final StringProperty ID;
    private final StringProperty nazwa;
    private final StringProperty producent;
    private final StringProperty pojemnosc;
    private final StringProperty cena;
    private final StringProperty owoce;
    private final StringProperty rodzaj;

    public SokiModel (String id, String nazwa, String producent, String poj, String cena, String owoce, String rodzaj) {
        this.ID = new SimpleStringProperty(id);
        this.nazwa = new SimpleStringProperty(nazwa);
        this.producent = new SimpleStringProperty(producent);
        this.pojemnosc = new SimpleStringProperty(poj);
        this.cena = new SimpleStringProperty(cena);
        this.owoce = new SimpleStringProperty(owoce);
        this.rodzaj = new SimpleStringProperty(rodzaj);

    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getNazwa() {
        return nazwa.get();
    }

    public StringProperty nazwaProperty() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    public String getProducent() {
        return producent.get();
    }

    public StringProperty producentProperty() {
        return producent;
    }

    public void setProducent(String producent) {
        this.producent.set(producent);
    }

    public String getPojemnosc() {
        return pojemnosc.get();
    }

    public StringProperty pojemnoscProperty() {
        return pojemnosc;
    }

    public void setPojemnosc(String pojemnosc) {
        this.pojemnosc.set(pojemnosc);
    }

    public String getCena() {
        return cena.get();
    }

    public StringProperty cenaProperty() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena.set(cena);
    }

    public String getOwoce() {
        return owoce.get();
    }

    public StringProperty owoceProperty() {
        return owoce;
    }

    public void setOwoce(String owoce) {
        this.owoce.set(owoce);
    }

    public String getRodzaj() {
        return rodzaj.get();
    }

    public StringProperty rodzajProperty() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj.set(rodzaj);
    }





}
