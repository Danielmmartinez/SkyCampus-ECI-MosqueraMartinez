package co.edu.escuelaing.dosw.skycampus.core.services;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.Mision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoDrone;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.Prioridad;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoDrone;

import java.time.LocalDateTime;
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
    public Map<TipoDrone, Long> misionesUrgentesPorTipo(List<Mision> misiones) {
        return misiones.stream()
                .filter(m -> m.estado() == EstadoMision.EN_VUELO && m.drone() != null)
                .filter(m -> m.prioridad() == Prioridad.URGENTE)
                .collect(Collectors.groupingBy(m -> m.drone().tipo(), Collectors.counting()));
    }

    // 6. Reto Monferno: Verificar si existe misión urgente pendiente retrasada
    public boolean existeMisionUrgenteRetrasada(List<Mision> misiones, int minutosLimite) {
        LocalDateTime limite = LocalDateTime.now().minusMinutes(minutosLimite);
        return misiones.stream()
                .anyMatch(m -> m.prioridad() == Prioridad.URGENTE
                        && m.estado() == EstadoMision.PENDIENTE
                        && m.fechaCreacion() != null
                        && m.fechaCreacion().isBefore(limite));
    }
}