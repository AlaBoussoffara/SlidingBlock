package gloo.projet.metier;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("30ce8baa-f718-4149-95ba-17eadd36537a")
public abstract class AbstractCase {
    @objid ("86f5ae5c-f50d-4bdd-acb0-7bad181e6b65")
    private final Plateau plateau;

    @objid ("9059a2dc-6246-4957-a259-61cf715f1d1d")
    private final Position position;

    @objid ("7007a3fd-d16f-4696-adc9-31bee59fc1b9")
    private BlocElementaire blocElementaire;

    protected AbstractCase(final Plateau plateau, final Position position) {
        if (plateau == null || position == null) {
            throw new IllegalArgumentException("plateau and position must be non-null");
        }
        this.plateau = plateau;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    protected Plateau getPlateau() {
        return plateau;
    }

    BlocElementaire getBlocElementaire() {
        return blocElementaire;
    }

    void setBlocElementaire(final BlocElementaire blocElementaire) {
        this.blocElementaire = blocElementaire;
    }

    public boolean accepte(final BlocElementaire element) {
        if (element == null) {
            return false;
        }
        if (blocElementaire == null) {
            return true;
        }
        return blocElementaire.getBloc() == element.getBloc();
    }

}
