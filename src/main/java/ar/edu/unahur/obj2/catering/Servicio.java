package ar.edu.unahur.obj2.catering;

public class Servicio implements Propuesta {
    private final String nombre;
    private double precio;

    public Servicio(String nombre, double precio) {
        validarPrecio(precio);
        this.nombre = nombre;
        this.precio = precio;
    }

    public void actualizarPrecio(double nuevoPrecio) {
        validarPrecio(nuevoPrecio);
        this.precio = nuevoPrecio;
    }

    private static void validarPrecio(double precio) {
        if (precio < 0) {
            throw new PrecioNegativoException(precio);
        }
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
