package test02.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private double balance;

    @Version
    private int version;

    @Column(nullable = false)
    private int transactionCounter = 0;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions = new ArrayList<>();

    public User(Long id, String name, String surname, double balance) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.balance = balance;
    }

    public User(String name, String surname, double balance) {
        this.name = name;
        this.surname = surname;
        this.balance = balance;
    }
}

