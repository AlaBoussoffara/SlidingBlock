package gloo.projet.metier;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("4bab21d3-8f75-45f0-9ac6-4176f9323a18")
public class Bloc {
    @objid ("4824592d-0c37-470b-a157-8a7a77c9425c")
    private int numero;

    @objid ("22d7e096-82e6-41a4-903d-bd19010c3f24")
    private List<BlocElementaire> elements = new ArrayList<BoxElementaire> ();

    @objid ("0adb2614-44ef-4f1c-8eaf-72999587c24d")
    private Plateau plateau;

}
