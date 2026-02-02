package gloo.projet.controle;

import gloo.projet.metier.ContenuPlateau;
import gloo.projet.metier.Direction;


public interface IControleur {
    /**
     * Méthode appellée par l'IHM quand je joueur clique sur le plateau.
     *
     * @param ligne numéro de ligne de la case cliquée
     * @param colonne numéro de colonne de la case cliquée
     */
    void selection(int ligne, int colonne);

    /**
     * Méthode appellée par l'IHM quand je joueur appuie sur l'une des
     * flèches du clavier.
     *
     * @param direction sens de la flèche utilisée
     */
    void action(Direction direction);

    /**
     * Méthode appellée par l'IHM pour connaitre le numéro du bloc sélectionné
     *
     * @return le numéro du bloc sélectionné (de 0 à getNbBlocs() - 1) ou une
     *         autre valeur quand aucun bloc n'est sélectionné
     */
    int getNumeroBlocSelectionne();

    /**
     * Méthode appellée par l'IHM pour savoir si le jeu est terminé.
     *
     * @return true si le jeu est terminé, false sinon
     */
    boolean jeuTermine();

    /**
     * Méthode appellée par l'IHM pour connaître le nombre de
     * lignes du plateau courant.
     *
     * @return le nombre de lignes du plateau
     */
    int getNbLignes();

    /**
     * Méthode appellée par l'IHM pour connaître le nombre de
     * colonnes du plateau courant.
     *
     * @return le nombre de colonnes du plateau
     */
    int getNbColonnes();

    /**
     * Méthode appellée par l'IHM pour connaître le type de case
     * du plateau courant.
     *
     * @param ligne numéro de la ligne de la case (de 0 à getNbLignes() - 1)
     * @param colonne numéro de la colonne de la case (de 0 à getNbColonnes() - 1)
     * @return le type de la case (CASE, MUR ou SORTIE)
     */
    ContenuPlateau getContenu(int ligne, int colonne);

    /**
     * Méthode appellée par l'IHM pour connaitre le nombre de blocs.
     *
     * @return le nombre de blocs
     */
    int getNbBlocs();

    /**
     * Méthode appellée par l'IHM pour connaitre les coordonnées des cases constituant
     * un bloc.
     *
     * @param numero numéro du bloc (de 0 à getNbLignes() - 1)
     * @return un tableau de tableaux d'entiers ; la première dimension est égale au 
     *         nombre de cases constituant le bloc, la seconde dimension vaut 2 et
     *         contient pour chaque case son numéro de ligne et son numéro de colonne
     */
    int[][] getPositionsBloc(int numero);

}
