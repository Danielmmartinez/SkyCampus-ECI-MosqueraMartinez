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
    @DisplayName("Enterprise: Planificación viable cuando Aerocivil aprueba, clima es apto y batería alcanza")
    void esRutaViable_cumpleTodasLasCondiciones() {
        Mockito.when(aerocivil.autorizarVueloInterSede("ECI", "UNAL", 100.0)).thenReturn(true);
        Mockito.when(clima.esAptoParaVuelo("ECI", "UNAL")).thenReturn(true);
        Mockito.when(ruta.getConsumoBateriaEstimado()).thenReturn(70);

        PlanificadorRutaEnterprise planificador = new PlanificadorRutaEnterprise(aerocivil, clima);
        assertTrue(planificador.esRutaViable(ruta, "ECI", "UNAL", 100.0));
    }

    @Test
    @DisplayName("Enterprise: Restricción RNF Aerocivil - Altitud superior a 120m es rechazada inmediatamente")
    void esRutaViable_altitudExcesiva_rechazaInmediatamente() {
        PlanificadorRutaEnterprise planificador = new PlanificadorRutaEnterprise(aerocivil, clima);
        assertFalse(planificador.esRutaViable(ruta, "ECI", "UNAL", 130.0));
        Mockito.verifyNoInteractions(aerocivil);
    }
}