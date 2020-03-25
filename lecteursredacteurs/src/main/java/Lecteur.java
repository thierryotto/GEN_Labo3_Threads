/*
porblème avec l'utilisation de thread sleep car les assert ne marche pas à tout les coup.
 */

public class Lecteur implements Runnable {

    private Controleur controleur;
    private boolean enAttente = false;

    public Lecteur(Controleur c) {
        controleur = c;
    }

    public void run(){
        // si le controleur n'a pas commencé, le lecteur est en attente
         enAttente = true;
        {
            controleur.startRead();
        }
        // si il est en train de lire il n'est plus en attente
         enAttente = false;
    }

    public synchronized void startRead()   {
        new Thread(this).start();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {}
    }

    public synchronized void stopRead()   {
        controleur.stopRead();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {}
    }

    public boolean isWaiting() {
        return enAttente;
    }


}