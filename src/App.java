import service.Servicios;

public class App {
    public static void main(String[] args) throws Exception {
        Servicios servicios = new Servicios("examples/Camiones.csv", "examples/Paquetes.csv");
        System.out.println("--------------------------------------------------------");
        System.out.println("servicio 1: ");
        System.out.println(servicios.servicio1("P001")); // Paquete [idPaquete=1, codigoPaquete=P001, peso=30, contieneAlimentos=true, nivelUrgencia=80]
        System.out.println(servicios.servicio1("P999")); // null


        System.out.println("--------------------------------------------------------");
        System.out.println("contiene alimentos: ");
        System.out.println(servicios.servicio2(true));
        System.out.println("&&&&&&&&&&&&&&&&&");
        System.out.println("no contiene alimentos: ");
        System.out.println(servicios.servicio2(false));

    }
}
