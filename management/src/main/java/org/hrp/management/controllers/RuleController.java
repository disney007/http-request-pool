package org.hrp.management.controllers;

import lombok.RequiredArgsConstructor;
import org.hrp.lib.entities.RuleEntity;
import org.hrp.lib.models.RuleRecord;
import org.hrp.lib.repositories.RuleRepository;
import org.hrp.lib.services.RuleService;
import org.hrp.management.models.CreateRuleRecord;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class RuleController {

    final RuleService ruleService;
    final RuleRepository repository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public RuleEntity createRule(@RequestBody @Valid CreateRuleRecord record) {
        RuleRecord ruleRecord = new RuleRecord(UUID.randomUUID().toString(), record.getPath(), record.getMethod(),
                record.getCondition(), record.getReturnHttpCode(), record.getReturnJson(), record.getIsEnabled());
        return ruleService.createRule(ruleRecord);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void editRule(@PathVariable("id") @Valid @NotNull String id,
                         @RequestBody @Valid CreateRuleRecord record) {
        RuleRecord ruleRecord = new RuleRecord(id, record.getPath(), record.getMethod(),
                record.getCondition(), record.getReturnHttpCode(), record.getReturnJson(), record.getIsEnabled());
        ruleService.editRule(ruleRecord);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<RuleEntity> findAllRules() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable("id") @Valid @NotNull String id) {
        repository.deleteById(id);
    }
}
