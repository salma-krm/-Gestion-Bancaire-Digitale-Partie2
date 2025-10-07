package com.systemeBancaire.com;

import Controller.*;
import Entity.*;
import Entity.Enum.*;
import Repository.Inpelement.*;
import Repository.Interfaces.*;
import Service.Inpement.*;
import Service.Interfaces.*;


import java.math.BigDecimal;
import java.security.Provider;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static final Scanner scanner = new Scanner(System.in);

    // Repositories
    private static final UserRepository userRepository = new InMemoryUserRepository();
    private static final ClientRepository clientRepository = new inMemoryClientRepository();
    private static final AccountRepository accountRepository = new inMemoryAccountRepository();
    private static final TransactionRepository transactionRepository = new InMemoryTransactionRepository(accountRepository);
    private static final FeeRuleRepository feeRuleRepository = new inMemoryFeeRuleRepository();
    private static final CreditRepository creditRepository = new Repository.Inpelement.inMemoryCreditRepository();

    // Services
    private static final UserService userService = new UserServiceImplement(userRepository);
    private static final ClientService clientService = new ClientServiceImplement(clientRepository, accountRepository);
    private static final AccountService accountService = new AccountServiceImplement(accountRepository, clientRepository);
    private static final FeeRuleService feeruleService = new Service.Implement.FeeRuleServiceImplement(feeRuleRepository);
    private static final TransactionService transactionService = new Service.Implement.TransactionServiceImplement(accountRepository, transactionRepository,feeruleService);
    private static final CreditService creditService = new Service.Inpement.CreditServiceImplement(creditRepository,accountRepository);


    // Controllers
    private static final AuthController authController = new AuthController(userService);
    private static final ClientController clientController = new ClientController(clientService);
    private static final AccountController accountController = new AccountController(accountService);
    private static final TransactionController transactionController = new TransactionController(transactionService);
    private static final FeeRuleController feeRuleController = new FeeRuleController(feeruleService);
    private static final controller.CreditController creditController = new controller.CreditController(creditService,accountService);

    public static User currentUser = null;

    public static void main(String[] args) {
        int choix;
        do {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Connexion");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> login();
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide.");
            }
        } while (choix != 0);
    }

    private static void login() {
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        User user = userService.login(new User(null, null, email, password, null));
        if (user != null) {
            System.out.println("Connexion réussie !");
            currentUser = user;

            if (currentUser.getRole() == Role.admin) {
                Menus.showMenuAdmin();
            } else if (currentUser.getRole() == Role.teller) {
                Menus.showMenuTeller();
            } else if (currentUser.getRole() == Role.manager){
                Menus.showMenuManager();
        }else {
                System.out.println("Rôle non pris en charge.");
            }
        } else {
            System.out.println("Échec de la connexion.");
        }
    }

    public static void register() {
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        System.out.println("Role : 1-ADMIN, 2-AUDITOR, 3-TELLER, 4-MANAGER");
        int roleInput = scanner.nextInt();
        scanner.nextLine();

        Role role = switch (roleInput) {
            case 1 -> Role.admin;
            case 2 -> Role.auditor;
            case 3 -> Role.teller;
            case 4 -> Role.manager;
            default -> null;
        };

        if (role == null) {
            System.out.println("Rôle invalide.");
            return;
        }

        User user = new User(prenom, nom, email, password, role);
        User registered = authController.register(user);

        if (registered != null) System.out.println("Inscription réussie !");
    }

    public static void gestionRole() {
        int choix;
        System.out.println("\n --- Gestion des rôles --- ");
        System.out.println("1. Voir tous les utilisateurs avec leurs rôles");
        System.out.println("2. Modifier un rôle");
        System.out.println("3. Retour");
        choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1 -> getAllUser();
            case 2 -> editRole();
            case 3 -> Menus.showMenuAdmin();
        }
    }

    public static void getAllUser() {
        List<User> users = authController.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé.");
            return;
        }

        System.out.println("\n--- Liste des utilisateurs ---");
        for (User user : users) {
            System.out.println("ID: " + user.getId() +
                    " | Nom: " + user.getFirstName() + " " + user.getLastName() +
                    " | Email: " + user.getEmail() +
                    " | Rôle: " + user.getRole());
        }
    }


    public static void editRole() {

    }

    public static void editProfil() {

    }

    public static void validerCompteClient() {
        System.out.print("Entrer CIN client à valider : ");
        String cin = scanner.nextLine();
        Client client = accountController.getCinClient(cin);
        if (client == null || client.getAccounts().isEmpty()) {
            System.out.println("Client ou comptes introuvables.");
            return;
        }

        for (Account acc : client.getAccounts()) {
            if (!acc.getActive() && (acc.getType() == AccountType.CREDIT || acc.getType() == AccountType.EPARGNE)) {
                acc.setActive(true);
                accountRepository.update(acc.getId(), acc);
                System.out.println("Compte ID " + acc.getId() + " validé.");
            }
        }
    }


    public static void creeCompteClient() {
        System.out.print("Firstname : ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Lastname : ");
        String lastName = scanner.nextLine().trim();
        System.out.print("CIN : ");
        String CIN = scanner.nextLine().trim();
        System.out.print("Phone number : ");
        String phoneNumber = scanner.nextLine().trim();
        System.out.print("Email : ");
        String email = scanner.nextLine().trim();

        AccountType type = AccountType.COURANT;

        System.out.print("Balance initiale : ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        Client client = new Client(firstName, lastName, CIN, phoneNumber, email,200000);
        Account account = new Account(client, balance, type, true);

        Client clientCreated = clientController.create(client, account);
        if (clientCreated != null) {
            System.out.println("Client et compte créés avec succès !");
        }
    }

    public static void creeNouveauCompteClient() {
        System.out.print("Entrer CIN client existant : ");
        String cin = scanner.nextLine().trim();
        Client client = accountController.getCinClient(cin);
        if (client == null) {
            System.out.println("Client introuvable.");
            return;
        }

        System.out.println("Type de compte : 1-CREDIT, 2-EPARGNE");
        int choice = scanner.nextInt();
        scanner.nextLine();
        AccountType type = choice == 1 ? AccountType.CREDIT : choice == 2 ? AccountType.EPARGNE : null;
        if (type == null) {
            System.out.println("Type invalide.");
            return;
        }

        System.out.print("Balance initiale : ");
        double balance = scanner.nextDouble();
        scanner.nextLine();


        Account account = new Account();
        account.setClient(client);
        account.setBalance(balance);
        account.setType(type);
        account.setIsActive(true);
        account.setCreatedAt(Instant.now());
        account.setUpdatedAt(Instant.now());

        accountController.create(client,account);

        System.out.println("Compte " + type + " créé avec succès et activé.");
    }


    public static void deposer() {
        System.out.print("CIN : ");
        String cin = scanner.nextLine();
        Client client = accountController.getCinClient(cin);
        if (client == null) {
            System.out.println("Client introuvable.");
            return;
        }

        if (client.getAccounts() == null || client.getAccounts().isEmpty()) {
            System.out.println("Aucun compte trouvé.");
            return;
        }

        System.out.println("Comptes disponibles :");
        for (Account acc : client.getAccounts()) {
            System.out.println("ID: " + acc.getId() + " | Solde: " + acc.getBalance());
        }

        System.out.print("ID du compte : ");
        UUID accountId = UUID.fromString(scanner.nextLine());
        Account account = accountController.getById(accountId);
        if (account == null) {
            System.out.println("Compte introuvable.");
            return;
        }

        System.out.print("Montant : ");
        double montant = scanner.nextDouble();
        scanner.nextLine();

        Transaction t = transactionController.deposit(montant, accountId);
        if (t != null) {
            System.out.println("Dépôt réussi. Nouveau solde : " + t.getAccount().getBalance());
        }
    }

    public static void retrait() {
        System.out.print("CIN : ");
        String cin = scanner.nextLine();
        Client client = accountController.getCinClient(cin);
        if (client == null || client.getAccounts().isEmpty()) {
            System.out.println("Client ou compte introuvable.");
            return;
        }

        System.out.print("ID du compte : ");
        UUID accountId = UUID.fromString(scanner.nextLine());
        Account account = accountController.getById(accountId);
        if (account == null) {
            System.out.println("Compte introuvable.");
            return;
        }

        System.out.print("Montant : ");
        double montant = scanner.nextDouble();
        scanner.nextLine();

        Transaction t = transactionController.withdraw(montant, accountId);
        if (t != null) {
            System.out.println("Retrait réussi. Nouveau solde : " + t.getAccount().getBalance());
        }
    }

    public static void virementExterne() {
        System.out.print("CIN compte source : ");
        String cinSource = scanner.nextLine();
        Client clientSource = accountController.getCinClient(cinSource);
        if (clientSource == null) {
            System.out.println("Client introuvable.");
            return;
        }

        System.out.println("Comptes du client source :");
        for (Account acc : clientSource.getAccounts()) {
            System.out.println("ID: " + acc.getId() + " | Solde: " + String.format("%.2f", acc.getBalance()));
        }

        System.out.print("ID compte source : ");
        UUID accountIdSource = UUID.fromString(scanner.nextLine());

        System.out.print("Numéro du compte externe : ");
        String externalAccountNumber = scanner.nextLine();

        System.out.print("Montant du virement externe : ");
        double montant = scanner.nextDouble();
        scanner.nextLine();

        Transaction txOut = transactionController.externalTransfer(accountIdSource, externalAccountNumber, montant);
        if (txOut != null) {
            Account updatedAccount = accountController.getById(accountIdSource);
            String currencyCode = "MAD";

            System.out.println("Virement externe réussi !");
            System.out.println("Nouveau solde du compte source : " + String.format("%.2f", updatedAccount.getBalance()) + " " + currencyCode);
            System.out.println("Virement effectué vers le compte externe : " + externalAccountNumber);
        } else {
            System.out.println("Échec du virement externe.");
        }
    }

    public static void virementInterne() {
        System.out.print("CIN compte source : ");
        String cinSource = scanner.nextLine();
        Client clientSource = accountController.getCinClient(cinSource);
        if (clientSource == null) {
            System.out.println("Client source introuvable.");
            return;
        }

        System.out.println("Comptes du client source :");
        for (Account acc : clientSource.getAccounts()) {
            System.out.println("ID: " + acc.getId() + " | Solde: " + String.format("%.2f", acc.getBalance()));
        }

        System.out.print("ID compte source : ");
        UUID accountIdSource = UUID.fromString(scanner.nextLine());

        System.out.print("CIN compte destination : ");
        String cinDest = scanner.nextLine();
        Client clientDest = accountController.getCinClient(cinDest);
        if (clientDest == null) {
            System.out.println("Client destination introuvable.");
            return;
        }

        System.out.println("Comptes du client destination :");
        for (Account acc : clientDest.getAccounts()) {
            System.out.println("ID: " + acc.getId() + " | Solde: " + String.format("%.2f", acc.getBalance()));
        }

        System.out.print("ID compte destination : ");
        UUID accountIdDest = UUID.fromString(scanner.nextLine());

        System.out.print("Montant du virement : ");
        double montant = scanner.nextDouble();
        scanner.nextLine();

        Transaction txOut = transactionController.internalTransfer(accountIdSource, accountIdDest, montant);
        if (txOut != null) {
            System.out.println("Virement interne réussi !");
            System.out.println("Nouveau solde du compte source : " + String.format("%.2f", accountController.getById(accountIdSource).getBalance()));
            System.out.println("Nouveau solde du compte destination : " + String.format("%.2f", accountController.getById(accountIdDest).getBalance()));
        } else {
            System.out.println("Échec du virement interne.");
        }
    }

    public static void demandeCredit(){
        creditController.demanderCredit(scanner);
    }
    public static void validerCredit(){
        creditController.validerCredit(scanner);
    }

    public static void createFeeRule() {
        try {
            System.out.println("\n--- Création d'une règle de frais ---");

            System.out.print("Type d'opération (TRANSFER_OUT, CREDIT_SIMPLE, CREDIT_COMPOSE, CREDIT_REPAYMENT) : ");
            OperationType operationType = OperationType.valueOf(scanner.nextLine().trim().toUpperCase());

            System.out.print("Mode (FIX, PERCENT) : ");
            Mode mode = Mode.valueOf(scanner.nextLine().trim().toUpperCase());

            System.out.print("Valeur : ");
            BigDecimal value = new BigDecimal(scanner.nextLine());

            System.out.print("Devise (MAD, EUR, USD...) : ");
            Currency currency = Currency.getInstance(scanner.nextLine().trim().toUpperCase());

            FeeRule rule = new FeeRule(operationType, mode, value, currency, true);
            feeRuleController.create(rule);
            System.out.println("Règle créée avec succès.");

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
    public static void updateFeeRule() {
        try {
            System.out.println("\n--- Mise à jour d'une règle de frais ---");

            System.out.print("ID de la règle (UUID) : ");
            UUID id = UUID.fromString(scanner.nextLine());

            System.out.print("Type d'opération (TRANSFER_OUT, CREDIT_SIMPLE, CREDIT_COMPOSE, CREDIT_REPAYMENT) : ");
            OperationType operationType = OperationType.valueOf(scanner.nextLine().trim().toUpperCase());

            System.out.print("Mode (FIX, PERCENT) : ");
            Mode mode = Mode.valueOf(scanner.nextLine().trim().toUpperCase());

            System.out.print("Valeur : ");
            BigDecimal value = new BigDecimal(scanner.nextLine());

            System.out.print("Devise (MAD, EUR, USD...) : ");
            Currency currency = Currency.getInstance(scanner.nextLine().trim().toUpperCase());

            FeeRule rule = new FeeRule(operationType, mode, value, currency, true);
            FeeRule updated = feeRuleController.update(id, rule);

            if (updated != null) {
                System.out.println("Règle mise à jour : " + updated);
            } else {
                System.out.println("Règle non trouvée.");
            }

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
    public static void getAllFeeRules() {
        List<FeeRule> feeRules = feeRuleController.getAll();

        if (feeRules == null || feeRules.isEmpty()) {
            System.out.println(" Aucune règle de frais trouvée.");
            return;
        }

        System.out.println("\n--- Liste des règles de frais ---");
        for (FeeRule rule : feeRules) {
            System.out.println("ID: " + rule.getId()
                    + " | Type d'opération: " + rule.getOperationType()
                    + " | Mode: " + rule.getMode()
                    + " | Valeur: " + rule.getValue()
                    + " " + rule.getCurrency()
                    + " | Actif: " + (rule.isActive() ? "Oui" : "Non"));
        }
    }



}
