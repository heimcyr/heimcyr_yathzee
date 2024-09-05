import java.util.Random;

public class De {

    // Méthode pour lancer le dé
    public static int lancer(int nbreFace) {
        Random random = new Random();
        return random.nextInt(nbreFace) + 1;
    }
}
