package gloo.projet.metier;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("4bab21d3-8f75-45f0-9ac6-4176f9323a18")
public class Bloc {
    @objid ("4824592d-0c37-470b-a157-8a7a77c9425c")
    private int numero;

    @objid ("0adb2614-44ef-4f1c-8eaf-72999587c24d")
    private Plateau plateau;

    @objid ("94d667dd-12a1-4647-9198-5d7609e54b01")
    private List<BlocElementaire> elements = new ArrayList<BoxElementaire> ();

    @objid ("9235b66e-b101-4dbf-86b9-bbf0ba60ebce")
    private List<BlocElementaire> elements = new ArrayList<BlocElementaire> ();

}
