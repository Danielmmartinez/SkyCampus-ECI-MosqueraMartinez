package co.edu.escuelaing.dosw.skycampus.core.services;

import co.edu.escuelaing.dosw.skycampus.core.models.Drone;
import co.edu.escuelaing.dosw.skycampus.core.models.Mision;
import co.edu.escuelaing.dosw.skycampus.core.models.Sede;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.core.models.enums.TipoDrone;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnalyticsEnterpriseService {

    // 1. Optional — Encontrar la sede más activa
    public Optional<Sede> obtenerSedeMasActiva(List<Sede> sedes) {
        return sedes.stream()
                .max(Comparator.comparingLong(Sede::misionesCompletadasHoy));
    }

    // 2. Comparator Compuesto de 3 criterios: Tasa de éxito DESC -> Misiones DESC -> Nombre ASC
    public List<Sede> clasificarRankingSedes(List<Sede> sedes) {
        return sedes.stream()
                .sorted(Comparator.comparingDouble(Sede::tasaExito).reversed()
                        .thenComparing(Comparator.comparingLong(Sede::misionesCompletadasHoy).reversed())
                        .thenComparing(Sede::nombre))
                .toList();
    }

    // 3. Collectors.teeing() — Total entregas y tiempo promedio en UNA SOLA pasada
    public Map.Entry<Long, Double> calcularMetricasSede(List<Mision> misiones, String sede) {
        return misiones.stream()
                .filter(m -> sede.equalsIgnoreCase(m.sede()) && m.estado() == EstadoMision.ENTREGADA)
                .collect(Collectors.teeing(
                        Collectors.counting(),
                        Collectors.averagingDouble(Mision::tiempoEntregaMinutos),
                        Map::entry
                ));
    }

    // 4. groupingBy Anidado — Drones agrupados por Sede y luego por TipoDrone
    public Map<String, Map<TipoDrone, Long>> contarDronesPorSedeYTipo(List<Drone> flota) {
        return flota.stream()
                .collect(Collectors.groupingBy(
                        Drone::sede,
                        Collectors.groupingBy(Drone::tipo, Collectors.counting())
                ));
    }

    // 5. Tasa de éxito porcentual por sede
    public Map<String, Double> tasaExitoPorSede(List<Mision> misiones) {
        Map<String, List<Mision>> porSede = misiones.stream()
                .collect(Collectors.groupingBy(Mision::sede));

        return porSede.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            List<Mision> lista = entry.getValue();
                            if (lista.isEmpty()) return 0.0;
                            long exitosas = lista.stream().filter(m -> m.estado() == EstadoMision.ENTREGADA).count();
                            return ((double) exitosas / lista.size()) * 100.0;
                        }
                ));
    }
}