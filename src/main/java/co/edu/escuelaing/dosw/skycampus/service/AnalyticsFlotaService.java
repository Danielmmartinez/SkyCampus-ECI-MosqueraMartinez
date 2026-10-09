package co.edu.escuelaing.dosw.skycampus.service;

import co.edu.escuelaing.dosw.skycampus.model.Drone;
import co.edu.escuelaing.dosw.skycampus.model.EstadoDrone;
import co.edu.escuelaing.dosw.skycampus.model.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.model.Mision;
import co.edu.escuelaing.dosw.skycampus.model.Prioridad;
import co.edu.escuelaing.dosw.skycampus.model.TipoDrone;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnalyticsFlotaService {

    // 1. Misiones completadas agrupadas por tipo de drone
    public Map<TipoDrone, Long> misionesCompletadasPorTipo(List<Mision> misiones) {
        return misiones.stream()
                .filter(m -> m.estado() == EstadoMision.ENTREGADA && m.drone() != null)
                .collect(Collectors.groupingBy(m -> m.drone().tipo(), Collectors.counting()));
    }

    // 2. Drone con más misiones completadas
    public Optional<String> droneConMasMisionesCompletadas(List<Mision> misiones) {
        return misiones.stream()
                .filter(m -> m.estado() == EstadoMision.ENTREGADA && m.drone() != null)
                .collect(Collectors.groupingBy(m -> m.drone().id(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    // 3. Porcentaje de misiones fallidas sobre el total
    public double porcentajeMisionesFallidas(List<Mision> misiones) {
        if (misiones.isEmpty()) return 0.0;
        long fallidas = misiones.stream()
                .filter(m -> m.estado() == EstadoMision.FALLIDA)
                .count();
        return ((double) fallidas / misiones.size()) * 100.0;
    }

    // 4. PartitioningBy: Drones operativos vs Drones en fallo/mantenimiento
    public Map<Boolean, List<Drone>> clasificarOperatividad(List<Drone> flota) {
        return flota.stream()
                .collect(Collectors.partitioningBy(d -> d.estado() != EstadoDrone.FALLO && d.estado() != EstadoDrone.EN_MANTENIMIENTO));
    }

    // 5. Contar misiones urgentes activas por tipo de drone
    public Map<TipoDrone, Long> misionesUrgentesPorTipo(List<Mision> misiones, Map<String, Prioridad> prioridadPorMision) {
        return misiones.stream()
                .filter(m -> m.estado() == EstadoMision.EN_VUELO && m.drone() != null)
                .filter(m -> prioridadPorMision.getOrDefault(m.id(), Prioridad.NORMAL) == Prioridad.URGENTE)
                .collect(Collectors.groupingBy(m -> m.drone().tipo(), Collectors.counting()));
    }
}