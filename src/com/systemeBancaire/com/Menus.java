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
            System.out.println("10. Virement externe");
            System.out.println("11. Gestion des FeeRule");
            System.out.println("12.demande credit ");
            System.out.println("13. Déconnexion");
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
                case 10 -> Main.virementExterne();
                case 11 -> MenuGestionFeeRule();
                case 12 -> Main.demanderCredit();
                case 13 -> Main.currentUser = null;
                default -> System.out.println("Choix invalide");
            }
        } while (choix != 12);
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
            System.out.println("6. Virement externe");
            System.out.println("7. Déconnexion");
            System.out.println("8. demande credit");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> Main.creeCompteClient();
                case 2 -> Main.creeNouveauCompteClient();
                case 3 -> Main.deposer();
                case 4 -> Main.retrait();
                case 5 -> Main.virementInterne();
                case 6 -> Main.virementExterne();
                case 7 -> Main.demanderCredit();
                case 8 -> Main.currentUser = null;
                default -> System.out.println("Choix invalide");
            }
        } while (choix != 8);
    }

    public static void showMenuManager() {
        int choix;
        do {
            System.out.println("\n---- Menu Manager ----");
            System.out.println("1. Gestion des comptes");
            System.out.println("2. Validation des opérations sensibles");
            System.out.println("3. Gestion des crédits");
            System.out.println("4. Rapports & Statistiques");
            System.out.println("5. Gestion des frais & commissions");
            System.out.println("6. Modifier mon profil");
            System.out.println("7. Changer mot de passe");
            System.out.println("8. Déconnexion");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
//                case 1 -> Main.gestionComptesManager();
//                case 2 -> Main.validationOperationsSensibles();
//                case 3 -> Main.gestionCredits();
//                case 4 -> Main.rapportsStatistiques();
                case 5 -> MenuGestionFeeRule();
                case 6 -> Main.editProfil();
//                case 7 -> Main.changePassword();
                case 8 -> Main.currentUser = null;
                default -> System.out.println("Choix invalide");
            }
        } while (choix != 8);
    }

    public static void MenuGestionFeeRule(){
        int choix;
        do {
            System.out.println("\n---- Menu gestion FeeRule ----");
            System.out.println("1. Lister toutes les règles");
            System.out.println("2. Créer une règle");
            System.out.println("3. Mettre à jour une règle");

            System.out.println("4. Retour");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> Main.getAllFeeRules();
                case 2 -> Main.createFeeRule();
                case 3 -> Main.updateFeeRule();

                case 4 -> Main.currentUser = null;
                default -> System.out.println("Choix invalide");
            }
        } while (choix != 4);

    }
}
