package ar.edu.unahur.obj2.catering;

public interface Propuesta {

    double precio();

    String descripcion();

    int cantidadDeServicios();

    boolean incluye(String nombreDeServicio);
}