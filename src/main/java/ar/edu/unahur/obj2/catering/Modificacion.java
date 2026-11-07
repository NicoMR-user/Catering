package ar.edu.unahur.obj2.catering;

import java.util.List;

public interface Modificacion {

    List<Modificacion> aplicar();

    void revertir();
}