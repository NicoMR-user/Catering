package ar.edu.unahur.obj2.catering;

public class PropuestaNoIncluidaException extends RuntimeException {
    public PropuestaNoIncluidaException(String descripcion) {
        super("La propuesta no es componente directo del menú: " + descripcion);
    }
}
