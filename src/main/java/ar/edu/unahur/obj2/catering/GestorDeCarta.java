package ar.edu.unahur.obj2.catering;

import java.util.ArrayList;
import java.util.List;

public class GestorDeCarta {

    private final List<Modificacion> historial = new ArrayList<>();

    public void aplicar(Modificacion modificacion) {
        historial.addAll(modificacion.aplicar());
    }

    public void revertirUltima() {
        if (historial.isEmpty()) {
            throw new NadaParaRevertirException();
        }
        int ultimo = historial.size() - 1;
        historial.get(ultimo).revertir();
        historial.remove(ultimo);
    }
}
