package com.itclopedia.cources.controller;

import com.itclopedia.cources.dto.UserRegistrationDto;
import com.itclopedia.cources.services.custom.category.CustomCatService;
import com.itclopedia.cources.services.finance.goals.FinanceGoalsService;
import com.itclopedia.cources.services.payment.reminders.PaymentRemindService;
import com.itclopedia.cources.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CustomCatService customCatService;
    private final FinanceGoalsService financeGoalsService;
    private final PaymentRemindService paymentRemindService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(),
                HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("userName/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(userService.getUserByName(name), HttpStatus.OK);
    }

    @GetMapping("{id}/customCategory/all")
    public ResponseEntity<?> getAllCustomCatForUser(@PathVariable("id") Integer userId) {
        return new ResponseEntity<>(customCatService.getAllForUser(userId), HttpStatus.OK);
    }

    @GetMapping("{id}/financeGoals/all")
    public ResponseEntity<?> getAllFinanceGoalsForUser(@PathVariable("id") Integer userId) {
        return new ResponseEntity<>(financeGoalsService.getAllForUser(userId), HttpStatus.OK);
    }

    @GetMapping("/paymentReminders/all/user/{userId}")
    public ResponseEntity<?> getAllReminders(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(paymentRemindService.getCurrentRemindersForUser(userId), HttpStatus.OK);
    }

    @PatchMapping("/{id}/name?name")
    public ResponseEntity<?> updateUserName(@RequestParam("name") String name,
                                            @PathVariable("id") Integer id) {
        userService.updateUserName(id, name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/email?email")
    public ResponseEntity<?> updateUserEmail(@RequestParam("email") String email,
                                             @PathVariable("id") Integer id) {
        userService.updateUserEmail(id, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/password?password")
    public ResponseEntity<?> updateUserPassword(@RequestParam("password") String password,
                                                @PathVariable("id") Integer id) {
        userService.updateUserPassword(id, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{userId}/finance/goal/{financeGoalId}")
    public ResponseEntity<?> deleteFinanceGoal(@PathVariable("userId") Integer userId,
                                               @PathVariable("financeGoalId") Integer financeGoalId) {
        userService.deleteFinanceGoal(userId, financeGoalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{userId}/custom/category/{customCategoryId}")
    public ResponseEntity<?> deleteCustomCategory(@PathVariable("userId") Integer userId,
                                                  @PathVariable("customCategoryId") Integer customCategoryId) {
        userService.deleteCustomCategory(userId, customCategoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{userId}/roles/{roleName}")
    public ResponseEntity<?> addRole(@PathVariable("userId") Integer userId,
                                     @PathVariable("roleName") String roleName) {
        userService.addRole(userId, roleName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        userService.insertNewUser(userRegistrationDto.getUsername(), userRegistrationDto.getEmail(),
                userRegistrationDto.getPassword());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
