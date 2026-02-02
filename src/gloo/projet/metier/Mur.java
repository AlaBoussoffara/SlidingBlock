package gloo.projet.metier;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("6ff04fe0-b433-4b90-94e2-96c26a4a883e")
public class Mur extends AbstractCase {
    public Mur(final Plateau plateau, final Position position) {
        super(plateau, position);
    }

    @Override
    public boolean accepte(final BlocElementaire element) {
        return false;
    }
}
