package io.github.chiangkaishek327.animatedfx.utils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class PhaseProperties {
    private DoubleProperty fromProperty = new SimpleDoubleProperty(0);
    private DoubleProperty byProperty = new SimpleDoubleProperty(0);
    private DoubleProperty toProperty = new SimpleDoubleProperty(0);

    public DoubleProperty fromProperty() {
        return fromProperty;
    }

    public DoubleProperty byProperty() {
        return byProperty;
    }

    public DoubleProperty toProperty() {
        return toProperty;
    }

    public void setFrom(double from) {
        fromProperty.setValue(from);
    }

    public void setBy(double by) {
        byProperty.setValue(by);
    }

    public void setTo(double to) {
        toProperty.setValue(to);
    }

    public void setAll(double all) {
        toProperty.setValue(all);
        byProperty.setValue(all);
        fromProperty.setValue(all);
    }

    public double getFrom() {
        System.out.println(fromProperty.getValue());

        return fromProperty.getValue();
    }

    public double getBy() {
        return byProperty.getValue();
    }

    public double getTo() {
        System.out.println(toProperty.getValue());
        return toProperty.getValue();
    }
}
