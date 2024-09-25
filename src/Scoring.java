public class Scoring {
    // Création d'une table d'objet combinaison
    public Combinaison[] tableCombinaisons;

    // Constructeur
    public Scoring() {
        this.tableCombinaisons = new Combinaison[]{
                new Combinaison("Yams", 50),
                new Combinaison("Grande Suite", 40),
                new Combinaison("Carre", 0),
                new Combinaison("Full House", 25),
                new Combinaison("Petite Suite", 30),
                new Combinaison("Brelan", 0),
                new Combinaison("Chance", 0)
        };
    }

    /**
     * Calcule la somme totale des dés
     *
     * @param nbreDeLancers Nombre de dés lancers
     * @param resultats     Le tableau de résultats des dés
     * @return La somme totale des dés
     */
    public int calculeSommeTotal(int nbreDeLancers, int[] resultats) {
        int sommeTotale = 0;
        for (int j = 0; j < nbreDeLancers; j++) {
            sommeTotale += resultats[j];
        }
        return sommeTotale;
    }
}

