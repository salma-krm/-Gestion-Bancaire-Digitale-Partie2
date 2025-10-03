package Service.Inpement;

import Entity.Account;
import Entity.Enum.TransactionType;
import Entity.Transaction;
import Repository.Interfaces.AccountRepository;
import Repository.Interfaces.TransactionRepository;
import Service.Interfaces.TransactionService;

import java.util.UUID;

public class TransactionServiceImplement implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImplement(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction create(TransactionType type, UUID accountId, double amount) {
        if (accountId == null || amount <= 0) return null;

        Account account = accountRepository.getById(accountId);
        if (account == null) {
            System.out.println("Compte introuvable.");
            return null;
        }

        double newBalance;
        if (type == TransactionType.DEPOSIT) {
            newBalance = account.getBalance() + amount;
        } else if (type == TransactionType.WITHDRAW) {
            if (account.getBalance() < amount) {
                System.out.println("Solde insuffisant.");
                return null;
            }
            newBalance = account.getBalance() - amount;
        } else {
            System.out.println("Type de transaction non supporté pour cette méthode.");
            return null;
        }

        account.setBalance(newBalance);
        accountRepository.update(accountId, account);

        return transactionRepository.create(type, amount, accountId);
    }

    @Override
    public Transaction transfer(UUID sourceAccountId, UUID destAccountId, double amount) {
        return internalTransfer(sourceAccountId, destAccountId, amount);
    }

    public Transaction internalTransfer(UUID sourceAccountId, UUID destAccountId, double amount) {
        if (sourceAccountId == null || destAccountId == null || amount <= 0) return null;

        Account source = accountRepository.getById(sourceAccountId);
        Account dest = accountRepository.getById(destAccountId);

        if (source == null || dest == null || !source.getActive() || !dest.getActive()) {
            System.out.println("Comptes invalides ou inactifs.");
            return null;
        }

        if (source.getBalance() < amount) {
            System.out.println("Solde insuffisant sur le compte source.");
            return null;
        }

        source.setBalance(source.getBalance() - amount);
        dest.setBalance(dest.getBalance() + amount);

        accountRepository.update(sourceAccountId, source);
        accountRepository.update(destAccountId, dest);

        transactionRepository.create(TransactionType.TRANSFER_OUT, amount, sourceAccountId);
        transactionRepository.create(TransactionType.TRANSFER_IN, amount, destAccountId);

        System.out.println("✅ Virement interne de " + amount + " réussi !");
        return null;
    }

    public Transaction externalTransfer(UUID sourceAccountId, String externalAccountNumber, double amount) {
        Account source = accountRepository.getById(sourceAccountId);

        if (source == null || !source.getActive()) {
            System.out.println("Compte source invalide ou inactif.");
            return null;
        }

        if (amount <= 0 || source.getBalance() < amount) {
            System.out.println("Montant invalide ou solde insuffisant.");
            return null;
        }

        source.setBalance(source.getBalance() - amount);
        accountRepository.update(sourceAccountId, source);

        transactionRepository.create(TransactionType.TRANSFER_OUT, amount, sourceAccountId);

        System.out.println("✅ Virement externe vers " + externalAccountNumber + " réussi !");
        return null;
    }
}
