package ar.edu.unahur.obj2.catering;

public class VajillaPremium extends Adicional {

    private static final double COSTO = 12000.0;

    public VajillaPremium(Propuesta propuesta) {
        super(propuesta);
    }

    @Override
    public double precio() {
        return precioDePropuesta() + COSTO;
    }

    @Override
    protected String nombreAdicional() {
        return "Vajilla premium";
    }
}
