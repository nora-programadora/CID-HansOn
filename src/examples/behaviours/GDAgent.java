package examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class OneShotAgent extends Agent {

  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");
    addBehaviour(new MyOneShotBehaviour());
  } 

  private class MyOneShotBehaviour extends OneShotBehaviour {

    public void action() {
        System.out.println("Si entro");

        float[] numeros = new float[10];                                                                             
        Random rnd = new Random();
        for (int i = 0; i < numeros.length; i++) {
             numeros[i] = rnd.nextInt(0 - 1);                                                 
        }
        return numeros;
        
    } 
    
    public int onEnd() {
      myAgent.doDelete();   
      return super.onEnd();
    } 
  }    // END of inner class ...Behaviour
}
