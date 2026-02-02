package gloo.projet.metier;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("30ce8baa-f718-4149-95ba-17eadd36537a")
public abstract class AbstractCase {
    @objid ("13195b9f-a275-4199-b5b2-24215fd5a565")
    protected BlocElementaire blocElementaire;

    @objid ("86f5ae5c-f50d-4bdd-acb0-7bad181e6b65")
    private Plateau plateau;

    @objid ("9059a2dc-6246-4957-a259-61cf715f1d1d")
    private Position position;

}
