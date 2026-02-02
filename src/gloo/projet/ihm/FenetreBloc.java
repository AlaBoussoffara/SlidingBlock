package gloo.projet.ihm;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import gloo.projet.controle.IControleur;
import gloo.projet.metier.Direction;

@SuppressWarnings("serial")
public class FenetreBloc extends JFrame implements KeyListener {
    private static final int LARGEUR_FENETRE = 256;
    private static final int HAUTEUR_FENETRE = 320;
    private static final int HAUTEUR_TITRE_FENETRE = 20;

    private IControleur controleur;

    @Override
    public void keyTyped(KeyEvent e) {
        // Nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {

        Direction direction = null;
        switch( e.getKeyCode() ) {
        case KeyEvent.VK_UP:
        case KeyEvent.VK_KP_UP:
            direction = Direction.HAUT;
            break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_KP_DOWN:
            direction = Direction.BAS;
            break;
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_KP_LEFT:
            direction = Direction.GAUCHE;
            break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_KP_RIGHT:
            direction = Direction.DROITE;
            break;
        }
        if( direction == null ) return;
        controleur.action( direction );
        if( controleur.jeuTermine() ) {
            JOptionPane.showMessageDialog( this, "Vous avez gagné !" );
            System.exit( 0 );
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Nothing
    }

    public FenetreBloc(IControleur controleur) {
        this.controleur = controleur;
        
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setPreferredSize( new Dimension( LARGEUR_FENETRE, HAUTEUR_FENETRE + HAUTEUR_TITRE_FENETRE ));
        this.setTitle( "Sliding Block" );
        
        this.add( new PanneauBloc( controleur ));
        this.addKeyListener( this );
        
        this.pack();
        this.setVisible( true );
    }

}
