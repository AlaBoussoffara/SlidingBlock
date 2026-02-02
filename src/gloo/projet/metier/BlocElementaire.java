package gloo.projet.metier;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("d565819f-5f71-4969-acda-e6db5b91f750")
public class BlocElementaire {
    @objid ("6fc238a9-aae3-42f0-a8fd-99379674a210")
    private AbstractCase kase;

    @objid ("86153c83-3ec1-4313-876c-e868988d7a25")
    private Bloc bloc;

    public BlocElementaire(final Bloc bloc, final AbstractCase kase) {
        if (bloc == null || kase == null) {
            throw new IllegalArgumentException("bloc and case must be non-null");
        }
        this.bloc = bloc;
        this.kase = kase;
        this.bloc.ajouterElement(this);
        this.kase.setBlocElementaire(this);
    }

    public Bloc getBloc() {
        return bloc;
    }

    public AbstractCase getCase() {
        return kase;
    }

    void setCase(final AbstractCase kase) {
        this.kase = kase;
    }

}
