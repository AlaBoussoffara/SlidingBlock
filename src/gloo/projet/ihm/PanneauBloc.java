package gloo.projet.ihm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import javax.swing.JPanel;
import gloo.projet.controle.IControleur;
import gloo.projet.metier.Direction;


@SuppressWarnings("serial")
public class PanneauBloc extends JPanel implements MouseListener {
    private static final int COTE_CARRE = 64;
    private static final int AJUSTEMENT_INTERIEUR_BLOC = 4;
    private static final int CONTOUR_BLOC = 2;
    private static final int CONTOUR_BLOC_SELECTIONNE = 5;

    private static final Color COULEUR_MUR = Color.GRAY;
    private static final Color COULEUR_CASE = Color.ORANGE;
    private static final Color COULEUR_SORTIE = Color.GREEN;
    private static final Color COULEUR_BLOC = Color.BLUE;
    private static final Color COULEUR_BLOC_PRINCIPAL = Color.YELLOW;
    private static final Color COULEUR_CONTOUR = Color.BLACK;

    private IControleur controleur;

    public PanneauBloc(IControleur controleur) {
        this.controleur = controleur;
        this.addMouseListener(this);
    }

    public void paint(Graphics g) {
        super.paint( g );
        
        // Le côté métier raisonne en [ligne, colonne]
        // Le côté IHM raisonne en [x, y]
        // Donc x <=> colonne et y <=> ligne
        for( int l = 0; l < controleur.getNbLignes(); ++l ) {
            for( int c = 0; c < controleur.getNbColonnes(); ++c ) {
                switch (controleur.getContenu( l, c )) {
                case CASE :
                    g.setColor(COULEUR_CASE);
                    break;
                case MUR :
                    g.setColor(COULEUR_MUR);
                    break;
                case SORTIE :
                    g.setColor(COULEUR_SORTIE);
                    break;
                default:
                    break;
                }
                g.fillRect(c * COTE_CARRE, l * COTE_CARRE, COTE_CARRE, COTE_CARRE);
            }
        }
        
        Graphics2D g2 = (Graphics2D) g;
        for( int i = 0; i < controleur.getNbBlocs(); ++i ) {
            int[][] positions = controleur.getPositionsBloc(i);
            
            Area area = new Area();
            for (int[] pos : positions) {
                area.add(new Area(new Rectangle(pos[1] * COTE_CARRE, pos[0] * COTE_CARRE, COTE_CARRE, COTE_CARRE)));
            }
            
            Polygon polygon = new Polygon();
            float[] coords = new float[6];
            for (PathIterator it = area.getPathIterator(null); ! it.isDone(); it.next()) {
                switch (it.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO :
                 case PathIterator.SEG_LINETO :
                    polygon.addPoint((int)coords[0], (int)coords[1]);
                    break;
                case PathIterator.SEG_CLOSE :
                    break;
                default :
                    break;
                }
            }
            
            // Calcul d'un polygone intérieur pour mieux visualiser les blocs
            Polygon polygon_interieur = new Polygon();
            for (int j = 0; j < polygon.npoints; ++j) {
                int x = polygon.xpoints[j];
                int y = polygon.ypoints[j];
                // Le parcours semble se faire toujours dans le sens trigonométrique
                // Il faut regarder la direction courante ainsi que la suivante pour calculer les ajustements des points
                if (j == 0) {
                    // Soit à gauche, soit en bas : même ajustement
                    x += AJUSTEMENT_INTERIEUR_BLOC;
                    y += AJUSTEMENT_INTERIEUR_BLOC;
                }
                else {
                    // Peut-on faire plus simple ?
                    Direction direction_courante = null;
                    int x_precedent = polygon.xpoints[j - 1];
                    int y_precedent = polygon.ypoints[j - 1];
                    if ((x_precedent == x) && (y_precedent <  y)) direction_courante = Direction.BAS;
                    if ((x_precedent == x) && (y_precedent >  y)) direction_courante = Direction.HAUT;
                    if ((x_precedent <  x) && (y_precedent == y)) direction_courante = Direction.DROITE;
                    if ((x_precedent >  x) && (y_precedent == y)) direction_courante = Direction.GAUCHE;
                     Direction direction_suivante = null;
                     int x_suivant = polygon.xpoints[0];
                     int y_suivant = polygon.ypoints[0];
                     if (j < polygon.npoints - 1) {
                         x_suivant = polygon.xpoints[j + 1];
                         y_suivant = polygon.ypoints[j + 1];
                     }
                     if ((x == x_suivant) && (y <  y_suivant)) direction_suivante = Direction.BAS;
                     if ((x == x_suivant) && (y >  y_suivant)) direction_suivante = Direction.HAUT;
                     if ((x <  x_suivant) && (y == y_suivant)) direction_suivante = Direction.DROITE;
                     if ((x >  x_suivant) && (y == y_suivant)) direction_suivante = Direction.GAUCHE;
                     switch (direction_courante) {
                     case BAS:
                         x += AJUSTEMENT_INTERIEUR_BLOC;
                         if (direction_suivante == Direction.DROITE) y -= AJUSTEMENT_INTERIEUR_BLOC;
                         else                                        y += AJUSTEMENT_INTERIEUR_BLOC;
                         break;
                     case HAUT:
                         x -= AJUSTEMENT_INTERIEUR_BLOC;
                         if (direction_suivante == Direction.GAUCHE) y += AJUSTEMENT_INTERIEUR_BLOC;
                         else                                        y -= AJUSTEMENT_INTERIEUR_BLOC;
                         break;
                     case DROITE:
                         y -= AJUSTEMENT_INTERIEUR_BLOC;
                         if (direction_suivante == Direction.HAUT)   x -= AJUSTEMENT_INTERIEUR_BLOC;
                         else                                        x += AJUSTEMENT_INTERIEUR_BLOC;
                         break;
                     case GAUCHE:
                         y += AJUSTEMENT_INTERIEUR_BLOC;
                         if (direction_suivante == Direction.BAS)    x += AJUSTEMENT_INTERIEUR_BLOC;
                         else                                        x -= AJUSTEMENT_INTERIEUR_BLOC;
                         break;
                     default:
                         System.err.println("PanneauBloc.paint() : erreur inattendue");
                         break;
                     }
                }
                polygon_interieur.addPoint(x, y);
            }
        
            g2.setColor(i == 0 ? COULEUR_BLOC_PRINCIPAL : COULEUR_BLOC);
            g.fillPolygon(polygon_interieur);
            g2.setStroke(new BasicStroke( i == controleur.getNumeroBlocSelectionne() ? CONTOUR_BLOC_SELECTIONNE : CONTOUR_BLOC));
            g2.setColor(COULEUR_CONTOUR);
            g.drawPolygon(polygon_interieur);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        controleur.selection( e.getPoint().y / COTE_CARRE, e.getPoint().x / COTE_CARRE);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Nothing
    }

}
