import java.util.Scanner;


public class Main {

    // Paramètre de jeux
    static final int nbreDeLancers = 5;
    static final int nbreFace = 6;
    static final int nbreRelance = 2;
    static final int nbreManche = 5;

    // Suivi des combinaisons déjà obtenues !!! don't touch !!!
    static int yamscompteur = 0;
    static int grandeSuitecompteur = 0;
    static int carrecompteur = 0;

    // Point d'entrée du programme
    public static void main(String[] args) {

        // Initialise le tableau de combinaison utilisé
        int[] combinaisonsUtilises = new int[7];

        // Calcule le nombre de points durant les manches
        int pointsDeManche = 0;

        // Lance la fonction qui permet de faire les manches d'affilés
        boucleManche(pointsDeManche, combinaisonsUtilises);
    }

    /**
     * Effectue les manches de jeux les une à la suite des autres.
     * @param pointsDeManche Nombre de points aux totaux de manches
     */
    public static void boucleManche(int pointsDeManche, int[] combinaisonsUtilises) {

        // Boucle qui relance jusqu'à atteindre le nombre de manches voulu.
        for (int i = 0; i < nbreManche; i++) {

            // affiche le numéro de la manche
            System.out.print("\nManche numéro : " + ( i + 1 ) + "\n");

            // initialise le tableau int resultats
            int[] resultats = new int[nbreDeLancers];

            // Effectue le premier lancer de dé
            premierLancer(resultats, nbreDeLancers, nbreFace);

            // Lance la boucle de relance de dé
            boucleRelance(nbreRelance, nbreDeLancers, resultats, nbreFace);

            // Calculer les occurrences
            int[] occurrences = compterOccurrences(resultats);

            // Identifier et affiches la combinaison obtenue
            String combinaisons = ajusterCombinaison(occurrences);

            // Affiche la combinaison obtenue
            System.out.print("Voici la combinaison obtenue : " + combinaisons + "\n");

            // Obtenir l'index de la combinaison
            int indexCombinaison = obtenirIndexCombinaison(combinaisons);

            // regarde avec l'index de la combinaison si elle est déja utilisé
            if (combinaisonsUtilises[indexCombinaison] > 0) {

                // Affiche si la combinaison a déja été utilisé
                System.out.println("Cette combinaison a déjà été utilisée !");
            } else {

                // Marquer la combinaison comme utilisée
                combinaisonsUtilises[indexCombinaison]++;

                // Ajoute au compteur ces combinaisons pour l'ajusteur de combinaisons.
                if (indexCombinaison == 0) { // index du yams
                    yamscompteur++;
                }if (indexCombinaison == 1) { // index du carré
                    carrecompteur++;
                }if (indexCombinaison == 5) { // index de la grande suite
                    grandeSuitecompteur++;
                }

                // Calcule le nombre de points pour les manches
                pointsDeManche += obtenirPointsPourCombinaison(combinaisons, resultats, nbreDeLancers);

                // Calculer et afficher les points obtenues
                System.out.print("Voici votre nombre de points : " +
                        obtenirPointsPourCombinaison(combinaisons, resultats, nbreDeLancers) + "\n");
            }

        }

        // Affiche le nombre de points final
        System.out.print("\nVoici votre nombre de points final ! : [" + pointsDeManche + "]\n");
    }

    /**
     * Effectue le premier tirage de dé
     * @param resultats     tableau d'entier reflétant le jeu du joueur
     * @param nbreFace      nombre de face des dés
     * @param nbreDeLancers nombre de dés à lancer
     */
    public static void premierLancer(int[] resultats, int nbreDeLancers, int nbreFace) {
        System.out.print("Jeux de yatzhee ! Voici vos dés actuel : \n");
        for (int i = 0; i < nbreDeLancers; i++) {
            resultats[i] = De.lancer(nbreFace);
            System.out.print(" Dé " + (i + 1) + " : " + resultats[i] + "\n");
        }
    }

    /**
     * Demande à l'utilisateur s'il veut relancer ses dés ou non
     * @param i = nombre de relance
     * @return le choix de l'utilisateur
     */
    public static int demandeRelanceDe(int i) {
        System.out.print("Voulez-vous relancer des dés ? " +
                "(0 = non, 1 = oui) " + "Nombre de relance restant : [" + i + "]\n");

        // Récupérer le nombre de dés à relancer
        Scanner lecteur = new Scanner(System.in);
        int choix = lecteur.nextInt();

        // Consommer la ligne restante pour éviter les problèmes avec nextLine()
        lecteur.nextLine();

        // retourne le choix de l'utilisateur
        return choix;
    }

