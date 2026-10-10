package co.edu.escuelaing.dosw.skycampus.core.models.enums;

public enum TipoDrone {
    MINI(500),
    EXPRESS(800),
    CARGO(2000);

    private final int capacidadGramos;

    TipoDrone(int capacidadGramos) {
        this.capacidadGramos = capacidadGramos;
    }

    public int getCapacidadGramos() {
        return capacidadGramos;
    }
}