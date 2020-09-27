package com;

public class IdealGas {
    private Pressure pressure;
    private Volume volume;
    private Temperature temperature;

    final private double R = 8.314;

    public IdealGas(double p, double v, double t) {
        pressure = new Pressure(p);
        volume = new Volume(v);
        temperature = new Temperature(t);
    }

    public String getInfo() {
        return String.format("Давление = %.2f, объем = %.2f, температура = %.2f", pressure.getValue(), volume.getValue(), temperature.getValue());
    }

    public double add(Pressure p) {
        pressure.setValue(pressure.getValue() * p.getValue());
        volume.setValue(volume.getValue() / p.getValue());
        return pressure.getValue();
    }

    public double reduce(Pressure p) {
        pressure.setValue(pressure.getValue() / p.getValue());
        volume.setValue(volume.getValue() * p.getValue());
        return pressure.getValue();
    }

    public double add(Volume v) {
        volume.setValue(volume.getValue() * v.getValue());
        pressure.setValue(pressure.getValue() / v.getValue());
        return volume.getValue();
    }

    public double reduce(Volume v) {
        volume.setValue(volume.getValue() / v.getValue());
        pressure.setValue(pressure.getValue() * v.getValue());
        return volume.getValue();
    }

    public double add(Temperature t) {
        temperature.setValue(temperature.getValue() * t.getValue());
        pressure.setValue(pressure.getValue() * t.getValue());
        return temperature.getValue();
    }

    public double reduce(Temperature t) {
        temperature.setValue(temperature.getValue() / t.getValue());
        pressure.setValue(pressure.getValue() / t.getValue());
        return temperature.getValue();
    }

    public double getPressure() {
        return pressure.getValue();
    }

    public double getVolume() {
        return volume.getValue();
    }

    public double getTemperature() {
        return temperature.getValue();
    }

}

class Pressure {
    private double value;

    public Pressure(double value) {
        this.value = value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}

class Volume {
    private double value;

    public Volume(double value) {
        this.value = value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}

class Temperature {
    private double value;

    public Temperature(double value) {
        this.value = value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
