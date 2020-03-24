import java.util.Random;

/**
 * Classe exécutant N transferts entre comptes dans son propre thread.
 * Les comptes et les montants de chaque transfert sont tirés aléatoirement.
 */
public class Transferts implements Runnable {
    private int nbComptes = 0;
    private int nbTransferts = 0;
    private Banque banque;

    /**
     * Constructeur
     */
    public Transferts(int nbComptes, int nbTransferts) {
        this.nbComptes = nbComptes;
        this.banque = new Banque(nbComptes);
        this.nbTransferts = nbTransferts;
    }

    /**
     * Retourne la valeur du
     *
     * @return vrai si les comptes sont consistant, faux sinon.
     */
    public boolean getCheckConsistent() {
        return banque.consistent();
    }

    /**
     * Méthode qui est exécuté au lancement du thread
     */
    public void run() {
        final int SOMMEMAX = 100;

        // Faire un certain nombre de transfert
        // Les comptes et les montants de chaque transfert sont tirés aléatoirement.
        for (int i = 0; i < nbTransferts; i++) {
            banque.transfert(Util.random.nextInt(this.nbComptes),
                             Util.random.nextInt(this.nbComptes),
                             Util.random.nextInt(SOMMEMAX));
        }
    }

    /**
     * Méthode main pour un test
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        final int NBCOMPTES = 10;
        final int NBTRANSFERTS = 100;

        Transferts t1 = new Transferts(NBCOMPTES, NBTRANSFERTS);
        Thread thread1 = new Thread(t1);
        thread1.start();
        thread1.join();

        System.out.println(t1.getCheckConsistent());
    }
}

class Util {
    public static final Random random = new Random(0x42);
}
