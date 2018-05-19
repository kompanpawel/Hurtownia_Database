package hurtownia.controller;

public enum InsertZamOption {
    Soki, Napoje;

    private InsertZamOption(){}

    public String value() {
        return name();
    }

    public static InsertZamOption fromvalue(String v) {
        return valueOf(v);
    }
}
