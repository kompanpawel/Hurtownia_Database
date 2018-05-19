package hurtownia.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InsertModel {

    private String imie_nazwisko;

    public InsertModel(String imie, String nazwisko) {
        this.imie_nazwisko = imie + " " + nazwisko;
    }

    public String getImie_nazwisko() {
        return imie_nazwisko;
    }

    public void setImie_nazwisko(String imie_nazwisko) {
        this.imie_nazwisko = imie_nazwisko;
    }

    @Override
    public String toString() {
        return this.imie_nazwisko;
    }
}
