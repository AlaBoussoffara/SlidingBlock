package gloo.projet.metier;

import java.util.Objects;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("a2d65ed3-5222-442d-b45c-8feb04aab2d6")
public class Position {
    @objid ("e49f6db4-8de7-438d-b1c9-4646b61d4c2b")
    private int ligne;

    @objid ("121ae27a-ebc6-4527-bdc7-91e2831c3f79")
    private int colonne;

    public Position(final int ligne, final int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    @objid ("fcf5eeb5-da56-43c9-a289-4832792b4cf9")
    @Override
    public int hashCode() {
        return Objects.hash(colonne, ligne);
    }

    @objid ("894246eb-7efe-4d4a-bc11-d8dbf122962d")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        return colonne == other.colonne && ligne == other.ligne;
    }

}
