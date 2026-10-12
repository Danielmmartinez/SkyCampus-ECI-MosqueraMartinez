package co.edu.escuelaing.dosw.skycampus.core.patterns.composite;

import java.util.ArrayList;
import java.util.List;

public class RutaCompuesta implements ComponenteRuta {
    private final String nombre;
    private final int tiempoParadaRecargaMinutos;
    private final List<ComponenteRuta> tramos = new ArrayList<>();

    public RutaCompuesta(String nombre, int tiempoParadaRecargaMinutos) {
        this.nombre = nombre;
        this.tiempoParadaRecargaMinutos = tiempoParadaRecargaMinutos;
    }

    public void agregarTramo(ComponenteRuta tramo) {
        this.tramos.add(tramo);
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public double getDistanciaKm() {
        return tramos.stream().mapToDouble(ComponenteRuta::getDistanciaKm).sum();
    }

    @Override
    public int getTiempoEstimadoMinutos() {
        int tiempoVuelo = tramos.stream().mapToInt(ComponenteRuta::getTiempoEstimadoMinutos).sum();
        int paradasIntermedias = Math.max(0, tramos.size() - 1);
        return tiempoVuelo + (paradasIntermedias * tiempoParadaRecargaMinutos);
    }

    @Override
    public int getConsumoBateriaEstimado() {
        return tramos.stream().mapToInt(ComponenteRuta::getConsumoBateriaEstimado).sum();
    }

    @Override
    public List<String> getParadas() {
        List<String> puntos = new ArrayList<>();
        for (ComponenteRuta tramo : tramos) {
            for (String parada : tramo.getParadas()) {
                if (puntos.isEmpty() || !puntos.get(puntos.size() - 1).equals(parada)) {
                    puntos.add(parada);
                }
            }
        }
        return puntos;
    }

    public int totalEtapas() {
        return tramos.size();
    }
}