package io.github.chiangkaishek327.animated.animation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

public abstract class AnimationGroup<N extends Animated> {

    protected ObjectProperty<Duration> durationProperty = new SimpleObjectProperty<Duration>(Duration.millis(100));
    protected ObjectProperty<N> nodeProperty = new SimpleObjectProperty<>();
    protected ReadOnlyObjectWrapper<N> roowNode = new ReadOnlyObjectWrapper<N>();
    protected ReadOnlyObjectProperty<N> roopNode = roowNode.getReadOnlyProperty();
    protected DoubleProperty changeScaleProperty = new SimpleDoubleProperty(3);

    public AnimationGroup() {
        roowNode.bind(nodeProperty);
    }

    public N getNode() {

        return nodeProperty.getValue();
    };

    public ReadOnlyObjectProperty<N> nodeProperty() {

        return roopNode;
    };

    public Duration getDuration() {
        return durationProperty.getValue();
    };

    public void setDuration(Duration duration) {
        durationProperty.setValue(duration);
    };

    public ObjectProperty<Duration> durationProperty() {
        return durationProperty;
    };

    public double getChangeScale() {
        return changeScaleProperty.getValue();
    };

    public void setChangeScale(double da) {
        changeScaleProperty.setValue(da);
    };

    public DoubleProperty changeScaleProperty() {
        return changeScaleProperty;
    };
}
