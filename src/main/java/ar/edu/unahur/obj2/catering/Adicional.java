package ar.edu.unahur.obj2.catering;

public abstract class Adicional implements Propuesta {

    private final Propuesta propuesta;

    protected Adicional(Propuesta propuesta) {
        this.propuesta = propuesta;
    }

    protected double precioDePropuesta() {
        return propuesta.precio();
    }

    protected abstract String nombreAdicional();

    @Override
    public String descripcion() {
        return propuesta.descripcion() + " + " + nombreAdicional();
    }

    @Override
    public int cantidadDeServicios() {
        return propuesta.cantidadDeServicios();
    }

    @Override
    public boolean incluye(String nombreDeServicio) {
        return propuesta.incluye(nombreDeServicio);
    }
}
