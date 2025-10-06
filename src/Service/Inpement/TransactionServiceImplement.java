package Service.Implement;

import Entity.Account;
import Entity.Enum.Mode;
import Entity.Enum.OperationType;
import Entity.Enum.TransactionType;
import Entity.FeeRule;
import Entity.Transaction;
import Repository.Interfaces.AccountRepository;
import Repository.Interfaces.TransactionRepository;
import Service.Interfaces.FeeRuleService;
import Service.Interfaces.TransactionService;

import java.util.Currency;
import java.util.UUID;

public class TransactionServiceImplement implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final FeeRuleService feeRuleService;

    public TransactionServiceImplement(AccountRepository accountRepository,
                                       TransactionRepository transactionRepository,
                                       FeeRuleService feeRuleService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.feeRuleService = feeRuleService;
    }

    @Override
    public Transaction create(TransactionType type, UUID accountId, double amount) {
        Account account = accountRepository.getById(accountId);
        if (account == null || amount <= 0) return null;

        if (type == TransactionType.WITHDRAW && account.getBalance() < amount) return null;

        double newBalance = (type == TransactionType.DEPOSIT)
                ? account.getBalance() + amount
                : account.getBalance() - amount;

        account.setBalance(newBalance);
        accountRepository.update(accountId, account);

        return transactionRepository.create(type, amount, accountId);
    }

    @Override
    public Transaction internalTransfer(UUID sourceId, UUID destId, double amount) {
        Account source = accountRepository.getById(sourceId);
        Account dest = accountRepository.getById(destId);
        if (source == null || dest == null || amount <= 0 || source.getBalance() < amount) return null;

        source.setBalance(source.getBalance() - amount);
        dest.setBalance(dest.getBalance() + amount);
        accountRepository.update(sourceId, source);
        accountRepository.update(destId, dest);

        Transaction out = transactionRepository.create(TransactionType.TRANSFER_OUT, amount, sourceId);
        Transaction in = transactionRepository.create(TransactionType.TRANSFER_IN, amount, destId);

        out.setTransferOut(source);
        out.setTransferIn(dest);
        in.setTransferOut(source);
        in.setTransferIn(dest);

        return out;
    }

    @Override
    public Transaction externalTransfer(UUID sourceId, String externalAccountNumber, double amount) {
        Account source = accountRepository.getById(sourceId);

        if (source == null) {
            System.out.println("Compte source introuvable.");
            return null;
        }

        if (amount <= 0) {
            System.out.println("Montant du virement invalide.");
            return null;
        }

        double fees = 6.0;


        Currency madCurrency = Currency.getInstance("MAD");
        FeeRule feeRule = feeRuleService.getFeeRule(OperationType.TRANSFER_OUT, Mode.FIX, madCurrency);
        if (feeRule != null && feeRule.isActive()) {
            fees = feeRule.getValue().doubleValue();
        }

        if (source.getBalance() < amount + fees) {
            System.out.println("Solde insuffisant pour couvrir le montant et les frais (" + fees + " MAD).");
            return null;
        }

        source.setBalance(source.getBalance() - amount - fees);
        accountRepository.update(sourceId, source);

        Transaction tx = transactionRepository.create(TransactionType.TRANSFER_OUT, amount, sourceId);
        System.out.println("Virement externe de " + amount + " MAD vers " + externalAccountNumber +
                " effectué avec succès. Frais appliqués : " + fees + " MAD");

        return tx;
    }


}
