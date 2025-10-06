package Service.Implement;

import Entity.Enum.Mode;
import Entity.Enum.OperationType;
import Entity.FeeRule;
import Repository.Interfaces.FeeRuleRepository;
import Service.Interfaces.FeeRuleService;
import utils.FeeRuleValidator;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

public class FeeRuleServiceImplement implements FeeRuleService {

    private final FeeRuleRepository feeRuleRepository;

    public FeeRuleServiceImplement(FeeRuleRepository feeRuleRepository) {
        this.feeRuleRepository = feeRuleRepository;
    }

    @Override
    public FeeRule create(FeeRule feeRule) {
        return feeRuleRepository.create(feeRule);
    }

    @Override
    public FeeRule update(UUID id, FeeRule feeRule) {
        FeeRuleValidator.validate(feeRule);
        return feeRuleRepository.update(id, feeRule);
    }

    @Override
    public FeeRule getById(UUID id) {
        return feeRuleRepository.getById(id);
    }

    @Override
    public List<FeeRule> getAll() {
        return feeRuleRepository.getAll();
    }

    // ✅ Correction ici : utilisation de java.util.Currency
    @Override
    public FeeRule getFeeRule(OperationType operationType, Mode mode, Currency currency) {
        return feeRuleRepository.findByOperationTypeAndModeAndCurrency(operationType, mode, currency);
    }
}
