package io.github.chiangkaishek327.animated.control.pane;

import io.github.chiangkaishek327.animated.animation.AnimationGroup;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.BorderPane;

/**
 * PaneAnimationGroup
 */
public abstract class PaneAnimationGroup extends AnimationGroup<AnimatedPane> {
    public enum PaneAnimationDirection {
        BOTTOM_TO_TOP, TOP_TO_BOTTOM, LEFT_TO_RIGHT, RIGHT_TO_LEFT, NONE
    }

    private DoubleProperty borderWidthProperty = new SimpleDoubleProperty(0);

    public double getBorderWidth() {
        return borderWidthProperty.getValue();
    };

    public void setBorderWidth(double da) {
        borderWidthProperty.setValue(da);
    };

    public DoubleProperty borderWidthProperty() {
        return borderWidthProperty;
    };

    public abstract void turn(PaneAnimationDirection direction, BorderPane from, BorderPane to);
}