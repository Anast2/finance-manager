package com.itclopedia.cources.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "mandatory_category")
public class MandatoryCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mandatory_category_id")
    private int mandatoryCategoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private float amount;

    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "mandatoryCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private PaymentReminder paymentReminder;

    public MandatoryCategory(int mandatoryCategoryId, String name, float amount) {
        this.mandatoryCategoryId = mandatoryCategoryId;
        this.name = name;
        this.amount = amount;
    }

    public MandatoryCategory(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }

}
