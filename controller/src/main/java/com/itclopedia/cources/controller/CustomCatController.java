package com.itclopedia.cources.controller;

import com.itclopedia.cources.model.CustomCategory;
import com.itclopedia.cources.services.custom.category.CustomCatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/custom/category")
@RequiredArgsConstructor
public class CustomCatController {

    private final CustomCatService customCatService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(customCatService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}/transactions/")
    public ResponseEntity<?> getTransactionsLastMonth(@PathVariable("id") Integer customCatId,
                                                      @RequestParam LocalDate from) {
        return new ResponseEntity<>(customCatService.getTransactionsByDate(customCatId, from),
                HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/check")
    public ResponseEntity<?> checkForUser(@PathVariable("userId") int userId) {
        if (customCatService.checkForUser(userId).isEmpty())
            return new ResponseEntity<>("No content", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(customCatService.checkForUser(userId), HttpStatus.OK);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> insertCustomCat(@PathVariable("userId") Integer userId,
                                             @RequestBody CustomCategory customCategory) {
        customCatService.insertCustomCat(userId, customCategory);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/amount/{amount}")
    public ResponseEntity<?> updateAmount(@PathVariable("id") int id,
                                          @PathVariable("amount") float amount) {
        customCatService.updateAmount(amount, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/name/{name}")
    public ResponseEntity<?> updateCustomCatNameById(@PathVariable("id") int id,
                                                     @PathVariable("name") String name) {
        customCatService.updateCustomCatNameById(id, name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{customCategoryId}/user/{userId}")
    public ResponseEntity<?> joinCustomCategory(@PathVariable("userId") Integer userId,
                                                @PathVariable("customCategoryId") Integer customCategoryId) {
        customCatService.joinCustomCategory(userId, customCategoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomCategory(@PathVariable("id") int id) {
        customCatService.deleteCustomCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
