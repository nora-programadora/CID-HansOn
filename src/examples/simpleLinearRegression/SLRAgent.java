/*
Simple Lineal Regression
Nora Rebeca Aguirre Sánchez
213400059
*/

package examples.simpleLinearRegression;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.*;

public class SLRAgent extends Agent {
    // valor X que usare en la predicción
    private double x;
    // Inicalizador de la interfaz
    private SLRGui myGui;

    // Inicialización del agente
    protected void setup() {
        System.out.println("Agente " + getLocalName() + " compilado.");

        // Creación de la interfaz
        myGui = new SLRGui(this);
        myGui.showGui();
    }

    // Limpieza del agente
    protected void takeDown() {
        // Cierre de interfaz
        myGui.dispose();
        // Mensaje de despedida
        System.out.println("Agente SLR " + getAID().getName() + " finalizando.");
    }

    /**
     * Esto es invocado por la interfaz.
     */
    public void updateXValue(final Double xValue) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                x = xValue;
                System.out.println("Valor de X recibido." + x);
            }
        });
        addBehaviour(new SLR(x));
    }

    private class SLR extends OneShotBehaviour {
        // Contantes para predición.
        private double a;
        private double b;
        // Variables para determinar las constantes.
        private double xAvg;
        private double yAvg;

        

        public SLR(double _x) {
            a = 0;
            b = 0;
            xAvg = 0;
            yAvg = 0;
            x = _x;
            System.out.println("Algoritmo inicializado");
        }

        // Función para entrenar el metodo. Enocntrar las constantes
        public void train(double[][] data) {
            System.out.println("Calculando---");

            xAvg = 0;
            yAvg = 0;
            for (double[] register : data) {
                xAvg += register[0];
                yAvg += register[1];
            }
            xAvg = xAvg / data.length;
            yAvg = yAvg / data.length;

            // Calculo de a y b
            double aux1 = 0, aux2 = 0;
            for (double[] register : data) {
                aux1 += ((register[0] - xAvg) * (register[1] - yAvg));
                aux2 += (register[0] - xAvg) * (register[0] - xAvg);
            }
            // Calculo de a 
            a = aux1 / aux2;
            System.out.println("Valor encontrado para a es: " + a);

            // Calculo de b
            b = yAvg - a * xAvg;
            System.out.println("Valor encontrado para b es: " + b);

            System.out.println(String.format("y_hat = %.2f + %.2fx", b, a));
        }

        // Para predecir la X
        public double predict(double z) {
            return b + a * z;
        }

        // Con nuestra X
        public double predict() {
            return b + a * x;
        }

        // Predicción con una X dada.
        public double[] predict(double[] X) {
            double[] y = new double[X.length];
            int i = 0;
            for (double z : X) {
                y[i] = predict(z);
                i++;
            }
            return y;
        }

        public void action() {
            System.out.println("Predictor SLR_Resultado: ");
            double[][] dataSet = { 
            {1, 2},
            {2, 4},
            {3, 6},
            {4, 8},
            {5, 10},
            {6, 12},
            {7, 14},
            {8, 16},
            {9, 18}
        };
            train(dataSet);

            // Preccion
            double y_hat = predict(x);
            System.out.println("Usando el valor de X " + x + " el valor de Y es: " + y_hat);
        }

        public int onEnd() {
            System.out.println("Behaviour has been finished...");
            myAgent.doDelete();
            return super.onEnd();
        }
    }// End of inner class

}
