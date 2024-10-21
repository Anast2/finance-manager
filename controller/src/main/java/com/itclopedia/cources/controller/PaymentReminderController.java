package com.itclopedia.cources.controller;

import com.itclopedia.cources.services.payment.reminders.PaymentRemindService;
import com.itclopedia.cources.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/paymentReminders")
@RequiredArgsConstructor
public class PaymentReminderController {

    private final PaymentRemindService paymentRemindService;
    private final UserService userService;

    @GetMapping("/current/user/{userId}")
    public ResponseEntity<?> getCurrentRemindersForUser(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(paymentRemindService.getCurrentRemindersForUser(userId), HttpStatus.OK);
    }

    @GetMapping("/current/all")
    public ResponseEntity<?> getCurrentReminders() {
        return new ResponseEntity<>(paymentRemindService.getAllCurrent(), HttpStatus.OK);
    }

    @PatchMapping("/{paymentReminderId}/user/{userId}")
    public ResponseEntity<?> joinPaymentReminder(@PathVariable("userId") Integer userId,
                                                 @PathVariable("paymentReminderId") Integer paymentReminderId) {
        paymentRemindService.joinPaymentReminder(userId, paymentReminderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{paymentReminderId}/user/{userId}")
    public void addPaymentReminder(@PathVariable("userId") Integer userId,
                                   @PathVariable("paymentReminderId") Integer paymentReminderId) {
        userService.addPaymentReminder(userId, paymentReminderId);
    }

    @PatchMapping("/{paymentReminderId}/mandatory/category/{id}")
    public ResponseEntity<?> updateMandatoryCatId(@PathVariable("id") int mandatoryCategoryId, @PathVariable("paymentReminderId") int paymentReminderId) {
        paymentRemindService.updateMandatoryCatId(mandatoryCategoryId, paymentReminderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/{mandatoryCatId}/user/{userId}/deadline/{deadline}")
    public ResponseEntity<?> createPaymentReminder(@PathVariable("userId") Integer userId,
                                                   @PathVariable("mandatoryCatId") int mandatoryCategoryId,
                                                   @PathVariable("deadline") LocalDate deadline) {
        paymentRemindService.createPaymentReminder(userId, mandatoryCategoryId, deadline);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
