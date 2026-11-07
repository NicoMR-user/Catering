package ar.edu.unahur.obj2.catering;

import java.util.List;

public class QuitarPropuesta implements Modificacion {

    private final Menu menu;
    private final Propuesta propuesta;

    public QuitarPropuesta(Menu menu, Propuesta propuesta) {
        this.menu = menu;
        this.propuesta = propuesta;
    }

    @Override
    public List<Modificacion> aplicar() {
        menu.quitar(propuesta);
        return List.of(this);
    }

    @Override
    public void revertir() {
        menu.agregar(propuesta);
    }
}