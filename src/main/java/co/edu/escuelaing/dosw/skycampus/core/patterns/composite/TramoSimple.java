package co.edu.escuelaing.dosw.skycampus.core.patterns.composite;

import java.util.List;

public class TramoSimple implements ComponenteRuta {
    private final String origen;
    private final String destino;
    private final double distanciaKm;
    private final int tiempoEstimadoMinutos;
    private final int consumoBateria;

    public TramoSimple(String origen, String destino, double distanciaKm, int tiempoEstimadoMinutos, int consumoBateria) {
        this.origen = origen;
        this.destino = destino;
        this.distanciaKm = distanciaKm;
        this.tiempoEstimadoMinutos = tiempoEstimadoMinutos;
        this.consumoBateria = consumoBateria;
    }

    @Override
    public String getNombre() {
        return origen + " -> " + destino;
    }

    @Override
    public double getDistanciaKm() {
        return distanciaKm;
    }

    @Override
    public int getTiempoEstimadoMinutos() {
        return tiempoEstimadoMinutos;
    }

    @Override
    public int getConsumoBateriaEstimado() {
        return consumoBateria;
    }

    @Override
    public List<String> getParadas() {
        return List.of(origen, destino);
    }
}