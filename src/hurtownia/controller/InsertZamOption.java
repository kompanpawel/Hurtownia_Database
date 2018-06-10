package hurtownia.controller;

public enum InsertZamOption { ///klasa wypełniająca comboboxy z sokami i napojami
    Soki, Napoje;

    private InsertZamOption(){}

    public String value() {
        return name();
    }

    public static InsertZamOption fromvalue(String v) {
        return valueOf(v);
    }
}
