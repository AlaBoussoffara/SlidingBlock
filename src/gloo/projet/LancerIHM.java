package gloo.projet;

import javax.swing.SwingUtilities;

import gloo.projet.controle.ControleurIHM;
import gloo.projet.ihm.FenetreBloc;
import gloo.projet.metier.Bloc;
import gloo.projet.metier.BlocElementaire;
import gloo.projet.metier.Case;
import gloo.projet.metier.Mur;
import gloo.projet.metier.Plateau;
import gloo.projet.metier.Position;
import gloo.projet.metier.Sortie;

public class LancerIHM implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new LancerIHM());
    }

    @Override
    public void run() {
        Plateau plateau = construirePlateauTD();
        new FenetreBloc(new ControleurIHM(plateau));
    }

    private Plateau construirePlateauTD() {
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