    /**
     * Demande à l'utilisateur quels dés il veut relancer
     * @return les dés qu'il veut relancer en liste string
     */
    public static String[] demandeDeARelancer() {
        String desARelancer;
        System.out.print("Quels dés voulez-vous relancer ?" +
                " (entrez les numéros séparés par des espaces) : \n");
        Scanner lecteur = new Scanner(System.in);
        desARelancer = lecteur.nextLine();

        // Sert pour la séparation des dés avec un espace
        return desARelancer.split(" ");
    }

    /**
     * Créer une boucle de relance de dée jusqu'à épuisement des relances, ou choix de l'utilisateur
     * @param nbreRelance le nombre de relances possible
     * @param nbreDeLancers nombre de dés lancers nécessaire pour la fonction verification
     * @param resultats tableau de valeur des dés nécessaire pour la fonction verification
     * @param nbreFace nombre de face d'un dée nécessaire pour la fonction verification
     */
    public static void boucleRelance(int nbreRelance, int nbreDeLancers, int[] resultats, int nbreFace) {
        for (int i = nbreRelance; i > 0; i--) {
            if (demandeRelanceDe(i) != 0) {
                // Demande quels dés relancer
                String[] indices = demandeDeARelancer();

                // Relance les dés en fonction des indices fournis
                for (String index : indices) {
                    int deIndex = Integer.parseInt(index) - 1; // Convertir en index 0-based
                    resultats[deIndex] = De.lancer(nbreFace);
                }

                // Affiche le résultat après chaque relance
                afficheResultRelance(nbreDeLancers, resultats);

            } else {
                i = 0; // Arrête la boucle si l'utilisateur choisit de ne pas relancer
            }
        }
    }

    /**
     * Affiche le résultat après chaque relance
     * @param nbreDeLancers Nombre de dés lancers
     * @param resultats tableau de valeur des dés
     */
    public static void afficheResultRelance(int nbreDeLancers, int[] resultats) {
        System.out.println("Voici vos résultats après relance : ");
        for (int j = 0; j < nbreDeLancers; j++) {
            System.out.println(" Dé " + (j + 1) + " : " + resultats[j]);
        }
    }

    /**
     * Calcule la somme totale des valeurs des dés
     * @param nbreDeLancers Nombre de dés lancers
     * @param resultats Valeurs des dés
     * @return la somme totale de tous les dés
     */
    public static int calculeSommeTotal(int nbreDeLancers, int[] resultats) {
        // Calcule la somme totale
        int sommeTotale = 0;
        for (int j = 0; j < nbreDeLancers; j++) {
            sommeTotale += resultats[j];
        }
        // Retourne la somme totale
        return sommeTotale;
    }

    /**
     * Compte les occurrences de chaque valeur de dé
     * @param resultats tableau des valeurs des dés
     * @return un tableau sur lequel chaque index représente une valeur de dé (0 pour 1, 1 pour 2, ..., 5 pour 6).
     */
    public static int[] compterOccurrences(int[] resultats) {
        int[] occurrences = new int[6]; // Tableau pour les valeurs de 1 à 6
        for (int index : resultats) {
            occurrences[index - 1]++;
        }
        return occurrences;
    }

    /**
     * Identifie la combinaison obtenue.
     * @param occurrences le nombre occurrences obtenue
     * @return le nom de la combinaison obtenue
     */
    public static String identifierCombinaisons(int[] occurrences) {
        if (estYahtzee(occurrences)) {
            return "Yams";
        } else if (estCarre(occurrences)) {
            return "Carre";
        } else if (estFull(occurrences)) {
            return "Full House";
        } else if (estBrelan(occurrences)) {
            return "Brelan";
        } else if (estGrandeSuite(occurrences)) {
            return "Grande Suite";
        } else if (estPetiteSuite(occurrences)) {
            return "Petite Suite";
        } else {
            return "Chance";
        }
    }

    /**
     * Obtiens le nombre de points pour chaque combinaison.
     * @param combinaison la combinaison réalisée par le joueur.
     * @param resultats le result des dés.
     * @param nbreDeLancers le nombre de dés lancés.
     * @return le nombre de points effectué.
     */
    public static int obtenirPointsPourCombinaison(String combinaison, int[] resultats, int nbreDeLancers) {
        return switch (combinaison) {
            case "Full House" -> 25; // Points pour un Full
            case "Petite Suite" -> 30; // Points pour une Petite Suite
            case "Grande Suite" -> 40; // Points pour une Grande Suite
            case "Yams" -> 50; // Points pour un Yatzhee
            default -> calculeSommeTotal(nbreDeLancers, resultats); // Points pour le Brelan, le carré, la Chance.
        };
    }

