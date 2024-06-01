package io.github.chiangkaishek327.animated.control.button;

import io.github.chiangkaishek327.animated.animation.AnimationGroup;

public abstract class ButtonAnimationGroup extends AnimationGroup<AnimatedButton> {

    public enum ButtonAnimationType {
        BAT_SCALE(ButtonScaleAnimationGroup.class), BAT_TRANSLATE(ButtonTranslateAnimationGroup.class);

        public Class<? extends ButtonAnimationGroup> clazz;

        ButtonAnimationType(Class<? extends ButtonAnimationGroup> clazz) {
            this.clazz = clazz;
        }
    }

}
