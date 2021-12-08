
public class Dato {

    private double x1;
    private double x2;
    private String etiqueta;

    public Dato(double x1, double x2, String etiqueta) {
        this.x1 = x1;
        this.x2 = x2;
        this.etiqueta = etiqueta;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String toString() {
        String resultado = "(" + x1 + ", " + x2 + ", " + etiqueta + ")";
        return resultado;
    }
}