package co.edu.escuelaing.dosw.skycampus.service;

import co.edu.escuelaing.dosw.skycampus.model.Drone;
import java.util.Comparator;
import java.util.List;

public class ConsultaFlotaService {

    // Consulta 1: IDs de drones disponibles con batería >= 50%, ordenados desc por batería
    public List<String> obtenerDisponiblesBateriaAlta(List<Drone> flota) {
        return flota.stream()
                .filter(Drone::disponible)
                .filter(d -> d.bateria() >= 50)
                .sorted(Comparator.comparingInt(Drone::bateria).reversed())
                .map(Drone::id)
                .toList();
    }

    // Consulta 2: ¿Hay algún drone disponible ubicado en "Bloque C"?
    public boolean hayDroneDisponibleEnBloqueC(List<Drone> flota) {
        return flota.stream()
                .anyMatch(d -> d.disponible() && "Bloque C".equalsIgnoreCase(d.ubicacion()));
    }

    // Consulta 3: Cuántos drones tienen batería crítica (< 20%), sin importar disponibilidad
    public long contarDronesBateriaCritica(List<Drone> flota) {
        return flota.stream()
                .filter(d -> d.bateria() < 20)
                .count();
    }

    // Consulta 4: Formato "ID: batería%" para todos los drones
    public List<String> formatearIdYBateria(List<Drone> flota) {
        return flota.stream()
                .map(d -> d.id() + ": " + d.bateria() + "%")
                .toList();
    }
}