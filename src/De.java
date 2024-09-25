import java.util.Random;

public class De {
    // Constante pour l'objet De
    public int nbreFace;
    public int[] resultats;

    /**
     * Constructeur pour l'objet De
     * @param nbreFace le nombre de face du dé
     * @param nbreLancers le nombre de dés à lancer
     */
    public De(int nbreFace, int nbreLancers) {
        this.nbreFace = nbreFace;
        this.resultats = new int[nbreLancers];
    }

    /**
     * Getter pour le nombre de face du dé
     * @return le nombre de face du dé
     */
    public int getNbreFace() {
        return nbreFace;
    }

    /**
     * Lance un dé aléatoirement
     * @param nbreFace le nombre de face du dé
     * @return la valeur de la face visible du dé
     */
    public int lancer(int nbreFace) {
        Random random = new Random();
        return random.nextInt(nbreFace) + 1;
    }

    /**
     * Calcule le nombre d'occurrences dans les valeurs des dés fournit
     * @param resultats Le tableau de résultat des dés
     * @return Un tableau d'occurrence
     */
    public int[] compterOccurrences(int[] resultats) {
        int[] occurrences = new int[6];
        for (int index : resultats) {
            occurrences[index - 1]++;
        }
        return occurrences;
    }
}