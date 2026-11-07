package ar.edu.unahur.obj2.catering;

import java.util.List;

public class ActualizarPrecio implements Modificacion {

    private final Servicio servicio;
    private final double nuevoPrecio;
    private double precioAnterior;

    public ActualizarPrecio(Servicio servicio, double nuevoPrecio) {
        this.servicio = servicio;
        this.nuevoPrecio = nuevoPrecio;
    }

    @Override
    public List<Modificacion> aplicar() {
        double anterior = servicio.precio();
        servicio.actualizarPrecio(nuevoPrecio);
        precioAnterior = anterior;
        return List.of(this);
    }

    @Override
    public void revertir() {
        servicio.actualizarPrecio(precioAnterior);
    }
}
