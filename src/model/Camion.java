package model;
import java.util.ArrayList;
import java.util.List;

public class Camion {

    private int idCamion;
    private String patente;
    private boolean estaRefrigerado;
    private int capacidad;
    private List<Paquete> paquetesAsignados;
    private int pesoActual;

    public Camion(int idCamion, String patente, boolean estaRefrigerado, int capacidad) {
        this.idCamion = idCamion;
        this.patente = patente;
        this.estaRefrigerado = estaRefrigerado;
        this.capacidad = capacidad;
        this.paquetesAsignados = new ArrayList<>();
        this.pesoActual = 0;
    }

    public int getIdCamion() {
        return idCamion;
    }
    public String getPatente() {
        return patente;
    }
    public boolean isEstaRefrigerado() {
        return estaRefrigerado;
    }
    public int getCapacidad() {
        return capacidad;
    }

    /*
     * Verifica si el paquete puede cargarse en el camion
     * controla que no supere la capacidad y
     * si el paquete contiene alimentos el camion este refrigerado
     */

    public boolean puedeCargar(Paquete paquete) {
        if (this.pesoActual + paquete.getPeso() > this.capacidad) {
            return false;
        }
        if (paquete.isContieneAlimentos() && !this.estaRefrigerado) {
            return false;
        }
        return true;
    }
    //Carga un paquete en el camion y actualiza el peso actual
    public void cargarPaquete(Paquete paquete) {
        this.paquetesAsignados.add(paquete);
        this.pesoActual += paquete.getPeso();
    }

    //Quita el ultimo paquete cargado para backtracking
    public void quitarUltimoPaquete() {
        Paquete paquete = this.paquetesAsignados.remove(this.paquetesAsignados.size() - 1);
        this.pesoActual -= paquete.getPeso();
    }

    public List<Paquete> getPaquetesAsignados() {
        return this.paquetesAsignados;
    }

    public int getPesoActual() {
        return this.pesoActual;
    }
    //ejecutar los algoritmos desde cero
    public void limpiarCarga() {
        this.paquetesAsignados.clear();
        this.pesoActual = 0;
    }

    @Override
    public String toString() {
        return "Camion " + idCamion 
                + " [" + patente 
                + ", refrigerado=" + estaRefrigerado 
                + ", capacidad=" + capacidad + "]";
    }
}
