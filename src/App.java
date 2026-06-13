import model.Camion;
import model.Paquete;
public class App {
    public static void main(String[] args) throws Exception {
        Camion c1 = new Camion(999, "AA 000 AA", true, 100);
        Paquete p1 = new Paquete(11111111, "PBA 000", 30, true, 80);
        System.out.println(c1);
        System.out.println(p1);
    }
}
