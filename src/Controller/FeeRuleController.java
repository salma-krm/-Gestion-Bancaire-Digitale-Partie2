package Controller;

import Entity.FeeRule;
import Service.Interfaces.FeeRuleService;
import utils.FeeRuleValidator;

import java.util.List;
import java.util.UUID;

public class FeeRuleController {

    private final FeeRuleService feeRuleService;

    public FeeRuleController(FeeRuleService feeRuleService) {
        this.feeRuleService = feeRuleService;
    }

    public FeeRule create(FeeRule feeRule) {
        try {
            FeeRuleValidator.validate(feeRule);
            return feeRuleService.create(feeRule);
        } catch (IllegalArgumentException e) {
            System.out.println(" Erreur de validation : " + e.getMessage());
            return null;
        }
    }

    public FeeRule update(UUID id, FeeRule feeRule) {
        if (id == null) {
            System.out.println(" ID invalide pour la mise à jour.");
            return null;
        }

        try {
            FeeRuleValidator.validate(feeRule);
            return feeRuleService.update(id, feeRule);
        } catch (IllegalArgumentException e) {
            System.out.println(" Erreur de validation : " + e.getMessage());
            return null;
        }
    }

    public FeeRule getById(UUID id) {
        if (id == null) {
            System.out.println(" ID invalide.");
            return null;
        }
        return feeRuleService.getById(id);
    }

    public List<FeeRule> getAll() {
        return feeRuleService.getAll();
    }
}
