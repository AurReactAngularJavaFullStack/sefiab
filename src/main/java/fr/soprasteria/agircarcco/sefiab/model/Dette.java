package fr.soprasteria.agircarcco.sefiab.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dette")
public class Dette {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dette_id")
    private Long id;

    @Column(name = "amount")
    private double amount;

    @ManyToOne
    @JoinColumn(name = "entreprise_id")
    private Entreprise entreprise;

    public Dette(double amount, Entreprise entreprise) {
        this.amount = amount;
        this.entreprise = entreprise;
    }

    public Dette() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    @Override
    public String toString() {
        return "Dette{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}

