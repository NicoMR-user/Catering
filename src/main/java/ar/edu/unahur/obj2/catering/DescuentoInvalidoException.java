package ar.edu.unahur.obj2.catering;

public class DescuentoInvalidoException extends RuntimeException {

    public DescuentoInvalidoException(String descuento) {
        super("El descuento tiene que estar entre 0 y 100: " + descuento);
    }
}
