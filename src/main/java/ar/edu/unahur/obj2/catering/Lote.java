package ar.edu.unahur.obj2.catering;

import java.util.ArrayList;
import java.util.List;

public class Lote implements Modificacion {

    private final List<Modificacion> modificaciones;
    private List<Modificacion> aplicadas = new ArrayList<>();

    public Lote(List<Modificacion> modificaciones) {
        this.modificaciones = new ArrayList<>(modificaciones);
    }

    @Override
    public List<Modificacion> aplicar() {
        List<Modificacion> hechas = new ArrayList<>();
        try {
            for (Modificacion modificacion : modificaciones) {
                hechas.addAll(modificacion.aplicar());
            }
        } catch (RuntimeException e) {
            revertirEnOrdenInverso(hechas);
            throw e;
        }
        aplicadas = hechas;
        return hechas;
    }

    @Override
    public void revertir() {
        revertirEnOrdenInverso(aplicadas);
        aplicadas = new ArrayList<>();
    }

    private static void revertirEnOrdenInverso(List<Modificacion> hechas) {
        for (int i = hechas.size() - 1; i >= 0; i--) {
            hechas.get(i).revertir();
        }
    }
}