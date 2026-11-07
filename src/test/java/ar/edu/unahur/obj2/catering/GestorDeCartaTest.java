package ar.edu.unahur.obj2.catering;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class GestorDeCartaTest {

    private final Catalogo catalogo = new Catalogo();
    private final GestorDeCarta gestor = new GestorDeCarta();
    private final Servicio fotografo = new Servicio("Fotógrafo", 100000.0);

    @Test
    void dadoUnAgregado_alAplicarloYRevertirlo_elMenuVuelveAComoEstaba() {
        gestor.aplicar(new AgregarPropuesta(catalogo.menuParrillero, fotografo));
        assertEquals(432000.0, catalogo.menuParrillero.precio());
        assertTrue(catalogo.menuParrillero.incluye("Fotógrafo"));

        gestor.revertirUltima();
        assertEquals(342000.0, catalogo.menuParrillero.precio());
        assertFalse(catalogo.menuParrillero.incluye("Fotógrafo"));
    }

    @Test
    void dadoUnQuitado_alAplicarloYRevertirlo_elMenuRecuperaLaPropuesta() {
        gestor.aplicar(new QuitarPropuesta(catalogo.menuParrillero, catalogo.mesaDulce));
        assertEquals(288000.0, catalogo.menuParrillero.precio());
        assertFalse(catalogo.menuParrillero.incluye("Mesa dulce"));

        gestor.revertirUltima();
        assertEquals(342000.0, catalogo.menuParrillero.precio());
        assertTrue(catalogo.menuParrillero.incluye("Mesa dulce"));
    }

    @Test
    void dadaUnaActualizacionDePrecio_alRevertirla_devuelveElPrecioAnterior() {
        gestor.aplicar(new ActualizarPrecio(catalogo.parrillada, 260000.0));
        assertEquals(260000.0, catalogo.parrillada.precio());

        gestor.revertirUltima();
        assertEquals(240000.0, catalogo.parrillada.precio());
    }

    @Test
    void dadaUnaSecuenciaInmediata_alRevertirTodo_vuelveAlCatalogoInicial() {
        assertEquals(342000.0, catalogo.menuParrillero.precio());

        gestor.aplicar(new AgregarPropuesta(catalogo.menuParrillero, fotografo));
        assertEquals(432000.0, catalogo.menuParrillero.precio());

        gestor.aplicar(new ActualizarPrecio(catalogo.parrillada, 260000.0));
        assertEquals(450000.0, catalogo.menuParrillero.precio());

        gestor.aplicar(new QuitarPropuesta(catalogo.menuParrillero, catalogo.mesaDulce));
        assertEquals(396000.0, catalogo.menuParrillero.precio());

        gestor.revertirUltima();
        assertEquals(450000.0, catalogo.menuParrillero.precio());
        gestor.revertirUltima();
        assertEquals(432000.0, catalogo.menuParrillero.precio());
        gestor.revertirUltima();
        assertEquals(342000.0, catalogo.menuParrillero.precio());

        assertThrows(NadaParaRevertirException.class, gestor::revertirUltima);
    }

    @Test
    void dadasDosActualizacionesDelMismoServicioCreadasAntes_alAplicarlasYRevertir_recuperaCadaPrecioAnterior() {
        Modificacion primera = new ActualizarPrecio(catalogo.parrillada, 260000.0);
        Modificacion segunda = new ActualizarPrecio(catalogo.parrillada, 300000.0);

        gestor.aplicar(primera);
        gestor.aplicar(segunda);
        assertEquals(300000.0, catalogo.parrillada.precio());

        gestor.revertirUltima();
        assertEquals(260000.0, catalogo.parrillada.precio());

        gestor.revertirUltima();
        assertEquals(240000.0, catalogo.parrillada.precio());
    }

    @Test
    void dadoUnLoteValido_alAplicarlo_cadaModificacionQuedaEnElHistorialEnOrden() {
        gestor.aplicar(loteDeLosPasosUnoATres());
        assertEquals(396000.0, catalogo.menuParrillero.precio());

        gestor.revertirUltima();
        assertEquals(450000.0, catalogo.menuParrillero.precio());

        gestor.revertirUltima();
        gestor.revertirUltima();
        assertEquals(342000.0, catalogo.menuParrillero.precio());

        assertThrows(NadaParaRevertirException.class, gestor::revertirUltima);
    }

    @Test
    void dadoUnLoteConUnaModificacionInvalida_alAplicarlo_dejaElCatalogoComoEstaba() {
        gestor.aplicar(new AgregarPropuesta(catalogo.menuParrillero, fotografo));

        Modificacion lote = new Lote(List.of(
                new ActualizarPrecio(catalogo.parrillada, 260000.0),
                new QuitarPropuesta(catalogo.menuParrillero, catalogo.barraDeTragos)));

        assertThrows(PropuestaNoIncluidaException.class, () -> gestor.aplicar(lote));
        assertEquals(432000.0, catalogo.menuParrillero.precio());
        assertEquals(240000.0, catalogo.parrillada.precio());
    }

    @Test
    void dadoUnLoteQueFallo_alRevertir_soloSeRevierteLoAplicadoAntesDelLote() {
        gestor.aplicar(new AgregarPropuesta(catalogo.menuParrillero, fotografo));
        Modificacion lote = new Lote(List.of(
                new ActualizarPrecio(catalogo.parrillada, 260000.0),
                new QuitarPropuesta(catalogo.menuParrillero, catalogo.barraDeTragos)));
        assertThrows(PropuestaNoIncluidaException.class, () -> gestor.aplicar(lote));

        gestor.revertirUltima();
        assertEquals(342000.0, catalogo.menuParrillero.precio());
        assertThrows(NadaParaRevertirException.class, gestor::revertirUltima);
    }

    @Test
    void dadoUnGestorSinHistorial_alRevertir_lanzaNadaParaRevertirException() {
        assertThrows(NadaParaRevertirException.class, gestor::revertirUltima);
    }

    @Test
    void dadaUnaPropuestaQueNoEsComponenteDirecto_alQuitarla_lanzaExcepcionYNoAlteraElMenu() {
        assertThrows(PropuestaNoIncluidaException.class,
                () -> gestor.aplicar(new QuitarPropuesta(catalogo.menuParrillero, catalogo.dj)));
        assertEquals(342000.0, catalogo.menuParrillero.precio());
        assertEquals(3, catalogo.menuParrillero.cantidadDeServicios());
    }

    @Test
    void dadaUnaPropuestaIndirectaDelMenu_alQuitarla_lanzaPropuestaNoIncluidaException() {
        // La Mesa dulce está dentro de Evento Completo, pero no como componente directo.
        assertThrows(PropuestaNoIncluidaException.class,
                () -> gestor.aplicar(new QuitarPropuesta(catalogo.eventoCompleto, catalogo.mesaDulce)));
    }

    @Test
    void dadaUnaActualizacionConPrecioNegativo_alAplicarla_lanzaExcepcionYNoQuedaEnElHistorial() {
        assertThrows(PrecioNegativoException.class,
                () -> gestor.aplicar(new ActualizarPrecio(catalogo.parrillada, -1.0)));
        assertEquals(240000.0, catalogo.parrillada.precio());
        assertThrows(NadaParaRevertirException.class, gestor::revertirUltima);
    }

    @Test
    void dadoUnLoteRevertidoDirectamente_alRevertirlo_deshaceTodasSusModificaciones() {
        Modificacion lote = loteDeLosPasosUnoATres();
        lote.aplicar();
        lote.revertir();
        assertEquals(342000.0, catalogo.menuParrillero.precio());
    }

    @Test
    void dadoUnServicioConPrecioActualizado_alCotizarUnMenuQueLoContiene_usaElPrecioNuevo() {
        gestor.aplicar(new ActualizarPrecio(catalogo.parrillada, 260000.0));
        assertEquals(360000.0, catalogo.menuParrillero.precio());
    }

    private Modificacion loteDeLosPasosUnoATres() {
        return new Lote(List.of(
                new AgregarPropuesta(catalogo.menuParrillero, fotografo),
                new ActualizarPrecio(catalogo.parrillada, 260000.0),
                new QuitarPropuesta(catalogo.menuParrillero, catalogo.mesaDulce)));
    }
}
