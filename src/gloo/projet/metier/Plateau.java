package gloo.projet.metier;

import java.util.HashMap;
import java.util.Map;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("a9ba4006-bb06-44b6-b94e-cd87bbfebcdf")
public class Plateau {
    @objid ("c440c761-fa74-403e-8bee-d6cad039032c")
    private int nbLignes;

    @objid ("5891be88-9ecd-4504-8e0a-e722797a8ffa")
    private int nbColonnes;

    @objid ("903a64cd-b3d7-46f1-8623-729277f33472")
    private Map<Position, AbstractCase> cases = new HashMap<>();

    @objid ("d527367d-0889-428b-b26c-870153db29b6")
    private Map<Integer, Bloc> blocs = new HashMap<>();

    public Plateau(final int nbLignes, final int nbColonnes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
    }

    public int getNbLignes() {
        return nbLignes;
    }

    public int getNbColonnes() {
        return nbColonnes;
    }

    public void ajouterCase(final AbstractCase kase) {
        if (kase == null) {
            return;
        }
        cases.put(kase.getPosition(), kase);
    }

    public void ajouterBloc(final Bloc bloc) {
        if (bloc == null) {
            return;
        }
        blocs.put(bloc.getNumero(), bloc);
    }

    public AbstractCase getCase(final Position position) {
        return cases.get(position);
    }

    public AbstractCase getCase(final int ligne, final int colonne) {
        return cases.get(new Position(ligne, colonne));
    }

    @objid ("a266a278-7e3e-4f38-a6f8-8f8ac6f30b01")
    public Bloc getBloc(final int ligne, final int colonne) {
        AbstractCase kase = getCase(ligne, colonne);
        if (kase == null) {
            return null;
        }
        BlocElementaire element = kase.getBlocElementaire();
        return element == null ? null : element.getBloc();
    }

    public Integer getNumeroBloc(final int ligne, final int colonne) {
        Bloc bloc = getBloc(ligne, colonne);
        return bloc == null ? null : bloc.getNumero();
    }

    public boolean estVictoire() {
        int sortiesOccupees = 0;
        int blocsZero = 0;
        for (AbstractCase kase : cases.values()) {
            if (kase.getBlocElementaire() != null) {
                BlocElementaire element = kase.getBlocElementaire();
                if (element.getBloc() != null && element.getBloc().getNumero() == 0) {
                    blocsZero++;
                    if (kase instanceof Sortie) {
                        sortiesOccupees++;
                    }
                }
            }
        }
        return blocsZero > 0 && blocsZero == sortiesOccupees;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int ligne = 0; ligne < nbLignes; ligne++) {
            for (int colonne = 0; colonne < nbColonnes; colonne++) {
                AbstractCase kase = getCase(ligne, colonne);
                String cell;
                if (kase == null) {
                    cell = "?";
                } else if (kase.getBlocElementaire() != null) {
                    cell = Integer.toString(kase.getBlocElementaire().getBloc().getNumero());
                } else if (kase instanceof Mur) {
                    cell = "M";
                } else if (kase instanceof Sortie) {
                    cell = "S";
                } else {
                    cell = "C";
                }
                builder.append("| ").append(cell).append(' ');
            }
            builder.append("|");
            if (ligne < nbLignes - 1) {
                builder.append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

}
