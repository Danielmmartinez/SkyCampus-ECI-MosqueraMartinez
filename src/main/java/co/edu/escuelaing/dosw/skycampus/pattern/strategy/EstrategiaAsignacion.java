package co.edu.escuelaing.dosw.skycampus.pattern.strategy;

import co.edu.escuelaing.dosw.skycampus.model.Drone;
import co.edu.escuelaing.dosw.skycampus.model.Mision;
import co.edu.escuelaing.dosw.skycampus.model.TipoDrone;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface EstrategiaAsignacion {
    Optional<Drone> seleccionarDrone(List<Drone> flota, Mision mision);
}

class AsignacionMayorBateria implements EstrategiaAsignacion {
    @Override
    public Optional<Drone> seleccionarDrone(List<Drone> flota, Mision mision) {
        return flota.stream()
                .filter(Drone::disponible)
                .max(Comparator.comparingInt(Drone::bateria));
    }
}

class AsignacionUrgenteExpress implements EstrategiaAsignacion {
    @Override
    public Optional<Drone> seleccionarDrone(List<Drone> flota, Mision mision) {
        // Para misiones urgentes, busca preferiblemente drones EXPRESS disponibles
        return flota.stream()
                .filter(Drone::disponible)
                .filter(d -> d.tipo() == TipoDrone.EXPRESS)
                .max(Comparator.comparingInt(Drone::bateria))
                .or(() -> flota.stream()
                        .filter(Drone::disponible)
                        .max(Comparator.comparingInt(Drone::bateria)));
    }
}