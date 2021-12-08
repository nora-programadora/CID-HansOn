import java.util.ArrayList;

public class KNN {

    ArrayList<Dato> values;
    ArrayList<Double> x1 = new ArrayList();
    ArrayList<Double> x2 = new ArrayList();

    public KNN(ArrayList<Dato> values) {
        this.values = values;
        for (int i = 0; i < this.values.size(); i++) {
            x1.add(values.get(i).getX1());
            x2.add(values.get(i).getX2());
        }

    }

    public void estandarizar() {
        double mediaX1, desviacionEstandarX1;
        double mediaX2, desviacionEstandarX2;
        Serie serieX1 = new Serie(x1);
        Serie serieX2 = new Serie(x2);
        mediaX1 = serieX1.promedio();
        desviacionEstandarX1 = serieX1.desviacionEstandar();
        mediaX2 = serieX2.promedio();
        desviacionEstandarX2 = serieX2.desviacionEstandar();
        for (int i = 0; i < values.size(); i++) {
            double aux1 = values.get(i).getX1();
            double aux2 = values.get(i).getX2();
            aux1 = (aux1 - mediaX1) / desviacionEstandarX1;
            aux2 = (aux2 - mediaX2) / desviacionEstandarX2;
            values.get(i).setX1(aux1);
            values.get(i).setX2(aux2);
        }
    }

    public void clasificarDato(Dato e, int vecinos) {
        double mediaX1, desviacionEstandarX1;
        double mediaX2, desviacionEstandarX2;
        Serie serieX1 = new Serie(x1);
        Serie serieX2 = new Serie(x2);
        mediaX1 = serieX1.promedio();
        desviacionEstandarX1 = serieX1.desviacionEstandar();
        mediaX2 = serieX2.promedio();
        desviacionEstandarX2 = serieX2.desviacionEstandar();
        double x1Normalizado, x2Normalizado;
        x1Normalizado = e.getX1();
        x2Normalizado = e.getX2();
        x1Normalizado = (x1Normalizado - mediaX1) / desviacionEstandarX1;
        x2Normalizado = (x2Normalizado - mediaX2) / desviacionEstandarX2;
        e.setX1(x1Normalizado);
        e.setX2(x2Normalizado);
        Dato[] vecinosCercanos = new Dato[vecinos];
        Double[] distancias = new Double[values.size()];
        ArrayList<Dato> listaAux = values;
        System.out.println(e.toString());
        for (int i = 0; i < values.size(); i++) {
            double x1 = values.get(i).getX1();
            double x2 = values.get(i).getX2();
            double cuadX1 = Math.pow(x1 - e.getX1(), 2);
            double cuadX2 = Math.pow(x2 - e.getX2(), 2);
            double suma = cuadX1 + cuadX2;
            distancias[i] = Math.sqrt(suma);
        }
        Dato aux;
        double distanciaAux;
        for (int i = 0; i < values.size(); i++) {
            for (int j = 0; j < values.size() - 1; j++) {
                if (distancias[j] > distancias[j + 1]) {
                    aux = listaAux.get(j);
                    distanciaAux = distancias[j];
                    listaAux.set(j, listaAux.get(j + 1));
                    distancias[j] = distancias[j + 1];
                    listaAux.set(j + 1, aux);
                    distancias[j + 1] = distanciaAux;
                }
            }
        }

        for (int i = 0; i < vecinos; i++) {
            vecinosCercanos[i] = listaAux.get(i);
        }
        ArrayList<String> categorias = encontrarCategorias();
        int[] contadores = new int[categorias.size()];
        /*for(int i = 0; i<vecinos; i++)
        {
            if(categoria1.equals(""))
                categoria1 = vecinosCercanos[i].getEtiqueta();
            if(categoria2.equals("") && !vecinosCercanos[i].getEtiqueta().equals(categoria1))
                categoria2 = vecinosCercanos[i].getEtiqueta();
        }*/
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println(categorias.get(i));
        }
        for (int i = 0; i < categorias.size(); i++) {
            for (int j = 0; j < vecinos; j++) {
                if (categorias.get(i).equals(vecinosCercanos[j].getEtiqueta())) {
                    contadores[i]++;
                }
            }
        }
        int posMayor = encontrarPosMax(contadores);
        System.out.println("El dato se ha clasificado como: " + categorias.get(posMayor));
        e.setEtiqueta(categorias.get(posMayor));
        values.add(e);
    }

    private ArrayList<String> encontrarCategorias() {
        ArrayList<String> categorias = new ArrayList();
        categorias.add(values.get(1).getEtiqueta());
        for (int i = 0; i < values.size(); i++) {
            if (!isIncluded(categorias, values.get(i).getEtiqueta())) {
                categorias.add(values.get(i).getEtiqueta());
            }
        }
        return categorias;
    }

    private boolean isIncluded(ArrayList<String> listaCategorias, String categoria) {
        boolean flag = false;
        for (int i = 0; i < listaCategorias.size(); i++) {
            if (listaCategorias.get(i).equals(categoria)) {
                flag = true;
            }
        }
        return flag;
    }

    private int encontrarPosMax(int[] datos) {
        int max = -1;
        int pos = -1;
        for (int i = 0; i < datos.length; i++) {
            if (datos[i] > max) {
                max = datos[i];
                pos = i;
            }
        }
        return pos;

    }

}