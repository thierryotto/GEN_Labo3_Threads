import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

class TransfertsTest {

    @Test
    void declencher1000TransfertsBanque10Comptes() throws InterruptedException {
        final int NBCOMPTES = 10;
        final int NBTRANSFERTS = 1000;
        final int NBBOUCLES = 1000;

        Transferts t1 = new Transferts(NBCOMPTES, NBTRANSFERTS);
        LinkedList<Thread> threadsTransfert = new LinkedList<>();

        // Exécuter N fois la classe de transfert
        for (int i = 0; i < NBBOUCLES; i++) {
            threadsTransfert.add(new Thread(t1));
            threadsTransfert.get(i).start();
        }

        // Attendre sur la fin de l'exécution de tous les threads
        for (int i = 0; i < NBBOUCLES; i++) {
            threadsTransfert.get(i).join();
        }

        // Vérifier que les comptes de la banques sont consistents
        assertTrue(t1.getCheckConsistent());
    }
}
