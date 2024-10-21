package com.itclopedia.cources.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "finance_goals")
public class FinanceGoals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "finance_goal_id")
    private int financeGoalId;

    @Column(name = "description")
    private String description;

    @Column(name = "start")
    private LocalDate start;

    @Column(name = "end")
    private LocalDate end;

    @Column(name = "amount")
    private float amount;

    @Column(name = "curr_amount")
    private float currAmount;

    @ToString.Exclude
    @OneToMany(mappedBy = "financeGoals",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Expense> expenseSet = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_finance_goal",
            joinColumns = @JoinColumn(name = "finance_goal_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> userSet = new HashSet<>();

    public FinanceGoals(int id, String description, LocalDate start, LocalDate end, float amount, float currAmount) {
        this.financeGoalId = id;
        this.description = description;
        this.start = start;
        this.end = end;
        this.amount = amount;
        this.currAmount = currAmount;
    }

    public FinanceGoals(String description, LocalDate start, LocalDate end, float amount, float currAmount) {
        this.description = description;
        this.start = start;
        this.end = end;
        this.amount = amount;
        this.currAmount = currAmount;
    }

    public FinanceGoals(String description, LocalDate start, LocalDate end, float amount, float currAmount, Set<User> userSet) {
        this.description = description;
        this.start = start;
        this.end = end;
        this.amount = amount;
        this.currAmount = currAmount;
        this.userSet = userSet;
    }

    public void addUser(User user) {
        this.userSet.add(user);
        user.getFinanceGoalsSet().add(this);
    }

    public void addExpense(Expense expense){
        expense.setFinanceGoals(this);
        this.expenseSet.add(expense);
    }

}

