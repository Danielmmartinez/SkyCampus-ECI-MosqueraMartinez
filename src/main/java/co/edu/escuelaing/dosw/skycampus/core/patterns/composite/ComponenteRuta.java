package co.edu.escuelaing.dosw.skycampus.core.patterns.composite;

import java.util.List;

public interface ComponenteRuta {
    String getNombre();
    double getDistanciaKm();
    int getTiempoEstimadoMinutos();
    int getConsumoBateriaEstimado();
    List<String> getParadas();
}