package org.example.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "building", uniqueConstraints = {
        @UniqueConstraint(columnNames = "address", name = "unique_address_constraint")
})
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Address cannot be null")
    @Size(min = 5, max = 45, message = "Address must be between 5 and 45 characters")
    @Column(length = 45, nullable = false)
    private String address;

    @Min(value = 1, message = "Floors must be at least 1")
    @Column(nullable = false)
    private int floors;

    @Min(value = 1, message = "Apartments count must be at least 1")
    @Column(name = "apartments_count", nullable = false)
    private int apartmentsCount;

    @Positive(message = "Total area must be positive")
    @Column(name = "total_area", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private double totalArea;

    @PositiveOrZero(message = "Common area must be zero or positive")
    @Column(name = "common_area", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private double commonArea;

    @PositiveOrZero(message = "Monthly fee must be zero or positive")
    @Column(name = "monthly_fee", nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private double monthlyFee;

    @NotNull(message = "Company cannot be null")
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apartment> apartments;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public int getApartmentsCount() {
        return apartmentsCount;
    }

    public void setApartmentsCount(int apartmentsCount) {
        this.apartmentsCount = apartmentsCount;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }

    public double getCommonArea() {
        return commonArea;
    }

    public void setCommonArea(double commonArea) {
        this.commonArea = commonArea;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }
}
