package co.edu.escuelaing.dosw.skycampus.solid;

import co.edu.escuelaing.dosw.skycampus.model.Drone;
import co.edu.escuelaing.dosw.skycampus.model.EstadoMision;
import co.edu.escuelaing.dosw.skycampus.model.Mision;
import co.edu.escuelaing.dosw.skycampus.model.TipoCarga;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolidTest {

    @Mock
    private RepositorioMision repositorio;

    @Mock
    private AlertaOperador alerta;

    @InjectMocks
    private AsignadorMision asignador;

    @Test
    @DisplayName("AsignadorMision: Guarda en repositorio y envía alerta con Mockito")
    void asignadorMision_guardaYNotifica() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A");
        Mision mision = new Mision("M-01", drone, "Bloque A", "Biblioteca", TipoCarga.SOBRE, EstadoMision.PENDIENTE);

        asignador.asignar(drone, mision);

        verify(repositorio, times(1)).guardar(mision);
        verify(alerta, times(1)).enviarAlerta("Operador-01", "Misión asignada al drone: D-01");
    }

    @Test
    @DisplayName("GeneradorReporte: Genera arreglo de bytes del reporte PDF")
    void generadorReporte_retornaBytes() {
        GeneradorReporte generador = new GeneradorReporte();
        byte[] pdf = generador.generarReportePdf(List.of());

        assertNotNull(pdf);
        assertEquals(0, pdf.length);
    }

    @Test
    @DisplayName("EstrategiaRuta: Interfaz calcula ruta correctamente")
    void estrategiaRuta_implementacionRetornaRuta() {
        EstrategiaRuta estrategia = (origen, destino) -> "Ruta directa de " + origen + " a " + destino;
        String ruta = estrategia.calcularRuta("Bloque A", "Bloque B");

        assertEquals("Ruta directa de Bloque A a Bloque B", ruta);
    }
}