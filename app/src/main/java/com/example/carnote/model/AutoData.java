package com.example.carnote.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model danych auta
**/
public class AutoData implements  Serializable
{
//    Model np Golf
    private String model;

//    Marka np VolksWagen
    private String make;

//    Kolor auta
    private String color;
//    Tankowania
    private List<TankUpRecord> tankUpRecord;

    public AutoData(String model, String make, String color)
    {
        this.model = model;
        this.make = make;
        this.color = color;
        tankUpRecord = new ArrayList<>();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<TankUpRecord> getTankUpRecord() {
        return tankUpRecord;
    }

    public void setTankUpRecord(List<TankUpRecord> tankUpRecord) {
        this.tankUpRecord = tankUpRecord;
    }

    @NonNull
    @Override
    public String toString() {
        return make + " " + model +" "+ color +" ";
    }
}
