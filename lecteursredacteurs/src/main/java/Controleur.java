public class Controleur {

    private int redacteurs = 0;
    private int redacteursAttente = 0;
    private int lecteurs = 0; // Nombre de lecteurs en train de lire

    public synchronized void startRead() {

        // Si il y a un rédacteur en train d'écrire -> thread en attente
        while (redacteurs > 0 || redacteursAttente > 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }
        lecteurs++;// Un lecteur supplémentaire
    }

    public synchronized void stopRead() {
        /*
        un lecteur actif en moins, si il n'y en
        a plus, on libère tout les threads en wait avec notifyAll
         */
        lecteurs--;
        if (lecteurs == 0) this.notifyAll();

    }

    public synchronized void startWrite() {
        redacteursAttente++;
        /* Si il y a encore un lecteur en train de lire
         ou un autre redacteur en train d'écrire
         le thread est en attente.
         */
        while ((lecteurs > 0 || redacteurs > 0)) {
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }
        redacteurs++;
        redacteursAttente--;
    }

    public synchronized void stopWrite() {
        /*
        un lecteur actif en moins, si il n'y en
        a plus, on libère tout les threads en wait avec notifyAll
         */
        redacteurs--;
        if (lecteurs == 0) this.notifyAll();
    }
}


