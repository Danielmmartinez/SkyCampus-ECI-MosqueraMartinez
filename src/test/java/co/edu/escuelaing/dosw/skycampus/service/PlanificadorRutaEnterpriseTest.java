package co.edu.escuelaing.dosw.skycampus.service;

import co.edu.escuelaing.dosw.skycampus.core.patterns.composite.ComponenteRuta;
import co.edu.escuelaing.dosw.skycampus.core.services.ApiMeteorologica;
import co.edu.escuelaing.dosw.skycampus.core.services.PlanificadorRutaEnterprise;
import co.edu.escuelaing.dosw.skycampus.core.services.ServicioAerocivil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlanificadorRutaEnterpriseTest {

    @Mock
    private ServicioAerocivil aerocivil;

    @Mock
    private ApiMeteorologica clima;

    @Mock
    private ComponenteRuta ruta;

    @Test
    @DisplayName("Enterprise: Planificación viable cuando todo cumple")
    void esRutaViable_cumpleTodasLasCondiciones() {
        Mockito.when(aerocivil.autorizarVueloInterSede("ECI", "UNAL", 100.0)).thenReturn(true);
        Mockito.when(clima.esAptoParaVuelo("ECI", "UNAL")).thenReturn(true);
        Mockito.when(ruta.getConsumoBateriaEstimado()).thenReturn(70);

        PlanificadorRutaEnterprise planificador = new PlanificadorRutaEnterprise(aerocivil, clima);
        assertTrue(planificador.esRutaViable(ruta, "ECI", "UNAL", 100.0));
    }

    @Test
    @DisplayName("Enterprise: Rechaza si altitud > 120m")
    void esRutaViable_altitudExcesiva_rechaza() {
        PlanificadorRutaEnterprise planificador = new PlanificadorRutaEnterprise(aerocivil, clima);
        assertFalse(planificador.esRutaViable(ruta, "ECI", "UNAL", 130.0));
    }

    @Test
    @DisplayName("Enterprise: Rechaza si Aerocivil no autoriza")
    void esRutaViable_aerocivilRechaza_retornaFalse() {
        Mockito.when(aerocivil.autorizarVueloInterSede("ECI", "UNAL", 100.0)).thenReturn(false);

        PlanificadorRutaEnterprise planificador = new PlanificadorRutaEnterprise(aerocivil, clima);
        assertFalse(planificador.esRutaViable(ruta, "ECI", "UNAL", 100.0));
    }

    @Test
    @DisplayName("Enterprise: Rechaza si el clima no es apto")
    void esRutaViable_climaAdverso_retornaFalse() {
        Mockito.when(aerocivil.autorizarVueloInterSede("ECI", "UNAL", 100.0)).thenReturn(true);
        Mockito.when(clima.esAptoParaVuelo("ECI", "UNAL")).thenReturn(false);

        PlanificadorRutaEnterprise planificador = new PlanificadorRutaEnterprise(aerocivil, clima);
        assertFalse(planificador.esRutaViable(ruta, "ECI", "UNAL", 100.0));
    }

    @Test
    @DisplayName("Enterprise: Rechaza si el consumo de batería excede el 85%")
    void esRutaViable_bateriaExcedida_retornaFalse() {
        Mockito.when(aerocivil.autorizarVueloInterSede("ECI", "UNAL", 100.0)).thenReturn(true);
        Mockito.when(clima.esAptoParaVuelo("ECI", "UNAL")).thenReturn(true);
        Mockito.when(ruta.getConsumoBateriaEstimado()).thenReturn(90);

        PlanificadorRutaEnterprise planificador = new PlanificadorRutaEnterprise(aerocivil, clima);
        assertFalse(planificador.esRutaViable(ruta, "ECI", "UNAL", 100.0));
    }
}