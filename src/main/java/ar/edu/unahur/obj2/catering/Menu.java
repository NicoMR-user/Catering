package ar.edu.unahur.obj2.catering;

import java.util.List;
import java.util.ArrayList;

public class Menu implements Propuesta{

    private final String nombre;
    private final Double descuento;
    private final List<Propuesta> propuesta = new ArrayList<>();

    public Menu(String nombre, Double descuento) {
        if (descuento < 0 || descuento > 100) {
            throw new DescuentoInvalidoException("El descuento debe estar entre 0 y 100");
        }
        this.nombre = nombre;
        this.descuento = descuento;
    }

    @Override
    public String descripcion() {
        return nombre;
    }

    @Override
    public double precio() {
        Double precioTotal = 0.0;
        for(Propuesta propuesta : this.propuesta){
            precioTotal += propuesta.precio();
        }
        return precioTotal * (100 - descuento) / 100;
    }

    @Override
    public int cantidadDeServicios() {
        return propuesta.stream().mapToInt(Propuesta::cantidadDeServicios).sum();
    }

    @Override
    public boolean incluye(String nombre) {
        return propuesta.stream().anyMatch(c -> c.incluye(nombre));
    }

    public void agregar(Propuesta unaPropuesta) {
        propuesta.add(unaPropuesta);
    }

}