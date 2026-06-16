package algorithm;

import java.util.List;
import model.Camion;
import model.Paquete;

public class Greedy {

    private List<Camion> camiones;
    private List<Paquete> paquetes;

    private int candidatosConsiderados;

    public Greedy(List<Camion> camiones, List<Paquete> paquetes) {
        this.camiones = camiones;
        this.paquetes = paquetes;
        this.candidatosConsiderados = 0;
    }

    /*
     * Estrategia Greedy:
     * El candidato es el paquete mas pesado que todavía no fue procesado
     * idea: es intentar asignar primero los paquetes de mayor peso porque si
     * quedan sin asignar aumentan más el peso total no asignado
     * La asignacion se busca entre los camiones válidos aquel que quede con
     * menor espacio libre luego de cargar el paquete e intentar aprovechar
     * mejor la capacidad disponible.
     * El paquete que no puede asignarse a ningún camion se agrega a la lista de
     * paquetes no asignados
     */
}