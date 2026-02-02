package gloo.projet.controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gloo.projet.metier.AbstractCase;
import gloo.projet.metier.Bloc;
import gloo.projet.metier.ContenuPlateau;
import gloo.projet.metier.Direction;
import gloo.projet.metier.Mur;
import gloo.projet.metier.Plateau;
import gloo.projet.metier.Sortie;

public class ControleurIHM implements IControleur {
    private final Plateau plateau;
    private final Controleur controleur;
    private int blocSelectionne = -1;

    public ControleurIHM(final Plateau plateau) {
        this.plateau = plateau;
        this.controleur = new Controleur(plateau);
    }

    @Override
    public void selection(int ligne, int colonne) {
        Bloc bloc = plateau.getBloc(ligne, colonne);
        blocSelectionne = bloc == null ? -1 : bloc.getNumero();
        controleur.selection(ligne, colonne);
    }

    @Override
    public void action(Direction direction) {
        controleur.action(direction);
    }

    @Override
    public int getNumeroBlocSelectionne() {
        return blocSelectionne;
    }

    @Override
    public boolean jeuTermine() {
        return plateau.estVictoire();
    }

    @Override
    public int getNbLignes() {
        return plateau.getNbLignes();
    }

    @Override
    public int getNbColonnes() {
        return plateau.getNbColonnes();
    }

    @Override
    public ContenuPlateau getContenu(int ligne, int colonne) {
        AbstractCase kase = plateau.getCase(ligne, colonne);
        if (kase instanceof Mur) {
            return ContenuPlateau.MUR;
        }
        if (kase instanceof Sortie) {
            return ContenuPlateau.SORTIE;
        }
        return ContenuPlateau.CASE;
    }

    @Override
    public int getNbBlocs() {
        return collectPositions().size();
    }

    @Override
    public int[][] getPositionsBloc(int numero) {
        Map<Integer, List<int[]>> positions = collectPositions();
        List<int[]> coords = positions.get(numero);
        if (coords == null) {
            return new int[0][0];
        }
        return coords.toArray(new int[coords.size()][]);
    }

    private Map<Integer, List<int[]>> collectPositions() {
        Map<Integer, List<int[]>> positions = new HashMap<>();
        for (int l = 0; l < plateau.getNbLignes(); l++) {
            for (int c = 0; c < plateau.getNbColonnes(); c++) {
                Bloc bloc = plateau.getBloc(l, c);
                if (bloc == null) {
                    continue;
                }
                positions.computeIfAbsent(bloc.getNumero(), key -> new ArrayList<>())
                    .add(new int[] { l, c });
            }
        }
        return positions;
    }
}
