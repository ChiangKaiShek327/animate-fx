package io.github.chiangkaishek327.animated.control.pane;

import io.github.chiangkaishek327.animated.animation.AnimationGroup;
import io.github.chiangkaishek327.animated.control.button.ButtonAnimationGroup;
import io.github.chiangkaishek327.animated.util.OtherUtil;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

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