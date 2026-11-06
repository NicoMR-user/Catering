package ar.edu.unahur.obj2.catering;

public class MozoAdicional extends Adicional {

    private static final double COSTO = 18000.0;

    public MozoAdicional(Propuesta propuesta) {
        super(propuesta);
    }

    @Override
    public double precio() {
        return precioDePropuesta() + COSTO;
    }

    @Override
    protected String nombreAdicional() {
        return "Mozos adicionales";
    }
}
