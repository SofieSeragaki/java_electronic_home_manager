package org.example.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Employee name cannot be null")
    @Size(min = 2, max = 45, message = "Employee name must be between 2 and 45 characters")
    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @NotNull(message = "Contact cannot be null")
    @Size(min = 5, max = 45, message = "Contact must be between 5 and 45 characters")
    @Column(name = "contact", length = 45, nullable = false)
    private String contact;

    @NotNull(message = "Company cannot be null")
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Building> buildings;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    // Метод за броене на сградите
    public int getBuildingsCount() {
        return buildings != null ? buildings.size() : 0;
    }
}
