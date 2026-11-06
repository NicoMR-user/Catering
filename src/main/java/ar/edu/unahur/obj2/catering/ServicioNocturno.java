package ar.edu.unahur.obj2.catering;

public class ServicioNocturno extends Adicional {

    private static final double RECARGO = 20.0; // porcentaje

    public ServicioNocturno(Propuesta propuesta) {
        super(propuesta);
    }

    @Override
    public double precio() {
        double acumulado = precioDePropuesta();
        return acumulado + acumulado * RECARGO / 100;
    }

    @Override
    protected String nombreAdicional() {
        return "Servicio nocturno";
    }
}
