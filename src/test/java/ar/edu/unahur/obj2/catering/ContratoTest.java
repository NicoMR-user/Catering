package ar.edu.unahur.obj2.catering;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ContratoTest {

    private final Catalogo catalogo = new Catalogo();

    @Test
    void dadoUnContratoSobreUnMenuConAdicional_alConsultarElImporte_devuelveElPrecioConAdicional() {
        Contrato contrato = new Contrato("Lucía", new MozoAdicional(catalogo.menuParrillero));

        assertEquals(360000.0, contrato.importe());
    }

    @Test
    void dadoUnContratoSobreUnMenuConAdicional_alConsultarLaDescripcion_devuelveLaDelMomentoDeContratar() {
        Contrato contrato = new Contrato("Lucía", new MozoAdicional(catalogo.menuParrillero));

        assertEquals("Menú Parrillero + Mozos adicionales", contrato.descripcion());
    }

    @Test
    void dadoUnContrato_alConsultarElCliente_devuelveElNombre() {
        Contrato contrato = new Contrato("Lucía", catalogo.menuParrillero);

        assertEquals("Lucía", contrato.cliente());
    }
}
