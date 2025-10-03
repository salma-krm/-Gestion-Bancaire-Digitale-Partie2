package com.systemeBancaire.com;

import java.util.Scanner;

public class Menus {
    private static final Scanner scanner = Main.scanner;

    public static void showMenuAdmin() {
        int choix;
        do {
            System.out.println("\n--- Menu Admin ---");
            System.out.println("1. Créer un compte utilisateur");
            System.out.println("2. Gestion des rôles");
            System.out.println("3. Modifier le profil");
            System.out.println("4. Valider un compte client");
            System.out.println("5. Créer un compte Courant pour client");
            System.out.println("6. Créer un compte Crédit/Épargne pour client");
            System.out.println("7. Dépôt");
            System.out.println("8. Retrait");
            System.out.println("9. Virement interne");
            System.out.println("10. Déconnexion");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> Main.register();
                case 2 -> Main.gestionRole();
                case 3 -> Main.editProfil();
                case 4 -> Main.validerCompteClient();
                case 5 -> Main.creeCompteClient();
                case 6 -> Main.creeNouveauCompteClient();
                case 7 -> Main.deposer();
                case 8 -> Main.retrait();
                case 9 -> Main.virementInterne();
                case 10 -> Main.currentUser = null;
                default -> System.out.println("Choix invalide");
            }
        } while (choix != 10);
    }

    public static void showMenuTeller() {
        int choix;
        do {
            System.out.println("\n---- Menu Teller ----");
            System.out.println("1. Créer un compte Courant");
            System.out.println("2. Créer un compte Crédit/Épargne");
            System.out.println("3. Dépôt");
            System.out.println("4. Retrait");
            System.out.println("5. Virement interne");
            System.out.println("6. Déconnexion");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> Main.creeCompteClient();
                case 2 -> Main.creeNouveauCompteClient();
                case 3 -> Main.deposer();
                case 4 -> Main.retrait();
                case 5 -> Main.virementInterne();
                case 6 -> Main.currentUser = null;
                default -> System.out.println("Choix invalide");
            }
        } while (choix != 6);
    }
}
