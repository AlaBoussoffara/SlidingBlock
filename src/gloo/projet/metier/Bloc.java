package gloo.projet.metier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("4bab21d3-8f75-45f0-9ac6-4176f9323a18")
public class Bloc {
    @objid ("4824592d-0c37-470b-a157-8a7a77c9425c")
    private int numero;

    @objid ("0adb2614-44ef-4f1c-8eaf-72999587c24d")
    private Plateau plateau;

    @objid ("94d667dd-12a1-4647-9198-5d7609e54b01")
    private List<BlocElementaire> elements = new ArrayList<>();

    public Bloc(final int numero, final Plateau plateau) {
        if (plateau == null) {
            throw new IllegalArgumentException("plateau must be non-null");
        }
        this.numero = numero;
        this.plateau = plateau;
        this.plateau.ajouterBloc(this);
    }

    public int getNumero() {
        return numero;
    }

    public List<BlocElementaire> getElements() {
        return Collections.unmodifiableList(elements);
    }

    void ajouterElement(final BlocElementaire element) {
        if (element == null) {
            return;
        }
        if (!elements.contains(element)) {
            elements.add(element);
        }
    }

    public boolean deplacer(final Direction direction) {
        if (direction == null) {
            return false;
        }
        Map<BlocElementaire, AbstractCase> destinations = new LinkedHashMap<>();
        for (BlocElementaire element : elements) {
            AbstractCase actuelle = element.getCase();
            if (actuelle == null) {
                return false;
            }
            Position position = actuelle.getPosition();
            Position nouvelle = deplacePosition(position, direction);
            AbstractCase destination = plateau.getCase(nouvelle);
            if (destination == null || !destination.accepte(element)) {
                return false;
            }
            destinations.put(element, destination);
        }

        for (BlocElementaire element : elements) {
            AbstractCase actuelle = element.getCase();
            if (actuelle != null) {
                actuelle.setBlocElementaire(null);
            }
        }
        for (Map.Entry<BlocElementaire, AbstractCase> entry : destinations.entrySet()) {
            BlocElementaire element = entry.getKey();
            AbstractCase destination = entry.getValue();
            destination.setBlocElementaire(element);
            element.setCase(destination);
        }
        return true;
    }

    private Position deplacePosition(final Position position, final Direction direction) {
        int ligne = position.getLigne();
        int colonne = position.getColonne();
        switch (direction) {
        case HAUT:
            ligne -= 1;
            break;
        case BAS:
            ligne += 1;
            break;
        case GAUCHE:
            colonne -= 1;
            break;
        case DROITE:
            colonne += 1;
            break;
        default:
            break;
        }
        return new Position(ligne, colonne);
    }

}
