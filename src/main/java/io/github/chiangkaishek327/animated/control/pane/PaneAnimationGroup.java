package io.github.chiangkaishek327.animated.control.pane;

import io.github.chiangkaishek327.animated.animation.AnimationGroup;
import io.github.chiangkaishek327.animated.util.OtherUtil;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;

/**
 * PaneAnimationGroup
 */
public abstract class PaneAnimationGroup extends AnimationGroup<AnimatedPane> {
    public enum PaneAnimationDirection {
        BOTTOM_TO_TOP, TOP_TO_BOTTOM, LEFT_TO_RIGHT, RIGHT_TO_LEFT, NONE
    }

    public enum PaneAnimationType {

        PAT_TRANSLATE(PaneTranslateAnimationGroup.class), PAT_OAT(PaneOATAnimationGroup.class);

        public final Class<? extends PaneAnimationGroup> clazz;

        PaneAnimationType(Class<? extends PaneAnimationGroup> clazz) {
            this.clazz = clazz;
        }
    }

    private DoubleProperty borderWidth = new SimpleDoubleProperty(0), height = new SimpleDoubleProperty(0),
            width = new SimpleDoubleProperty();

    public double getBorderWidth() {
        return borderWidth.getValue();
    };

    public void setBorderWidth(double da) {
        borderWidth.setValue(da);
    };

    public DoubleProperty borderWidthProperty() {
        return borderWidth;
    };

    public double getWidth() {
        return width.getValue();
    };

    public void setWidth(double da) {
        width.setValue(da);
    };

    public DoubleProperty widthProperty() {
        return width;
    };

    public double getHeight() {
        return height.getValue();
    };

    public void setHeight(double da) {
        height.setValue(da);
    };

    public DoubleProperty heightProperty() {
        return height;
    };

    protected void setVectorVisible(Node from, Node to) {
        new Thread(() -> {
            to.setVisible(true);
            OtherUtil.delayConviently((long) getDuration().toMillis());
            from.setVisible(false);
        }).start();
        ;

    }

    public abstract void turn(PaneAnimationDirection direction, Node from, Node to);
}