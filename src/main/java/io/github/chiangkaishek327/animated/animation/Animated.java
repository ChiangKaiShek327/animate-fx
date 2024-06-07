package io.github.chiangkaishek327.animated.animation;

import javafx.beans.property.ObjectProperty;
import javafx.util.Duration;

public interface Animated {
    public void setDuration(Duration duration);

    public Duration getDuration();

    public ObjectProperty<Duration> durationProperty();
}
