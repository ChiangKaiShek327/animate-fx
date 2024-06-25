package io.github.chiangkaishek327.animated.control.pane;

import javafx.beans.property.DoubleProperty;

public interface CanBeAnimatedLikePane {
    public double getBorderWidth();

    public void setBorderWidth();

    public DoubleProperty borderWidthProperty();
}
