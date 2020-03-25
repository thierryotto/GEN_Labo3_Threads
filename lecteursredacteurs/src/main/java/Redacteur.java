public class Redacteur  implements  Runnable{

    private Controleur controleur;
    private boolean enAttente = false;

    public Redacteur(Controleur c) {
        controleur = c;
    }

    public void run() {
        // si le controleur n'a pas commencé, le redacteur est en attente
        enAttente = true;
        {
            controleur.startWrite();
        }
        // si il est en train d'écrire il n'est plus en attente
        enAttente = false;
    }

    public synchronized void startWrite()   {
        new Thread(this).start();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {}
    }

    public synchronized void stopWrite()   {
        controleur.stopWrite();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {}
    }

    public  boolean isWaiting() {
        return enAttente;
    }

}
