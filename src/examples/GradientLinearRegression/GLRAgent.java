/*
Gradient Lineal Regression
Nora Rebeca Aguirre Sánchez
213400059
*/

package examples.gradientLinearRegression;

import jade.core.Agent;
import java.util.ArrayList;
import java.util.List;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.*;

public class GLRAgent extends Agent {

  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");
    addBehaviour(new MyOneShotBehaviour());
  } 

  private class MyOneShotBehaviour extends OneShotBehaviour {

    public double predecirY(double x, double beta0, double beta1){
        double resultado = 0;
        resultado = beta0 + (beta1 * x);
        return resultado;
    }
      
    public void action() {
        System.out.println("Agent's action method executed");
        lastError = 0;

        List<DataSet> dataset = new ArrayList<DataSet>();
        dataset = inicializarPrograma(dataset);

        double beta_0 = 0, beta_1 = 0, alfa = 0.0005f;

        double[] betas = calcularBetas( dataset, beta_0, beta_1, alfa);
        double dato = predecirY( 65, betas[0], betas[1]);

        System.out.println("Beta_0: " + betas[0] + " , Beta_1: " + betas[1]);
        System.out.println("Solucion Final: " + dato );
    }
    
    public List<DataSet> inicializarPrograma(List<DataSet> dataset){
        DataSet datos = new DataSet();
        datos = new DataSet(1,2);
        dataset.add(datos);
        datos = new DataSet(2,4);
        dataset.add(datos);
        datos = new DataSet(3,6);
        dataset.add(datos);
        datos = new DataSet(4,8);
        dataset.add(datos);
        datos = new DataSet(5,10);
        dataset.add(datos);
        datos = new DataSet(6,12);
        dataset.add(datos);
        datos = new DataSet(7,14);
        dataset.add(datos);
        datos = new DataSet(8,16);
        dataset.add(datos);
        datos = new DataSet(9,18);
        dataset.add(datos);
        return dataset;
    }
    
    public double lastError;
    
    public double[] calcularBetas(List<DataSet> dataset, double beta_0, double beta_1, double alfa)
    {
        while( calcularSumatoriaPerdida(dataset, beta_0, beta_1) > 0.0001 ){
            //Calcular valor de beta_0
            beta_0 -= (alfa * sumatoriaBeta_0(dataset, beta_0, beta_1));
            
            //Calcular valor de beta_1
            beta_1 -= (alfa * sumatoriaBeta_1(dataset, beta_0, beta_1));
        }
        double[] betas = {beta_0, beta_1};
        return betas;
    }
    
    public double sumatoriaBeta_0(List<DataSet> dataset, double beta_0 , double beta_1)
    {
        double resultado = 0;
        for(DataSet dato : dataset)
        {
            resultado += (dato.getY() - ( beta_0 + beta_1 * dato.getX() ));
        }
        return ( -2.0 / dataset.size() ) * resultado ;
    }
    
    public double sumatoriaBeta_1(List<DataSet> dataset, double beta_0, double beta_1)
    {
        double resultado = 0;
        for(DataSet dato : dataset)
        {
            resultado += dato.getX() * (dato.getY() - ( beta_0 + beta_1 * dato.getX() )) ;
        }  
        return ( -2.0 /dataset.size()) * resultado ;
    }
    
    public double calcularSumatoriaPerdida(List<DataSet> dataset, double beta_0, double beta_1)
    {
        double resultado = 0;
        for(DataSet dato : dataset)
        {
            resultado += (dato.getY() - (beta_0 + beta_1 * dato.getX())) *  (dato.getY() - beta_0 + (beta_1 * dato.getX()));
        }
        resultado = ( 1.0 / dataset.size() ) * resultado;
        
        double aux = Math.abs(lastError - resultado);
        
        lastError = resultado; 
        
        System.out.println("Error: " + resultado );
        return aux;
    }
    
    public int onEnd() {
      myAgent.doDelete();
      return super.onEnd();
    } 
  }
  
    class DataSet{
        private double x;
        private double y;
        DataSet(double vX, double vY){
            x = vX;
            y = vY;
        }
        DataSet(){}
        void setX(double vX){
            x = vX;
        }
        double getX(){
            return x;
        }
        void setY(double vY){
            y = vY;
        }
        double getY(){
            return y;
        }
    }// END of inner class ...Behaviour
}
