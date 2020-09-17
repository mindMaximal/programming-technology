package com.company;

public class IdealGas {
    private Pressure pressure;
    private Volume volume;
    private Temperature temperature;

    final private double R = 8.314;

    public IdealGas(double p, double V, double T) {
        pressure = new Pressure(p);
        volume = new Volume(V);
        temperature = new Temperature(T);
    }

    public String getInfo() {
        return String.format("Текущее состояние системы: давление = %s, объем = %s, температура = %s", pressure.getValue(), volume.getValue(), temperature.getValue());
    }

    public double addP(double multiplicator) {
        pressure.setValue(pressure.getValue() * multiplicator);
        volume.setValue(volume.getValue() / multiplicator);
        return pressure.getValue();
    }

    public double reduceP(double multiplicator) {
        pressure.setValue(pressure.getValue() / multiplicator);
        volume.setValue(volume.getValue() * multiplicator);
        return pressure.getValue();
    }

    public double addV(double multiplicator) {
        volume.setValue(volume.getValue() * multiplicator);
        pressure.setValue(pressure.getValue() / multiplicator);
        return volume.getValue();
    }

    public double reduceV(double multiplicator) {
        volume.setValue(volume.getValue() / multiplicator);
        pressure.setValue(pressure.getValue() * multiplicator);
        return volume.getValue();
    }

    public double addT(double multiplicator) {
        temperature.setValue(temperature.getValue() * multiplicator);
        pressure.setValue(pressure.getValue() * multiplicator);
        return temperature.getValue();
    }

    public double reduceT(double multiplicator) {
        temperature.setValue(temperature.getValue() / multiplicator);
        pressure.setValue(pressure.getValue() / multiplicator);
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
