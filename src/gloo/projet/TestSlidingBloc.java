package gloo.projet;

import gloo.projet.controle.Controleur;
import gloo.projet.metier.Bloc;
import gloo.projet.metier.BlocElementaire;
import gloo.projet.metier.Case;
import gloo.projet.metier.Direction;
import gloo.projet.metier.Mur;
import gloo.projet.metier.Plateau;
import gloo.projet.metier.Position;
import gloo.projet.metier.Sortie;

public class TestSlidingBloc {
    public static void main(String[] args) {
        System.out.println("Scenario TD");
        Plateau plateauScenario = construirePlateauTD();
        Controleur controleurScenario = new Controleur(plateauScenario);
        executerScenarioObligatoire(plateauScenario, controleurScenario);
        executerActionsSupplementaires(plateauScenario, controleurScenario);

        System.out.println("\nTests de robustesse");
        executerTestsRobustesse();
        System.out.println("\nFin des tests.");
    }

    private static void executerScenarioObligatoire(final Plateau plateau, final Controleur controleur) {
        System.out.println("Etat initial");
        System.out.println(plateau);

        controleur.selection(1, 1);
        controleur.action(Direction.GAUCHE);
        System.out.println("\nApres action GAUCHE");
        System.out.println(plateau);

        controleur.action(Direction.DROITE);
        System.out.println("\nApres action DROITE");
        System.out.println(plateau);
    }

    private static void executerActionsSupplementaires(final Plateau plateau, final Controleur controleur) {
        controleur.selection(1, 2);
        controleur.action(Direction.DROITE);
        System.out.println("\nTentative bloc 1 vers la sortie (refuse)");
        System.out.println(plateau);

        controleur.action(Direction.GAUCHE);
        System.out.println("\nRetour bloc 1 a gauche");
        System.out.println(plateau);

        controleur.selection(2, 2);
        controleur.action(Direction.HAUT);
        System.out.println("\nBloc 0 monte");
        System.out.println(plateau);

        controleur.action(Direction.DROITE);
        System.out.println("\nBloc 0 vers la sortie");
        System.out.println(plateau);
    }

    private static void executerTestsRobustesse() {
        int total = 0;
        int ok = 0;

        Plateau plateau;
        Controleur controleur;

        plateau = construirePlateauTD();
        controleur = new Controleur(plateau);
        controleur.selection(1, 1);
        ok += verifier(++total, "Bloc 1 vers mur (GAUCHE) refuse", !controleur.action(Direction.GAUCHE));

        plateau = construirePlateauTD();
        controleur = new Controleur(plateau);
        controleur.selection(1, 1);
        ok += verifier(++total, "Bloc 1 vers case libre (DROITE) accepte", controleur.action(Direction.DROITE));

        ok += verifier(++total, "Bloc 1 vers sortie (DROITE) refuse", !controleur.action(Direction.DROITE));

        plateau = construirePlateauTD();
        controleur = new Controleur(plateau);
        controleur.selection(1, 2);
        ok += verifier(++total, "Selection case vide => move refuse", !controleur.action(Direction.DROITE));

        plateau = construirePlateauTD();
        controleur = new Controleur(plateau);
        controleur.selection(2, 2);
        ok += verifier(++total, "Bloc 0 vers bas (mur) refuse", !controleur.action(Direction.BAS));

        plateau = construirePlateauTD();
        controleur = new Controleur(plateau);
        controleur.selection(1, 1);
        controleur.action(Direction.DROITE);
        controleur.selection(2, 2);
        ok += verifier(++total, "Bloc 0 monte sur bloc 1 (refuse)", !controleur.action(Direction.HAUT));

        plateau = construirePlateauTD();
        controleur = new Controleur(plateau);
        controleur.selection(2, 2);
        boolean up = controleur.action(Direction.HAUT);
        boolean right = controleur.action(Direction.DROITE);
        ok += verifier(++total, "Bloc 0 vers sortie => victoire", up && right && plateau.estVictoire());

        System.out.println("Resultat: " + ok + "/" + total + " OK");
    }

    private static int verifier(final int index, final String nom, final boolean condition) {
        String status = condition ? "OK" : "KO";
        System.out.println("[" + status + "] " + index + " - " + nom);
        return condition ? 1 : 0;
    }

    private static Plateau construirePlateauTD() {
        Plateau plateau = new Plateau(5, 4);

        for (int ligne = 0; ligne < 5; ligne++) {
            for (int colonne = 0; colonne < 4; colonne++) {
                Position position = new Position(ligne, colonne);
                if (ligne == 0 || ligne == 4 || colonne == 0 || colonne == 3) {
                    if ((ligne == 1 && colonne == 3) || (ligne == 2 && colonne == 3)) {
                        plateau.ajouterCase(new Sortie(plateau, position));
                    } else {
                        plateau.ajouterCase(new Mur(plateau, position));
                    }
                } else {
                    plateau.ajouterCase(new Case(plateau, position));
                }
            }
        }

        Bloc blocPrincipal = new Bloc(0, plateau);
        Bloc blocSecondaire = new Bloc(1, plateau);

        new BlocElementaire(blocSecondaire, plateau.getCase(1, 1));
        new BlocElementaire(blocPrincipal, plateau.getCase(2, 2));
        new BlocElementaire(blocPrincipal, plateau.getCase(3, 2));

        return plateau;
    }
}
