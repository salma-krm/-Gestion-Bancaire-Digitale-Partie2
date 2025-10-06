package Service.Interfaces;

import Entity.Enum.Mode;
import Entity.Enum.OperationType;
import Entity.FeeRule;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

public interface FeeRuleService {
    FeeRule create(FeeRule feeRule);
    FeeRule update(UUID id, FeeRule feeRule);
    FeeRule getById(UUID id);
    List<FeeRule> getAll();
    FeeRule getFeeRule(OperationType operationType, Mode mode, Currency currency);

}
