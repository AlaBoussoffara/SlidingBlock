package gloo.projet;

import java.io.IOException;
import java.io.InputStream;

import gloo.projet.controle.Controleur;
import gloo.projet.metier.Bloc;
import gloo.projet.metier.BlocElementaire;
import gloo.projet.metier.Case;
import gloo.projet.metier.Direction;
import gloo.projet.metier.Mur;
import gloo.projet.metier.Plateau;
import gloo.projet.metier.Position;
import gloo.projet.metier.Sortie;

public class ConsoleSlidingBloc {
    public static void main(String[] args) {
        try {
            jouerConsole();
        } catch (IOException ex) {
            System.out.println("Erreur d'entree/sortie : " + ex.getMessage());
        }
    }

    private static void jouerConsole() throws IOException {
        InputStream in = System.in;
        Plateau plateau = construirePlateauTD();
        Controleur controleur = new Controleur(plateau);

        afficherBanniere();
        afficherAide();

        while (true) {
            System.out.println();
            afficherPlateau(plateau);
            if (plateau.estVictoire()) {
                System.out.println("Victoire !");
                break;
            }
            System.out.print("Selection (ligne colonne), 'h' aide, 'r' reset, 'q' quitter : ");
            String ligne = readLine(in);
            if (ligne == null) {
                break;
            }
            ligne = ligne.trim();
            if (ligne.equalsIgnoreCase("q")) {
                break;
            }
            if (ligne.equalsIgnoreCase("h") || ligne.equals("?")) {
                afficherAide();
                continue;
            }
            if (ligne.equalsIgnoreCase("r")) {
                plateau = construirePlateauTD();
                controleur = new Controleur(plateau);
                System.out.println("Nouveau jeu.");
                continue;
            }

            String[] tokens = ligne.split("\\s+");
            if (tokens.length != 2) {
                System.out.println("Entrez deux entiers pour ligne et colonne.");
                continue;
            }
            Integer l = parseInt(tokens[0]);
            Integer c = parseInt(tokens[1]);
            if (l == null || c == null) {
                System.out.println("Coordonnees invalides.");
                continue;
            }
            Bloc bloc = plateau.getBloc(l, c);
            if (bloc == null) {
                System.out.println("Aucun bloc sur cette case.");
                continue;
            }
            controleur.selection(l, c);
            System.out.println("Bloc " + bloc.getNumero() + " selectionne.");

            System.out.print("Direction (fleches) : ");
            Direction direction = lireDirection(in);
            if (direction == null) {
                System.out.println("Direction invalide.");
                continue;
            }
            boolean deplace = controleur.action(direction);
            if (!deplace) {
                System.out.println("Deplacement impossible.");
            }
        }
    }

    private static void afficherBanniere() {
        System.out.println("=== Sliding Block (console) ===");
    }

    private static void afficherAide() {
        System.out.println("Commandes :");
        System.out.println("- Entrez 'ligne colonne' (indices a partir de 0) pour selectionner un bloc.");
        System.out.println("- Utilisez les fleches du clavier pour deplacer le bloc selectionne.");
        System.out.println("- 'h' ou '?' pour l'aide, 'r' pour recommencer, 'q' pour quitter.");
        System.out.println("Legende : M=Mur, S=Sortie, C=Case, chiffres=Bloc.");
    }

    private static void afficherPlateau(final Plateau plateau) {
        int lignes = plateau.getNbLignes();
        int colonnes = plateau.getNbColonnes();
        StringBuilder header = new StringBuilder("   ");
        for (int c = 0; c < colonnes; c++) {
            header.append(" |").append(String.format("%2d", c));
        }
        header.append(" |");
        System.out.println(header.toString());

        for (int l = 0; l < lignes; l++) {
            StringBuilder row = new StringBuilder();
            row.append(String.format("%3d", l));
            for (int c = 0; c < colonnes; c++) {
                String cell = cellule(plateau, l, c);
                row.append(" |").append(String.format("%2s", cell));
            }
            row.append(" |");
            System.out.println(row.toString());
        }
    }

    private static String cellule(final Plateau plateau, final int ligne, final int colonne) {
        if (plateau.getCase(ligne, colonne) == null) {
            return "?";
        }
        Integer numeroBloc = plateau.getNumeroBloc(ligne, colonne);
        if (numeroBloc != null) {
            return Integer.toString(numeroBloc);
        }
        if (plateau.getCase(ligne, colonne) instanceof Mur) {
            return "M";
        }
        if (plateau.getCase(ligne, colonne) instanceof Sortie) {
            return "S";
        }
        return "C";
    }

    private static Integer parseInt(final String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static String readLine(final InputStream in) throws IOException {
        StringBuilder builder = new StringBuilder();
        while (true) {
            int ch = in.read();
            if (ch == -1) {
                return builder.length() == 0 ? null : builder.toString();
            }
            if (ch == '\n') {
                return builder.toString();
            }
            if (ch == '\r') {
                continue;
            }
            builder.append((char) ch);
        }
    }

    private static Direction lireDirection(final InputStream in) throws IOException {
        int ch;
        do {
            ch = in.read();
            if (ch == -1) {
                return null;
            }
        } while (ch == '\n' || ch == '\r');

        if (ch == 27) {
            int next = in.read();
            if (next != '[') {
                return null;
            }
            int code = in.read();
            switch (code) {
            case 'A':
                return Direction.HAUT;
            case 'B':
                return Direction.BAS;
            case 'C':
                return Direction.DROITE;
            case 'D':
                return Direction.GAUCHE;
            default:
                return null;
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append((char) ch);
        while (true) {
            int c = in.read();
            if (c == -1 || c == '\n') {
                break;
            }
            if (c == '\r') {
                continue;
            }
            builder.append((char) c);
        }
        return parseDirectionTexte(builder.toString());
    }

    private static Direction parseDirectionTexte(final String input) {
        if (input == null) {
            return null;
        }
        String value = input.trim().toUpperCase();
        if (value.isEmpty()) {
            return null;
        }
        switch (value) {
        case "W":
        case "Z":
            return Direction.HAUT;
        case "S":
            return Direction.BAS;
        case "A":
        case "Q":
            return Direction.GAUCHE;
        case "D":
            return Direction.DROITE;
        default:
            return null;
        }
    }

    private static Plateau construirePlateauTD() {
        Plateau plateau = new Plateau(5, 4);

        for (int ligne = 0; ligne < 5; ligne++) {
            for (int colonne = 0; colonne < 4; colonne++) {
                Position position = new Position(ligne, colonne);
                if (ligne == 0 || ligne == 4 || colonne == 0 || colonne == 3) {
                    if ((ligne == 1 && colonne == 3) || (ligne == 2 && colonne == 3)) {
                        plateau.ajouterCase(new Sortie(plateau, position));
                    } else {
                        plateau.ajouterCase(new Mur(plateau, position));
                    }
                } else {
                    plateau.ajouterCase(new Case(plateau, position));
                }
            }
        }

        Bloc blocPrincipal = new Bloc(0, plateau);
        Bloc blocSecondaire = new Bloc(1, plateau);

        new BlocElementaire(blocSecondaire, plateau.getCase(1, 1));
        new BlocElementaire(blocPrincipal, plateau.getCase(2, 2));
        new BlocElementaire(blocPrincipal, plateau.getCase(3, 2));

        return plateau;
    }
}
