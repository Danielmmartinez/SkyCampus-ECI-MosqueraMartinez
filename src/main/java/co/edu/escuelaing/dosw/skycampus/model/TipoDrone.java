package co.edu.escuelaing.dosw.skycampus.model;

public enum TipoDrone {
    MINI(500),      // hasta 500g
    EXPRESS(800),   // hasta 800g, rápido
    CARGO(2000);    // hasta 2000g, pesado

    private final int capacidadGramos;

    TipoDrone(int capacidadGramos) {
        this.capacidadGramos = capacidadGramos;
    }

    public int getCapacidadGramos() {
        return capacidadGramos;
    }
}