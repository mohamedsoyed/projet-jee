package com.example.auction;



import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BIDid;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Enchere enchere;

    private Long userId;
    private double amount;
    private LocalDateTime timestamp;

    // Getters et setters
    public Long getBIDid() {
        return BIDid;
    }

    public void setiBIDd(Long id) {
        this.BIDid = id;
    }

    public Enchere getEnchere() {
        return enchere;
    }

    public void setEnchere(Enchere enchere) {
        this.enchere = enchere;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
