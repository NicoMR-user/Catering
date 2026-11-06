package ar.edu.unahur.obj2.catering;

public class Servicio implements Propuesta {
    private final String nombre;
    private final double precio;

    public Servicio(String nombre, double precio) {
        if (precio < 0) {
            throw new PrecioNegativoException(precio);
        }
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public double precio() {
        return precio;
    }

    @Override
    public String descripcion() {
        return nombre;
    }

    @Override
    public int cantidadDeServicios() {
        return 1;
    }

    @Override
    public boolean incluye(String nombreDeServicio) {
        return nombre.equals(nombreDeServicio);
    }
}
