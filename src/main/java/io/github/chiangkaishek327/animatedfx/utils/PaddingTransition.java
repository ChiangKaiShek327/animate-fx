package io.github.chiangkaishek327.animatedfx.utils;

import javafx.animation.Transition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class PaddingTransition extends Transition {
    PhaseProperties bottomPhaseProperties = new PhaseProperties();
    PhaseProperties topPhaseProperties = new PhaseProperties();
    PhaseProperties leftPhaseProperties = new PhaseProperties();
    PhaseProperties rightPhaseProperties = new PhaseProperties();
    ObjectProperty<Region> regionProperty = new SimpleObjectProperty<>();

    ObjectProperty<Duration> durationProperty = new SimpleObjectProperty<>();

    public PaddingTransition(Region region, Duration duration) {
        regionProperty.setValue(region);
        durationProperty.setValue(duration);
        durationProperty.addListener((ob, o, n) -> setCycleDuration(n));

    }

    @Override
    protected void interpolate(double frac) {
        double top = topPhaseProperties.getFrom() + (topPhaseProperties.getTo() * frac);

        double bottom = topPhaseProperties.getFrom() + (topPhaseProperties.getTo() * frac);

        double left = topPhaseProperties.getFrom() + (topPhaseProperties.getTo() * frac);

        double right = topPhaseProperties.getFrom() + (topPhaseProperties.getTo() * frac);
        regionProperty.getValue().setStyle("-fx-padding: %f %f %f %f;".formatted(top, left, bottom, right));

    }

    public ObjectProperty<Region> regionProperty() {
        return regionProperty;
    }

    public ObjectProperty<Duration> durationProperty() {
        return durationProperty;
    }

    public void setDuration(Duration duration) {
        durationProperty.setValue(duration);
    }

    public Duration getDuration() {
        return durationProperty.getValue();
    }

    public void setRegion(Region region) {
        regionProperty.setValue(region);
    }

    public Region getRegion() {
        return regionProperty.getValue();
    }

    public PhaseProperties getBottomPhaseProperties() {
        return bottomPhaseProperties;
    }

    public PhaseProperties getTopPhaseProperties() {
        return topPhaseProperties;
    }

    public PhaseProperties getLeftPhaseProperties() {
        return leftPhaseProperties;
    }

    public PhaseProperties getRightPhaseProperties() {
        return rightPhaseProperties;
    }

}
