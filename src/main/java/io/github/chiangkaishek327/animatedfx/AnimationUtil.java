package io.github.chiangkaishek327.animatedfx;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;

public class AnimationUtil {
    private static Duration animationLength = Duration.millis(100);
    private static DoubleProperty range = new SimpleDoubleProperty(1.2);

    public static Duration getAnimationLength() {

        return animationLength;
    }

    public static void setAnimationLength(Duration animationLength) {
        AnimationUtil.animationLength = animationLength;
    }

    public static DoubleProperty getRangeValue() {
        return range;
    }

    public static double getRange() {
        return range.getValue();
    }

    public static void setRange(double range) {
        AnimationUtil.range.setValue(range);
    }

}
