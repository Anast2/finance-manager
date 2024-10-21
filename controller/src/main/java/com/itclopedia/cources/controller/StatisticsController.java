package com.itclopedia.cources.controller;

import com.itclopedia.cources.services.custom.category.CustomCatService;
import com.itclopedia.cources.services.finance.goals.FinanceGoalsService;
import com.itclopedia.cources.services.payment.reminders.PaymentRemindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final FinanceGoalsService financeGoalsService;
    private final CustomCatService customCatService;
    private final PaymentRemindService paymentRemindService;

    @GetMapping("/financeGoals/user/{userId}")
    public ResponseEntity<?> getFinanceGoals(@PathVariable("userId") int userId) {
        return new ResponseEntity<>(financeGoalsService.getStatisticsForUser(userId), HttpStatus.OK);
    }

    @GetMapping("/customCategory/user/{userId}")
    public ResponseEntity<?> getCustomCategory(@PathVariable("userId") int userId) {
        return new ResponseEntity<>(customCatService.checkForUser(userId), HttpStatus.OK);
    }

    @GetMapping("paymentReminders/user/{userId}")
    public ResponseEntity<?> getPaymentReminders(@PathVariable("userId") int userId) {
        return new ResponseEntity<>(paymentRemindService.getCurrentRemindersForUser(userId), HttpStatus.OK);
    }

}
