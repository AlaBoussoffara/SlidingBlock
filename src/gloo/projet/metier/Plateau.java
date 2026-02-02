package gloo.projet.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("a9ba4006-bb06-44b6-b94e-cd87bbfebcdf")
public class Plateau {
    @objid ("c440c761-fa74-403e-8bee-d6cad039032c")
    private int nbLignes;

    @objid ("5891be88-9ecd-4504-8e0a-e722797a8ffa")
    private int nbColonnes;

    @objid ("903a64cd-b3d7-46f1-8623-729277f33472")
    private Map<Position, AbstractCase> kases = new HashMap<>();

    @objid ("d527367d-0889-428b-b26c-870153db29b6")
    private Map<Integer, Bloc> blocs = new HashMap<>();

}
