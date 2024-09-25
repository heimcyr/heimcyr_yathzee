import java.util.Scanner;

public class Jeu {
    // Constante de l'objet Jeu
    private final int nbreDeLancers;
    private final int nbreRelance;
    private final int nbreManche;

    /**
     * Constructeur de jeu
     * @param nbreDeLancers le nombre de dés à lancers
     * @param nbreRelance le nombre de relances
     * @param nbreManche le nombre de manches
     */
    public Jeu (int nbreDeLancers, int nbreRelance, int nbreManche) {
        this.nbreDeLancers = nbreDeLancers;
        this.nbreRelance = nbreRelance;
        this.nbreManche = nbreManche;
    }

    /**
     * Getter pour le nombre de manche
     * @return le nombre de manche
     */
    public int getNbreManche() {
        return nbreManche;
    }

    /**
     * Getter pour le nombre de relance
     * @return le nombre de relance
     */
    public int getNbreRelance() {
        return nbreRelance;
    }

    /**
     * Getter pour le nombre de dés lancers
     * @return le nombre de dés lancers
     */
    public int getNbreDeLancers() {
        return nbreDeLancers;
    }

    // Le point d'entrée du programme
    public static void main(String[] args) {
        // Initialise l'objet Jeu
        Jeu jeu = new Jeu(5, 2, 5);
        // Appel de la méthode d'instance pour lancer le jeu
        jeu.jouer();
    }

    /**
     * Lance le jeu
     */
    public void jouer() {
        De de = new De(6, nbreDeLancers );
        Scoring scoring = new Scoring();
        int points;
        points = boucleManche(de, scoring);
        System.out.print("\nVoici votre score final : [" + points + "]\n");
    }

    /**
     * Lance les Manches en boucles et sauvegarde les points de chaque manche
     * @param de l'objet De
     * @param scoring l'objet Scoring
     * @return le nombre de points a la fin de toutes les manches.
     */
    public int boucleManche(De de, Scoring scoring) {
        int pointsFinal = 0;
        for (int i = 0; i < getNbreManche(); i++) {
            System.out.print("\nManche numéro : " + (i + 1) + "\n");
            pointsFinal += manche(de, scoring);
        }
        return pointsFinal;
    }

    /**
     * Effectue une manche complète de Yatzhee
     * @param de l'objet De
     * @param scoring l'objet Scoring
     * @return le nombre de points d'une manche
     */
    public int manche(De de, Scoring scoring) {
        Combinaison combinaison = new Combinaison("Initial", 0);  // Création d'une instance de Combinaison

        premierLancer(de.resultats, getNbreDeLancers(), de);
        boucleRelance(getNbreRelance(), getNbreDeLancers(), de.resultats, de);
        String combi = combinaison.obtenirNomCombinaisonFinal(de, scoring);
        return combinaison.verifieCombinaisonUtiliser(combi, scoring, this, de);
    }

    /**
     * Effectue le premier lancer de dé
     * @param resultats le tableau de dés
     * @param nbreDeLancers le nombre de dés à lancers
     * @param de l'objet De
     */
    public void premierLancer(int[] resultats, int nbreDeLancers, De de) {
        System.out.print("Jeux de Yahtzee ! Voici vos dés actuels : \n");
        for (int i = 0; i < nbreDeLancers; i++) {
            resultats[i] = de.lancer(de.getNbreFace());
            System.out.print("Dé " + (i + 1) + " : " + resultats[i] + "\n");
        }
    }

    /**
     * Demande a l'utlisateur si il veut relancer les dés
     * @param i Le nombre de relance disponibles
     * @return Si il relance les dés ou non
     */
    public int demandeRelanceDe(int i) {
        System.out.print("Voulez-vous relancer des dés ? (0 = non, 1 = oui). Relances restantes : [" + i + "]\n");
        Scanner lecteur = new Scanner(System.in);
        int choix = lecteur.nextInt();
        lecteur.nextLine();
        return choix;
    }

    /**
     * Demande à l'utilisateur les dés à relancer
     * @return les dés à relancer
     */
    public String[] demandeDeARelancer() {
        System.out.print("Quels dés voulez-vous relancer ? (entrez les numéros séparés par des espaces) : \n");
        Scanner lecteur = new Scanner(System.in);
        String desARelancer = lecteur.nextLine();
        return desARelancer.split(" ");
    }

    /**
     * Boucle pour la relance des dés
     * @param nbreRelance   Le nombre de relances disponibles
     * @param nbreDeLancers Le nombre de dés lancers
     * @param resultats     le tableau de résultats des dés
     * @param de            l'objet De
     */
    public void boucleRelance(int nbreRelance, int nbreDeLancers, int[] resultats, De de) {
        for (int i = nbreRelance; i > 0; i--) {
            if (demandeRelanceDe(i) != 0) {
                String[] indices = demandeDeARelancer();
                for (String index : indices) {
                    int deIndex = Integer.parseInt(index) - 1;
                    resultats[deIndex] = de.lancer(de.getNbreFace());
                }
                afficheResultRelance(nbreDeLancers, resultats);
            } else {
                i = 0;
            }
        }
    }

    /**
     * Affiche le résultat après chaque relance
     * @param nbreDeLancers Le nombre de dés lancers
     * @param resultats     Le tableau de resultats des dés
     */
    public void afficheResultRelance(int nbreDeLancers, int[] resultats) {
        System.out.println("Voici vos résultats après relance : ");
        for (int j = 0; j < nbreDeLancers; j++) {
            System.out.println("Dé " + (j + 1) + " : " + resultats[j]);
        }
    }
}
