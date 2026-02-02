package gloo.projet.controle;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import gloo.projet.Direction;
import gloo.projet.metier.Bloc;
import gloo.projet.metier.Plateau;

@objid ("f0679868-8169-4a7a-99ef-56f84e7b2610")
public class Controleur {
    @objid ("eb0267a3-5d31-44ee-bbf2-89c2de4ac41f")
    private Plateau plateau;

    @objid ("c2dcba57-7fc9-4f10-a68b-a854c4ba0ce1")
    public Bloc blocSelectionne;

    @objid ("dda4ed0d-96e9-4a55-b8f2-5c16b3582465")
    public void selection(final int ligne, final int colonne) {
    }

    @objid ("5f17de43-a771-4ff4-9eef-3233e0b78eef")
    public void action(final Direction direction) {
    }

}
