package com.itclopedia.cources.controller;

import com.itclopedia.cources.dto.MandatoryCategoryDto;
import com.itclopedia.cources.model.User;
import com.itclopedia.cources.services.mandatory.category.MandatoryCatService;
import com.itclopedia.cources.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/mandatory/category")
@RequiredArgsConstructor
public class MandatoryCategoryController {

    private final MandatoryCatService mandatoryCatService;
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllMandatoryCategories() {
        return new ResponseEntity<>(mandatoryCatService.getAllMandatoryCats(), HttpStatus.OK);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getUserMandatoryCat(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(mandatoryCatService.printUserMandatoryCats(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> insertMandatoryCategory(@RequestBody MandatoryCategoryDto mandatoryCategoryDto) {
        User user = userService.getUserById(mandatoryCategoryDto.getUserId());
        mandatoryCatService.insertMandatoryCat(user, mandatoryCategoryDto.getName(),
                mandatoryCategoryDto.getAmount(), mandatoryCategoryDto.getDeadline());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/name/{name}")
    public ResponseEntity<?> updateMandatoryCatNameById(@PathVariable("id") int id,
                                                        @PathVariable("name") String name) {
        mandatoryCatService.updateMandatoryCatNameById(id, name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/amount/{amount}")
    public ResponseEntity<?> updateMandatoryCatNameById(@PathVariable("id") int id,
                                                        @PathVariable("amount") float amount) {
        mandatoryCatService.updateMandatoryCatAmount(id, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
