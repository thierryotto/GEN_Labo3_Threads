import java.util.ArrayList;
import java.util.List;

public class Banque {

    private List<Compte> comptes;
    private final int creditInitial = 5;

    public Banque(int nbComptes) {
        comptes = new ArrayList<Compte>();
        for (int i = 0; i < nbComptes; i++) {
            comptes.add(new Compte(i, creditInitial));
        }
    }

    public int getNbComptes() {
        return comptes.size();
    }

    public boolean consistent() {
        int total = 0;
        for (int i = 0; i < getNbComptes(); i++) {
            total += comptes.get(i).getMontant();
        }
        return total == creditInitial * getNbComptes();
    }

    public void transfert(int debiteur, int crediteur, int montant) {

        // Récupérer les comptes en tant qu'objet pour pouvoir appliquer
        // la fonction de syncronisation des threads dessus
        Compte compteDebiteur = comptes.get(debiteur);
        Compte compteCrediteur = comptes.get(crediteur);

        // L'accès au compte à débiter doit se faire par un seul thread à la fois
        synchronized (compteDebiteur) {
            if (compteDebiteur.debit(montant)) {

                // Pour ralentir l'exécution des threads
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Idem : l'accès au compte à créditer doit se faire par un seul thread à la fois
                synchronized (compteCrediteur) {
                    compteCrediteur.credit(montant);
                }
            }
        }
    }

}
