package ar.edu.unahur.obj2.catering;

import java.util.List;

public class AgregarPropuesta implements Modificacion {

    private final Menu menu;
    private final Propuesta propuesta;

    public AgregarPropuesta(Menu menu, Propuesta propuesta) {
        this.menu = menu;
        this.propuesta = propuesta;
    }

    @Override
    public List<Modificacion> aplicar() {
        menu.agregar(propuesta);
        return List.of(this);
    }

    @Override
    public void revertir() {
        menu.quitar(propuesta);
    }
}
