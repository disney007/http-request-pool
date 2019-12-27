package org.hrp.management.controllers;

import lombok.RequiredArgsConstructor;
import org.hrp.lib.entities.RuleEntity;
import org.hrp.lib.repositories.RuleRepository;
import org.hrp.lib.services.RuleService;
import org.hrp.management.models.CreateRuleRecord;
import org.hrp.management.models.EditRuleRecord;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class RuleController {

    final RuleService ruleService;
    final RuleRepository repository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RuleEntity createRule(@RequestBody @Valid CreateRuleRecord record) {
        return ruleService.createRule(record.getPath(), record.getMethod(), record.getCondition(), record.getReturnHttpCode(),
                record.getReturnJson());
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public void editRule(@RequestBody @Valid EditRuleRecord record) {
        ruleService.editRule(record.getId(), record.getPath(), record.getMethod(), record.getCondition(), record.getReturnHttpCode(),
                record.getReturnJson(), record.getIsEnabled());
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
