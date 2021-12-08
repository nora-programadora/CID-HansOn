import java.util.ArrayList;

public class Serie {

    private ArrayList<Double> sum;

    public Serie(ArrayList<Double> sum) {
        this.sum = sum;
    }

    public double obtenerSum() {
        double total = 0;
        for (int i = 0; i < sum.size(); i++) {
            total += sum.get(i);
        }
        return total;
    }

    public double sumarProductos(ArrayList<Double> x) {
        double total = 0;
        for (int i = 0; i < sum.size(); i++) {
            total += this.sum.get(i) * x.get(i);
        }
        return total;
    }

    public double promedio() {
        double suma = 0;
        for (int i = 0; i < sum.size(); i++) {
            suma += sum.get(i);
        }
        double promedio = suma / sum.size();
        return promedio;
    }

    public double desviacionEstandar() {
        double promedio = promedio();
        double sumatoria = 0;
        for (int i = 0; i < sum.size(); i++) {
            sumatoria += Math.pow(sum.get(i) - promedio, 2);
        }
        double desviacionEstandar = sumatoria / (sum.size() - 1);
        double resultado = Math.sqrt(desviacionEstandar);
        return resultado;
    }

    public double encontrarMin() {
        double min = sum.get(0);
        for (int i = 1; i < sum.size(); i++) {
            if (sum.get(i) < min) {
                min = sum.get(i);
            }
        }
        return min;
    }

    public double encontrarMax() {
        double max = sum.get(0);
        for (int i = 1; i < sum.size(); i++) {
            if (sum.get(i) > max) {
                max = sum.get(i);
            }
        }
        return max;
    }

}