package fr.soprasteria.agircarcco.sefiab.model;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "entreprise")
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entreprise_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "entreprise_id")
    private List<Dette> dettes;

    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contact> contacts; // Liste des contacts de l'entreprise

    @Column(name = "debt_Amount")
    private double debtAmount;

    public Entreprise() {
    }

    public Entreprise(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Entreprise(String name, double debtAmount) {
        this.name = name;
        this.debtAmount = debtAmount;
    }

    // Getter et Setter pour la liste des contacts
    public List<Contact> getContacts() {
        return contacts != null ? contacts : Collections.emptyList();
    }


    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDebtAmount(){
        return this.debtAmount;
    }

    public void setDebtAmount(double debtAmount){
        this.debtAmount = debtAmount;
    }

    public List<Dette> getDettes() {
        return dettes != null ? dettes : Collections.emptyList();
    }


    public void setDettes(List<Dette> dettes) {
        this.dettes = dettes;
    }

    @Override
    public String toString() {
        return "Entreprise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}


