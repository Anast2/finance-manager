package com.itclopedia.cources.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "custom_category")
public class CustomCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_category_id")
    private int customCategoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private float amount;

    @Column(name = "curr_amount")
    private float currAmount;

    @ToString.Exclude
    @OneToMany(mappedBy = "customCategory",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Expense> expenseSet = new HashSet<>();

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_custom_category",
            joinColumns = @JoinColumn(name = "custom_category_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> userSet = new HashSet<>();

    public CustomCategory(String name, float amount, float currAmount, Set<User> userSet) {
        this.name = name;
        this.amount = amount;
        this.currAmount = currAmount;
        this.userSet = userSet;
    }

    public CustomCategory(String name, float amount, float currAmount) {
        this.name = name;
        this.amount = amount;
        this.currAmount = currAmount;
    }

    public CustomCategory(int customCategoryId, String name, float amount,
                          float currAmount, Set<User> userSet) {
        this.name = name;
        this.amount = amount;
        this.customCategoryId = customCategoryId;
        this.currAmount = currAmount;
        this.userSet = userSet;
    }

    public void addUser(User user) {
        this.getUserSet().add(user);
        user.getCustomCategorySet().add(this);
    }

    public void addExpense(Expense expense){
        expense.setCustomCategory(this);
        this.expenseSet.add(expense);
    }

}
