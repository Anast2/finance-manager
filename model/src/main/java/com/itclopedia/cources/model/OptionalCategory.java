package com.itclopedia.cources.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "optional_category")
public class OptionalCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optional_category_id")
    private int optionalCategoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private float amount;

    @Column(name = "curr_amount")
    private float currAmount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "optionalCategory",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Expense> expenseSet = new HashSet<>();

    public OptionalCategory(String name, float amount, float currAmount) {
        this.name = name;
        this.amount = amount;
        this.currAmount = currAmount;
    }

    public OptionalCategory(int optionalCategoryId, String name, float amount, float currAmount) {
        this.optionalCategoryId = optionalCategoryId;
        this.name = name;
        this.amount = amount;
        this.currAmount = currAmount;
    }

    public void addExpense(Expense expense){
        expense.setOptionalCategory(this);
        this.expenseSet.add(expense);
    }

}
