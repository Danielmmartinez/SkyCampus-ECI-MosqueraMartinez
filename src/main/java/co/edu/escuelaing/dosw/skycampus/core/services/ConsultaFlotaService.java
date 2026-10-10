package co.edu.escuelaing.dosw.skycampus.core.services;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;

import java.util.Comparator;
import java.util.List;

public class ConsultaFlotaService {

    public List<String> obtenerDisponiblesBateriaAlta(List<Drone> flota) {
        return flota.stream()
                .filter(Drone::disponible)
                .filter(d -> d.bateria() >= 50)
                .sorted(Comparator.comparingInt(Drone::bateria).reversed())
                .map(Drone::id)
                .toList();
    }

    public boolean hayDroneDisponibleEnBloqueC(List<Drone> flota) {
        return flota.stream()
                .anyMatch(d -> d.disponible() && "Bloque C".equalsIgnoreCase(d.ubicacion()));
    }

    public long contarDronesBateriaCritica(List<Drone> flota) {
        return flota.stream()
                .filter(d -> d.bateria() < 20)
                .count();
    }

    public List<String> formatearIdYBateria(List<Drone> flota) {
        return flota.stream()
                .map(d -> d.id() + ": " + d.bateria() + "%")
                .toList();
    }
}