package com.itclopedia.cources.controller;

import com.itclopedia.cources.model.OptionalCategory;
import com.itclopedia.cources.services.optional.category.OptionalCatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/category/optional")
@RequiredArgsConstructor
public class OptionalCategoryController {

    private final OptionalCatService optionalCatService;

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(optionalCatService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> findByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(optionalCatService.getByName(name), HttpStatus.OK);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactionsByDate(@PathVariable("id") int id,
                                                   @RequestParam("date") LocalDate date) {
        return new ResponseEntity<>(optionalCatService.getTransactionsByDate(id, date), HttpStatus.OK);
    }

    @PatchMapping("{id}/budget/{budget}")
    public ResponseEntity<?> updateOptionalCatBudget(@PathVariable("id") int id,
                                                     @PathVariable("budget") float budget) {
        optionalCatService.updateOptionalCatBudget(id, budget);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("{id}/name/{name}")
    public ResponseEntity<?> updateOptionalCatNameById(@PathVariable("id") int id,
                                                       @PathVariable("name") String name) {
        optionalCatService.updateOptionalCatNameById(id, name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> insertOptionalCat(@RequestBody OptionalCategory optionalCategory) {
        optionalCatService.insertOptionalCat(optionalCategory);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOptionalCat(@PathVariable("id") int id) {
        optionalCatService.deleteOptionalCat(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
