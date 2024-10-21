package com.itclopedia.cources.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "type")
    private String type;

    @Column(name = "datetime")
    @CreationTimestamp
    private LocalDateTime dataTime;

    @Column(name = "amount")
    private float amount;

    public Transaction(String type, User userId, float amount) {
        this.type = type;
        this.userId = userId;
        this.amount = amount;
    }

    public Transaction(int id, String type, User userId, LocalDateTime dataTime, float amount) {
        this.transactionId = id;
        this.type = type;
        this.userId = userId;
        this.dataTime = dataTime;
        this.amount = amount;
    }

}
