package co.edu.escuelaing.dosw.skycampus.pattern.factory;

import co.edu.escuelaing.dosw.skycampus.core.patterns.factory.ReporteECIFactory;
import co.edu.escuelaing.dosw.skycampus.core.patterns.factory.ReporteSedeFactory;
import co.edu.escuelaing.dosw.skycampus.core.patterns.factory.ReporteUNALFactory;
import co.edu.escuelaing.dosw.skycampus.core.patterns.factory.ReporteUniandesFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReporteFactoryTest {

    @Test
    @DisplayName("Abstract Factory: ECI genera reporte en HTML completo")
    void factoryECI_generaHTML() {
        ReporteSedeFactory factory = new ReporteECIFactory();
        assertEquals(".html", factory.getExtension());
        assertTrue(factory.crearHeader().formatearHeader("ECI", "2026-10-08").contains("<header>"));
        assertTrue(factory.crearCuerpo().formatearCuerpo("100").contains("<main>"));
    }

    @Test
    @DisplayName("Abstract Factory: UNAL genera reporte en PDF completo")
    void factoryUNAL_generaPDF() {
        ReporteSedeFactory factory = new ReporteUNALFactory();
        assertEquals(".pdf", factory.getExtension());
        assertTrue(factory.crearHeader().formatearHeader("UNAL", "2026-10-08").contains("PDF-HEADER"));
        assertTrue(factory.crearCuerpo().formatearCuerpo("80").contains("PDF-BODY"));
    }

    @Test
    @DisplayName("Abstract Factory: Uniandes genera reporte en JSON completo")
    void factoryUniandes_generaJSON() {
        ReporteSedeFactory factory = new ReporteUniandesFactory();
        assertEquals(".json", factory.getExtension());
        assertTrue(factory.crearHeader().formatearHeader("Uniandes", "2026-10-08").contains("\"universidad\""));
        assertTrue(factory.crearCuerpo().formatearCuerpo("50").contains("\"payload\""));
    }
}