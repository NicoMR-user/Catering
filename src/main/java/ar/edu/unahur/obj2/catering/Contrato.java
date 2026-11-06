package ar.edu.unahur.obj2.catering;

public class Contrato {

    private final String cliente;
    private final double importe;
    private final String descripcion;

    public Contrato(String cliente, Propuesta propuesta) {
        this.cliente = cliente;
        this.importe = propuesta.precio();
        this.descripcion = propuesta.descripcion();
    }

    public String cliente() {
        return cliente;
    }

    public double importe() {
        return importe;
    }

    public String descripcion() {
        return descripcion;
    }
}
