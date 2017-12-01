package com.springbootapp.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class BloodPackage extends BaseEntity {
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acceptanceDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date shelfLife;

    private BloodType bloodType;

    @NotNull
    private float capacityInLiters;

    public BloodPackage() {
    }

    public BloodPackage(Date acceptanceDate, Date expiryDate, BloodType bloodType, float capacityInLiters) {
        this.acceptanceDate = acceptanceDate;
        this.shelfLife = expiryDate;
        this.bloodType = bloodType;
        this.capacityInLiters = capacityInLiters;
    }

    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public Date getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(Date shelfLife) {
        this.shelfLife = shelfLife;
    }


    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public float getCapacityInLiters() {
        return capacityInLiters;
    }

    public void setCapacityInLiters(float capacityInLiters) {
        this.capacityInLiters = capacityInLiters;
    }

}
