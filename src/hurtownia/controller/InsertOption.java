package hurtownia.controller;

public enum InsertOption {
    Soki, Napoje;

    private InsertOption(){}

    public String value() {
        return name();
    }

    public static InsertOption fromvalue(String v) {
        return valueOf(v);
    }
}
