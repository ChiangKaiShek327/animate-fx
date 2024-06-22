package io.github.chiangkaishek327.animated.control.button;

import io.github.chiangkaishek327.animated.animation.Animated;
import io.github.chiangkaishek327.animated.control.button.ButtonAnimationGroup.ButtonAnimationType;
import io.github.chiangkaishek327.animated.util.AnimationGroupLoader;
import io.github.chiangkaishek327.animated.util.OtherUtil;
import io.github.chiangkaishek327.animated.util.ReadOnlyPropertyCreator;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class AnimatedButton extends Button implements Animated {
    private ObjectProperty<ButtonAnimationGroup> animationGroupProperty = new SimpleObjectProperty<>();
    private ObjectProperty<ButtonAnimationType> animationTypeProperty = new SimpleObjectProperty<>();
    private DoubleProperty changeScaleProperty = new SimpleDoubleProperty(3);
    private ObjectProperty<Duration> durationProperty = new SimpleObjectProperty<>(Duration.millis(100));
    private ReadOnlyObjectProperty<ButtonAnimationGroup> readOnlyAnimationGroupProperty = ReadOnlyPropertyCreator
            .createObjectProperty(animationGroupProperty);
    AnimationGroupLoader<AnimatedButton> animationGroupLoader = new AnimationGroupLoader<AnimatedButton>(
            AnimatedButton.this);

    public AnimatedButton() {
        this(OtherUtil.DEFAULT_TEXT);
    }

    /**
     * 
     * @param text text this button show
     */
    public AnimatedButton(String text) {
        super();
        setFocusTraversable(false);
        animationTypeProperty.addListener((ob, o, n) -> {
            try {
                ButtonAnimationGroup newAnimationGroup = ((ButtonAnimationGroup) animationGroupLoader.load(n.clazz));
                newAnimationGroup.durationProperty().bind(durationProperty);
                newAnimationGroup.changeScaleProperty().bind(changeScaleProperty);

                ButtonAnimationGroup old = animationGroupProperty.getValue();
                if (old != null) {
                    old.changeScaleProperty().unbind();
                    old.durationProperty().unbind();
                }
                animationGroupProperty.setValue(newAnimationGroup);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        setAnimationType(ButtonAnimationType.BAT_TRANSLATE);
        setText(text);
    }

    /**
     * 
     * @param animationType animation type will be set
     *                      <p>
     *                      tips: you can use animationgroup to animate some
     *                      component
     *                      <p>
     *                      like: new XXXXAnimationGroup(XXXXControl);
     */
    public void setAnimationType(ButtonAnimationType animationType) {
        animationTypeProperty.setValue(animationType);
    }

    public ButtonAnimationType getAnimationType() {
        return animationTypeProperty.getValue();
    }

    public ObjectProperty<ButtonAnimationType> animationTypeProperty() {
        return animationTypeProperty;
    }

    public ButtonAnimationGroup getAnimationGroup() {
        return animationGroupProperty.getValue();
    }

    public ReadOnlyObjectProperty<ButtonAnimationGroup> animationGroupProperty() {
        return readOnlyAnimationGroupProperty;
    }

    public double getChangeScale() {
        return changeScaleProperty.getValue();
    };

    public void setChangeScale(double da) {
        changeScaleProperty.setValue(da);
    };

    public DoubleProperty changeScaleProperty() {
        return changeScaleProperty;
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

}