    /**
     * Verifies si la combinaison Yams est possible ou non.
     * @param occurrences le nombre d'occurrences.
     * @return true, si possible, false si impossible.
     */
    public static boolean estYahtzee(int[] occurrences) {
        for (int index : occurrences) {
            if (index == 5) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies si la combinaison Carré est possible ou non.
     * @param occurrences le nombre d'occurrences.
     * @return true, si possible, false si impossible.
     */
    public static boolean estCarre(int[] occurrences) {
        for (int index : occurrences) {
            if (index == 4) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies si la combinaison Full House est possible ou non.
     * @param occurrences le nombre d'occurrences.
     * @return true, si possible, false si impossible.
     */
    public static boolean estFull(int[] occurrences) {
        boolean trois = false;
        boolean deux = false;
        for (int occ : occurrences) {
            if (occ == 3) {
                trois = true;
            } else if (occ == 2) {
                deux = true;
            }
        }
        return trois && deux;
    }

    /**
     * Verifies si la combinaison Brelan est possible ou non.
     * @param occurrences le nombre d'occurrences.
     * @return true, si possible, false si impossible.
     */
    public static boolean estBrelan(int[] occurrences) {
        for (int index : occurrences) {
            if (index == 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies si la combinaison Petite suite est possible ou non.
     * @param occurrences le nombre d'occurrences.
     * @return true, si possible, false si impossible.
     */
    public static boolean estPetiteSuite(int[] occurrences) {
        return (occurrences[0] > 0 && occurrences[1] > 0 && occurrences[2] > 0 && occurrences[3] > 0) ||  // 1-2-3-4
                (occurrences[1] > 0 && occurrences[2] > 0 && occurrences[3] > 0 && occurrences[4] > 0) ||  // 2-3-4-5
                (occurrences[2] > 0 && occurrences[3] > 0 && occurrences[4] > 0 && occurrences[5] > 0);    // 3-4-5-6
    }

    /**
     * Verifies si la combinaison Grande suite est possible ou non.
     * @param occurrences le nombre d'occurrences.
     * @return true, si possible, false si impossible.
     */
    public static boolean estGrandeSuite(int[] occurrences) {
        // Grande suite : 5 dés consécutifs (ex: 1-2-3-4-5, 2-3-4-5-6)
        return (occurrences[0] > 0 && occurrences[1] > 0 && occurrences[2] > 0 && occurrences[3] > 0 && occurrences[4] > 0) ||  // 1-2-3-4-5
                (occurrences[1] > 0 && occurrences[2] > 0 && occurrences[3] > 0 && occurrences[4] > 0 && occurrences[5] > 0);   // 2-3-4-5-6
    }

    /**
     * Obtiens un index pour le nom d'une combinaison
     * @param combinaison le nom de la combinaison à trouver l'index
     * @return l'index relatif a cette combinaison
     */
    public static int obtenirIndexCombinaison(String combinaison) {
        return switch (combinaison) {
            case "Yams" -> 0;
            case "Carre" -> 1;
            case "Full House" -> 2;
            case "Brelan" -> 3;
            case "Petite Suite" -> 4;
            case "Grande Suite" -> 5;
            case "Chance" -> 6;
            default -> -1;
        };
    }

    /**
     * Ajuste les combinaisons si les combinaisons plus puissantes est déja utilisé
     * @param occurrences les occurrences des combinaisons
     * @return la combinaison ajustée si possible.
     */
    public static String ajusterCombinaison(int[] occurrences) {

        // Récupère les combinaisons actuelles.
        String combinaison = identifierCombinaisons(occurrences);

        // Verifies si des combinaisons a déja été utilisé et le remplace si oui.
        switch (combinaison) {
            case "Yams":
                if (yamscompteur > 0) {
                    System.out.println("Vous avez déjà obtenu un Yams. Cette combinaison est ajustée en Carré.");
                    combinaison = "Carre";
                }
                break;
            case "Grande Suite":
                if (grandeSuitecompteur > 0) {
                    System.out.println("Vous avez déjà obtenu une Grande Suite." +
                            " Cette combinaison est ajustée en Petite Suite.");
                    combinaison = "Petite Suite";
                }
                break;
            case "Carre":
                if (carrecompteur > 0) {
                    System.out.println("Vous avez déjà obtenu un Carré. Cette combinaison est ajustée en Brelan.");
                    combinaison = "Brelan";
                }
                break;
        }
        return combinaison;
    }
}
