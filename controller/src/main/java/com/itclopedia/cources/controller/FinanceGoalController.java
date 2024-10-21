package com.itclopedia.cources.controller;

import com.itclopedia.cources.model.FinanceGoals;
import com.itclopedia.cources.services.finance.goals.FinanceGoalsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/finance/goals")
@RequiredArgsConstructor
public class FinanceGoalController {

    private final FinanceGoalsService financeGoalsService;

    @PatchMapping("/{id}/budget")
    public ResponseEntity<?> updateFinanceGoalBudget(@PathVariable("id") int id,
                                                     @RequestParam("budget") float amount) {
        financeGoalsService.updateFinanceGoalBudget(id, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{financeGoalId}/user/{userId}")
    public ResponseEntity<?> joinFinanceGoal(@PathVariable("userId") int userId,
                                             @PathVariable("financeGoalId") int financeGoalId) {
        financeGoalsService.joinFinanceGoal(userId, financeGoalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> insertFinanceGoal(@PathVariable("userId") Integer userId,
                                               @RequestBody FinanceGoals financeGoal) {
        financeGoalsService.insertFinanceGoal(userId, financeGoal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFinanceGoal(@PathVariable("id") int id) {
        financeGoalsService.deleteFinanceGoal(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
