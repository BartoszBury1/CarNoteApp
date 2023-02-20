package com.example.carnote.model;

import java.io.Serializable;
import java.util.Date;

//Model danych pojedynczego tankowania
public class TankUpRecord implements Serializable
{
//    Data tankowania
    private Date tankUpDate;
//    Przebieg auta gdy tankujemy
    private Integer milage;
//    Zatankowane litry
    private Integer liters;
//    Kwota tankowania
    private Integer costInPln;

    public TankUpRecord(Date tankUpDate, Integer milage, Integer liters, Integer costInPln) {
        this.tankUpDate = tankUpDate;
        this.milage = milage;
        this.liters = liters;
        this.costInPln = costInPln;
    }

    public Date getTankUpDate() {
        return tankUpDate;
    }

    public void setTankUpDate(Date tankUpDate) {
        this.tankUpDate = tankUpDate;
    }

    public Integer getMilage() {
        return milage;
    }

    public void setMilage(Integer milage) {
        this.milage = milage;
    }

    public Integer getLiters() {
        return liters;
    }

    public void setLiters(Integer liters) {
        this.liters = liters;
    }

    public Integer getCostInPln() {
        return costInPln;
    }

    public void setCostInPln(Integer costInPln) {
        this.costInPln = costInPln;
    }
}
