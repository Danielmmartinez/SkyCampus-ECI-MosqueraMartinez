package co.edu.escuelaing.dosw.skycampus.core.services;

import co.edu.escuelaing.dosw.skycampus.core.patterns.composite.ComponenteRuta;

public class PlanificadorRutaEnterprise {
    private final ServicioAerocivil aerocivil;
    private final ApiMeteorologica clima;

    public PlanificadorRutaEnterprise(ServicioAerocivil aerocivil, ApiMeteorologica clima) {
        this.aerocivil = aerocivil;
        this.clima = clima;
    }

    public boolean esRutaViable(ComponenteRuta ruta, String origen, String destino, double altitudMetros) {
        // 1. RNF Regulatorio Aerocivil (máximo 120 metros en entorno urbano)
        if (altitudMetros > 120.0) {
            return false;
        }

        // 2. Consulta de autorización
        if (!aerocivil.autorizarVueloInterSede(origen, destino, altitudMetros)) {
            return false;
        }

        // 3. Consulta Meteorológica
        if (!clima.esAptoParaVuelo(origen, destino)) {
            return false;
        }

        // 4. Batería suficiente para el total del Composite (máx 100% de descarga acumulada)
        return ruta.getConsumoBateriaEstimado() <= 85;
    }
}