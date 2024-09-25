public class Combinaison {
    // Constantes pour l'objet Combinaison
    private final String nom;
    private boolean estUtilisee;  // Indique si cette combinaison a déjà été utilisée
    private int points;

    /**
     * Constructeur de l'objet Combinaison
     * @param nom le nom de la combinaison
     * @param points Les points associés à cette combinaison
     */
    public Combinaison(String nom, int points) {
        this.nom = nom;
        this.points = points;
        this.estUtilisee = false;  // Par défaut, une combinaison n'a pas été utilisée
    }

    /**
     * Getter pour le nom de la combinaison
     * @return le nom de la combinaison
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter pour le nombre de points de la combinaison
     * @return le nombre de points de la combinaison
     */
    public int getPoints() {
        return points;
    }

    /**
     * Methode pour savoir si la combinaison est utilisé ou non
     * @return le booléean de estUtilisee, Oui ou non.
     */
    public boolean estUtilisee() {
        return estUtilisee;
    }

    /**
     * Setter pour le nombre de points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Method pour dire que mettre que la combinaison est utilisé
     */

    public void utiliser() {
        estUtilisee = true;
    }

    /**
     * Obtiens le nom de la combinaison finale
     * @param de l'objet De
     * @param scoring l'objet Scoring
     * @return la combinaison
     */
    public String obtenirNomCombinaisonFinal(De de, Scoring scoring) {
        // Identifier la combinaison obtenue Après identification et ajustement de la combinaison
        String combinaison = ajusterCombinaison(de.compterOccurrences(de.resultats), scoring.tableCombinaisons);
        // Annonce la combinaison obtenue
        System.out.print("Voici la combinaison obtenue : " + combinaison + "\n");
        return combinaison;
    }

    /**
     * Obtiens le nombre de points de la combinaison
     * @param jeu l'objet Jeu
     * @param scoring l'objet Scoring
     * @param de l'objet De
     * @param combinaison la combinaison
     * @return le nombre de points
     */
    public int obtenirNombreDePoint(Jeu jeu, Scoring scoring, De de, String combinaison) {
        // Calcule le nombre de points obtenu grâce à cette combinaison
        int sommeTotal = scoring.calculeSommeTotal(jeu.getNbreDeLancers(), de.resultats);
        scoring.tableCombinaisons[2].setPoints(sommeTotal);
        scoring.tableCombinaisons[5].setPoints(sommeTotal);
        scoring.tableCombinaisons[6].setPoints(sommeTotal);
        // Calcule le nombre de points obtenue grâce à cette combinaison
        return obtenirCombinaison(combinaison, scoring.tableCombinaisons).getPoints();
    }

    /**
     * Verifie si la combinaison est utilisé ou non et affiche si, non le nombre de points
     * @param combinaison la combinaison
     * @param scoring l'objet Scoring
     * @param jeu l'objet Jeu
     * @param de l'objet De
     * @return 0 si la combinaison est déja utilisé. sinon retourne le nombre de points obtenu
     */
    public int verifieCombinaisonUtiliser(String combinaison, Scoring scoring, Jeu jeu, De de) {
        // Vérifie si la combinaison est déja utilisée
        if (obtenirCombinaison(combinaison, scoring.tableCombinaisons).estUtilisee()) {
            // Annonce si la combinaison a été utilisée
            System.out.println("Cette combinaison a déjà été utilisée !");
        } else {
            // La marque comme utilisé
            obtenirCombinaison(combinaison, scoring.tableCombinaisons).utiliser();
            // Annonce le nombre points obtenue grace a cet combinaisons
            System.out.println("Vous avez gagnez " + obtenirNombreDePoint(jeu, scoring, de, combinaison));
            // Assigne le nombre de points
            return obtenirNombreDePoint(jeu, scoring, de, combinaison);
        }
        return 0;
    }

    /**
     * Ajuste les combinaisons de la plus puissante a la moins forte si déja utilisé
     * @param occurrences le tableau d'occurrences
     * @param tableCombinaisons le tableau de combinaison
     * @return la combinaison une fois ajusté
     */
    public String ajusterCombinaison(int[] occurrences, Combinaison[] tableCombinaisons) {

        // Récupère les combinaisons actuelles.
        Combinaison combinaisonAAjuster = identifierCombinaisons(occurrences, tableCombinaisons);

        // Récupère le nom en String de la combinaison A verifier
        String combinaisonAVerifier = combinaisonAAjuster.getNom();

        // Verifies si la combinaison a déja été utilisé et le remplace si oui.
        boolean correct = false;
        do {
            switch (combinaisonAVerifier) {
                case "Yams":
                    if (tableCombinaisons[0].estUtilisee()) {
                        System.out.println("Vous avez déjà obtenu un Yams. Cette combinaison est ajustée en Carré.");
                        combinaisonAVerifier = "Carre";
                    } else {
                        correct = true;
                    }
                    break;
                case "Carre":
                    if (tableCombinaisons[2].estUtilisee()) {
                        System.out.println("Vous avez déjà obtenu un Carré. Cette combinaison est ajustée en Brelan.");
                        combinaisonAVerifier = "Brelan";
                    } else {
                        correct = true;
                    }
                    break;
                case "Grande Suite":
                    if (tableCombinaisons[1].estUtilisee()) {
                        System.out.println("Vous avez déjà obtenu une Grande Suite." +
                                "Cette combinaison est ajustée en Petite Suite.");
                        combinaisonAVerifier = "Petite Suite";
                    } else {
                        correct = true;
                    }
                    break;
                case "Full House":
                    if (tableCombinaisons[3].estUtilisee()) {
                        System.out.println("Vous avez déjà obtenu une Full House. Cette combinaison est ajustée en Brelan.");
                        combinaisonAVerifier = "Brelan";
                    } else {
                        correct = true;
                    }
                    break;
                case "Brelan":
                    if (tableCombinaisons[5].estUtilisee()) {
                        System.out.println("Vous avez déjà obtenu un Brelan. Cette combinaison est ajustée en Chance.");
                        combinaisonAVerifier = "Chance";
                    } else {
                        correct = true;
                    }
                    break;
                case "Petite Suite":
                    if (tableCombinaisons[4].estUtilisee()) {
                        System.out.println("Vous avez déjà obtenu une petite suite. Cette combinaison est ajustée en Chance.");
                        combinaisonAVerifier = "Chance";
                    } else {
                        correct = true;
                    }
                    break;
                case "Chance":
                    // Cas final : "Chance" ne peut pas être ajustée plus loin.
                    correct = true;
                    break;
            }
        } while (!correct);
        return combinaisonAVerifier;
    }

    /**
     * Identifie les combinaisons a l'aide des occurrences
     * @param occurrences le tableau d'occurrences
     * @param tableCombinaisons le tableau de combinaison
     * @return l'index de la combinaison identifié
     */
    public Combinaison identifierCombinaisons(int[] occurrences, Combinaison[] tableCombinaisons
    ) {
        if (estYahtzee(occurrences)) return tableCombinaisons[0];
        else if (estCarre(occurrences)) return tableCombinaisons[2];
        else if (estFull(occurrences)) return tableCombinaisons[3];
        else if (estBrelan(occurrences)) return tableCombinaisons[5];
        else if (estGrandeSuite(occurrences)) return tableCombinaisons[1];
        else if (estPetiteSuite(occurrences)) return tableCombinaisons[4];
        else return tableCombinaisons[6];
    }

    /**
     * Obtiens la combinaison à l'aide de son nom
     * @param combinaison le tableau d'occurrences
     * @param tableCombinaisons le tableau de combinaison
     * @return l'index de la combinaison identifié
     */
    public Combinaison obtenirCombinaison(String combinaison, Combinaison[] tableCombinaisons) {
        return switch (combinaison) {
            case "Yams" -> tableCombinaisons[0];
            case "Carre" -> tableCombinaisons[2];
            case "Full House" -> tableCombinaisons[3];
            case "Brelan" -> tableCombinaisons[5];
            case "Petite Suite" -> tableCombinaisons[4];
            case "Grande Suite" -> tableCombinaisons[1];
            case "Chance" -> tableCombinaisons[6];
            default -> throw new IllegalStateException("Unexpected value: " + combinaison);
        };
    }

    /**
     * Verifies si la combinaison Yams est possible ou non.
     * @param occurrences le nombre d'occurrences.
     * @return true, si possible, false si impossible.
     */
    public boolean estYahtzee(int[] occurrences) {
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
    public boolean estCarre(int[] occurrences) {
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
    public boolean estFull(int[] occurrences) {
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
    public boolean estBrelan(int[] occurrences) {
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
    public boolean estPetiteSuite(int[] occurrences) {
        return (occurrences[0] > 0 && occurrences[1] > 0 && occurrences[2] > 0 && occurrences[3] > 0) ||  // 1-2-3-4
                (occurrences[1] > 0 && occurrences[2] > 0 && occurrences[3] > 0 && occurrences[4] > 0) ||  // 2-3-4-5
                (occurrences[2] > 0 && occurrences[3] > 0 && occurrences[4] > 0 && occurrences[5] > 0);    // 3-4-5-6
    }

    /**
     * Verifies si la combinaison Grande suite est possible ou non.
     * @param occurrences le nombre d'occurrences.
     * @return true, si possible, false si impossible.
     */
    public boolean estGrandeSuite(int[] occurrences) {
        // Grande suite : 5 dés consécutifs (ex : 1-2-3-4-5, 2-3-4-5-6)
        return (occurrences[0] > 0 && occurrences[1] > 0 && occurrences[2] > 0 && occurrences[3] > 0 && occurrences[4] > 0) ||  // 1-2-3-4-5
                (occurrences[1] > 0 && occurrences[2] > 0 && occurrences[3] > 0 && occurrences[4] > 0 && occurrences[5] > 0);   // 2-3-4-5-6
    }
}
