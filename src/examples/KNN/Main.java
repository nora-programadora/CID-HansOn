import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DataLoader x = new DataLoader("C:\\Users\\luis_\\Documents\\plantas.txt");
        ArrayList<Dato> e = x.leerDatos();
        KNN knn = new KNN(e);
        int option = JOptionPane.showConfirmDialog(null, "¿Las unidades de medida son diferentes?");
        System.out.println(option);
        if (option == 0) {
            knn.estandarizar();
        }
        Dato ejemplo = new Dato(5.2, 3.1, "");
        knn.clasificarDato(ejemplo, 3);
    }

}