package io.github.chiangkaishek327.animated.util;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

public class ValueTransition extends Transition {

    protected ObjectProperty<DoubleProperty> property = new SimpleObjectProperty<>(new SimpleDoubleProperty());
    protected ObjectProperty<Duration> duration = new SimpleObjectProperty<>(Duration.millis(100));
    protected DoubleProperty from = new SimpleDoubleProperty(0);
    protected DoubleProperty by = new SimpleDoubleProperty(0);
    protected DoubleProperty to = new SimpleDoubleProperty(0);

    public ValueTransition() {

    }

    public ValueTransition(DoubleProperty property) {

        this.property.setValue(property);
    }

    public ValueTransition(Duration duration, DoubleProperty property) {
        this.duration.setValue(duration);
        this.property.setValue(property);
    }

    @Override
    public void play() {
        Timeline timeline = new Timeline();
        Interpolator interpolator = getInterpolator();
        timeline.getKeyFrames().add(new KeyFrame(duration.get(), new KeyValue[] {
                new KeyValue(property.get(), from.get(), interpolator),
                new KeyValue(property.get(), by.get(), interpolator),
                new KeyValue(property.get(), to.get(), interpolator),
        }));
        timeline.play();
    }

    public Duration getDuration() {
        return duration.getValue();
    };

    public void setDuration(Duration duration) {
        this.duration.setValue(duration);
    };

    public ObjectProperty<Duration> durationProperty() {
        return duration;
    };

    public DoubleProperty getProperty() {
        return property.getValue();
    };

    public void setProperty(DoubleProperty property) {
        this.property.setValue(property);
    };

    public ObjectProperty<DoubleProperty> propertyProperty() {
        return property;
    };

    public void setFrom(double from) {
        this.from.setValue(from);
    }

    public double getFrom() {
        return this.from.getValue();
    }

    public DoubleProperty fromProperty() {
        return from;
    }

    public void setTo(double to) {
        this.to.setValue(to);
    }

    public double getTo() {
        return this.to.getValue();
    }

    public DoubleProperty toProperty() {
        return to;
    }

    public void setBy(double by) {
        this.by.setValue(by);
    }

    public double getBy() {
        return this.by.getValue();
    }

    public DoubleProperty byProperty() {
        return by;
    }

    @Override
    protected void interpolate(double frac) {
    }
}
