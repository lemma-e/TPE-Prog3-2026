package model;

public class Paquete {

    private int idPaquete;
    private String codigoPaquete;
    private int peso;
    private boolean contieneAlimentos;
    private int nivelUrgencia;
        public Paquete(int idPaquete, String codigoPaquete, int peso, boolean contieneAlimentos, int nivelUrgencia) {
        this.idPaquete = idPaquete;
        this.codigoPaquete = codigoPaquete;
        this.peso = peso;
        this.contieneAlimentos = contieneAlimentos;
        this.nivelUrgencia = nivelUrgencia;
    }

        public int getIdPaquete() {
            return idPaquete;
        }
        public String getCodigoPaquete() {
            return codigoPaquete;
        }
        public int getPeso() {
            return peso;
        }
        public boolean isContieneAlimentos() {
            return contieneAlimentos;
        }
        public int getNivelUrgencia() {
            return nivelUrgencia;
        }

        @Override
        public String toString() {
            return codigoPaquete 
                    + " [peso=" + peso 
                    + ", alimentos=" + contieneAlimentos 
                    + ", urgencia=" + nivelUrgencia + "]";
        }
    
}
