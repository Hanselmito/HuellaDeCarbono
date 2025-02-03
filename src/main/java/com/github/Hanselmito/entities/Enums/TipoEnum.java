package com.github.Hanselmito.entities.Enums;

public enum TipoEnum {
    diaria("diaria"),
    semanal("semanal"),
    mensual("mensual"),
    anual("anual");

    private String tipo;

    TipoEnum(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public static TipoEnum fromString(String tipo) {
        for (TipoEnum t : TipoEnum.values()) {
            if (t.tipo.equalsIgnoreCase(tipo)) {
                return t;
            }
        }
        throw new IllegalArgumentException("No enum constant " + TipoEnum.class.getCanonicalName() + "." + tipo);
    }
}