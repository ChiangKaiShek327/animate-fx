package io.github.chiangkaishek327.animatedfx;

import javafx.util.Duration;

public class AnimationUtil {
    private static Duration animationLength;

    public class AnimationConst {
    }

    public static Duration getAnimationLength() {
        return animationLength;
    }

    public static void setAnimationLength(Duration animationLength) {
        AnimationUtil.animationLength = animationLength;
    }

}
