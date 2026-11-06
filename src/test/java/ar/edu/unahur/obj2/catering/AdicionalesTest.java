package ar.edu.unahur.obj2.catering;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AdicionalesTest {

    private final Catalogo catalogo = new Catalogo();

    @Test
    void dadoUnServicio_alContratarMozosAdicionales_suma18milYAgregaLaDescripcion() {
        Propuesta propuesta = new MozoAdicional(catalogo.mesaDulce);

        assertEquals(78000.0, propuesta.precio());
        assertEquals("Mesa dulce + Mozos adicionales", propuesta.descripcion());
    }

    @Test
    void dadoUnServicio_alContratarVajillaPremium_suma12milYAgregaLaDescripcion() {
        Propuesta propuesta = new VajillaPremium(catalogo.mesaDulce);

        assertEquals(72000.0, propuesta.precio());
        assertEquals("Mesa dulce + Vajilla premium", propuesta.descripcion());
    }

    @Test
    void dadoUnServicio_alContratarServicioNocturno_suma20PorCientoYAgregaLaDescripcion() {
        Propuesta propuesta = new ServicioNocturno(catalogo.mesaDulce);

        assertEquals(72000.0, propuesta.precio());
        assertEquals("Mesa dulce + Servicio nocturno", propuesta.descripcion());
    }

    @Test
    void dadoUnMenuConMozosYLuegoNocturno_alCalcularPrecio_elNocturnoIncluyeLosMozos() {
        Propuesta propuesta = new ServicioNocturno(new MozoAdicional(catalogo.menuParrillero));

        assertEquals(432000.0, propuesta.precio());
        assertEquals("Menú Parrillero + Mozos adicionales + Servicio nocturno", propuesta.descripcion());
    }

    @Test
    void dadoUnServicioNocturnoAplicadoAntesQueMozos_alCalcularPrecio_elOrdenAlteraElPrecioFinal() {
        Propuesta propuesta = new MozoAdicional(new ServicioNocturno(catalogo.menuParrillero));

        assertEquals(428400.0, propuesta.precio());
        assertEquals("Menú Parrillero + Servicio nocturno + Mozos adicionales", propuesta.descripcion());
    }

    @Test
    void dadoUnMismoAdicionalRepetido_alCalcularPrecio_seAplicaCadaVez() {
        Propuesta propuesta = new VajillaPremium(new VajillaPremium(catalogo.brindis));

        assertEquals(74000.0, propuesta.precio());
        assertEquals("Brindis de bienvenida + Vajilla premium + Vajilla premium", propuesta.descripcion());
    }

    @Test
    void dadoUnServicioNocturnoRepetido_alCalcularPrecio_elSegundoRecargaSobreLoAcumulado() {
        Propuesta propuesta = new ServicioNocturno(new ServicioNocturno(catalogo.mesaDulce));

        assertEquals(86400.0, propuesta.precio());
        assertEquals("Mesa dulce + Servicio nocturno + Servicio nocturno", propuesta.descripcion());
    }

    @Test
    void dadoUnMenuQueContieneOtrosMenus_alContratarVajilla_sumaSobreElPrecioDelMenu() {
        Propuesta propuesta = new VajillaPremium(catalogo.eventoCompleto);

        assertEquals(530800.0, propuesta.precio());
        assertEquals("Evento Completo + Vajilla premium", propuesta.descripcion());
    }

    @Test
    void dadoUnMenuConAdicionales_alContarServiciosEIncluir_losAdicionalesNoAgreganServicios() {
        Propuesta propuesta = new ServicioNocturno(new MozoAdicional(catalogo.eventoCompleto));

        assertEquals(6, propuesta.cantidadDeServicios());
        assertTrue(propuesta.incluye("DJ y sonido"));
        assertFalse(propuesta.incluye("Mozos Adicionales"));
    }

    @Test
    void dadaUnaPropuestaConAdicional_alConsultarLaOriginal_sigueIgual() {
        new ServicioNocturno(new MozoAdicional(catalogo.menuParrillero));

        assertEquals(342000.0, catalogo.menuParrillero.precio());
        assertEquals("Menú Parrillero", catalogo.menuParrillero.descripcion());
    }

    @Test
    void dadoUnServicioConAdicional_alUsarloDentroDeUnMenu_elMenuLoTrataComoCualquierPropuesta() {
        Menu combo = new Menu("Combo", 0.0);
        combo.agregar(new VajillaPremium(catalogo.mesaDulce));

        assertEquals(72000.0, combo.precio());
        assertEquals(1, combo.cantidadDeServicios());
    }
}