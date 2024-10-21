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
@Entity
@Table(name = "payment_reminder")
public class PaymentReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_reminder_id")
    private int paymentReminderId;

    @Column(name = "date")
    private LocalDate date;

    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "mandatory_category_id")
    private MandatoryCategory mandatoryCategory;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "paymentReminders")
    @JsonIgnore
    private Set<User> userSet = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "expense_id")
    @JsonIgnore
    private Expense expense;

    public PaymentReminder(int paymentReminderId, LocalDate date, MandatoryCategory mandatoryCategory) {
        this.paymentReminderId = paymentReminderId;
        this.date = date;
        this.mandatoryCategory = mandatoryCategory;
    }

    public PaymentReminder(LocalDate date, MandatoryCategory mandatoryCategory) {
        this.date = date;
        this.mandatoryCategory = mandatoryCategory;
    }

    @Override
    public String toString() {
        return "PaymentReminder {paymentReminderId = " + this.paymentReminderId + ", date = " + this.date
                + ", mandatoryCategory = " + this.mandatoryCategory.getName() + "}";
    }

    public void addUser(User user) {
        userSet.add(user);
        user.getPaymentReminders().add(this);
    }

}