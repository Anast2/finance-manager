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
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "userSet", fetch = FetchType.EAGER)
    private Set<CustomCategory> customCategorySet = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "userSet", fetch = FetchType.EAGER)
    private Set<FinanceGoals> financeGoalsSet = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "user_payment_reminder",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_reminder_id"))
    private Set<PaymentReminder> paymentReminders = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(String name, String surname, String password, String email, Set<CustomCategory> customCategorySet, Set<FinanceGoals> financeGoalsSet) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.customCategorySet = customCategorySet;
        this.financeGoalsSet = financeGoalsSet;
    }

    public User(String name, String surname, String password, String email) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }

    public User(int id, String name, String surname, String password, String email, Set<CustomCategory> customCategorySet) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.customCategorySet = customCategorySet;
    }

    public void addCustomCategory(CustomCategory customCategory) {
        this.customCategorySet.add(customCategory);
        customCategory.getUserSet().add(this);
    }

    @Override
    public String toString() {
        return "User {userId = " + this.id + ", name = " + this.name + ", surname = " + this.surname + ", email = " + this.email + ", password = " + this.password + "}";
    }

}
