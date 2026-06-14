package algorithm;

public class Backtracking {


     /*
     * Raiz: no hay paquetes asignados
     * Estados: cada estado es una asignacion parcial de paquetes a camiones
     * Niveles: cada nivel representa la decision sobre un paquete
     * Hijos: Asignar el paquete actual a cada camion posible
     * Hoja: una hoja es solucion si ya se decidio sobre todos los paquetes
     * Solucion: asignacion completa de paquetes a camiones
     * Objetivo: minimizar el peso total de paquetes no asignados
     * Podas:
     * - !NO asignar si se supera la capacidad del camion
     * - !NO asignar alimentos a un camion no refrigerado
     * - Podar si el peso no asignado ya no mejora la mejor solucion.
     */

    private void back()
}
