package gloo.projet.metier;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("5c88ea33-c46b-44c3-8b95-9fd83df66de1")
public class Sortie extends AbstractCase {
    public Sortie(final Plateau plateau, final Position position) {
        super(plateau, position);
    }

    @Override
    public boolean accepte(final BlocElementaire element) {
        if (element == null || element.getBloc() == null) {
            return false;
        }
        if (element.getBloc().getNumero() != 0) {
            return false;
        }
        return super.accepte(element);
    }
}
