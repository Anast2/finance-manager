package com.itclopedia.cources.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private int expenseId;

    @ManyToOne
    @JoinColumn(name = "optional_category_id")
    @JsonIgnore
    private OptionalCategory optionalCategory;

    @ManyToOne
    @JoinColumn(name = "custom_category_id")
    @JsonIgnore
    private CustomCategory customCategory;

    @ManyToOne
    @JoinColumn(name = "finance_goal_id")
    @JsonIgnore
    private FinanceGoals financeGoals;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")
    private Transaction transaction;

    public Expense(Transaction transaction) {
        this.transaction = transaction;
    }

}
