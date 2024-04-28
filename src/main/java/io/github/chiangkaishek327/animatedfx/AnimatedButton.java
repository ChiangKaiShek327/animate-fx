package io.github.chiangkaishek327.animatedfx;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.Transition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class AnimatedButton extends Button implements Animated {

    ObjectProperty<Duration> animationLengthProperty = new SimpleObjectProperty<>(new Duration(100));
    DoubleProperty animationRangeProperty = new SimpleDoubleProperty(1.2);
    DoubleProperty translateAnimationRangeProperty = new SimpleDoubleProperty(3);
    Map<AnimationMode, Transition[]> animationMap = new HashMap<>();
    ObjectProperty<AnimationMode> animationModeProperty = new SimpleObjectProperty<>(AnimationMode.AM_TRANSLATE);

    public enum AnimationMode {
        AM_TRANSLATE, AM_SCALE
    }

    public AnimatedButton() {
        super();
        TranslateTransition[] tt = new TranslateTransition[2];
        ScaleTransition[] st = new ScaleTransition[2];
        for (int i = 0; i < 2; i++) {
            tt[i] = new TranslateTransition(getAnimationLength(), this);
            st[i] = new ScaleTransition(getAnimationLength(), this);
            tt[i].durationProperty().bind(animationLengthProperty);
            st[i].durationProperty().bind(animationLengthProperty);
        }
        tt[0].toYProperty().bind(translateAnimationRangeProperty.multiply(-1));
        tt[0].setFromY(0);
        tt[1].fromYProperty().bind(translateAnimationRangeProperty.multiply(-1));
        tt[1].setToY(0);
        st[1].fromYProperty().bind(animationRangeProperty);
        st[1].setToY(1);
        st[0].toYProperty().bind(animationRangeProperty);
        st[0].setFromY(1);
        animationMap.put(AnimationMode.AM_SCALE, st);
        animationMap.put(AnimationMode.AM_TRANSLATE, tt);
        setOnMouseEntered(e -> animationMap.get(animationModeProperty.getValue())[0].play());
        setOnMouseExited(e -> animationMap.get(animationModeProperty.getValue())[1].play());
        setOnMousePressed(e -> animationMap.get(animationModeProperty.getValue())[1].play());
        setOnMouseReleased(e -> animationMap.get(animationModeProperty.getValue())[0].play());
    }

    @Override
    public Transition[] getTransitions() {
        return animationMap.get(AnimationMode.AM_SCALE);
    }

    public Transition[] getTranslateTransitions() {

        return animationMap.get(AnimationMode.AM_TRANSLATE);
    }

    public AnimationMode getAnimationMode() {
        return animationModeProperty.getValue();
    }

    public void setAnimationMode(AnimationMode animationMode) {
        animationModeProperty.setValue(animationMode);
    }

    public ObjectProperty<AnimationMode> animationModeProperty() {
        return animationModeProperty;
    }

    @Override
    public Duration getAnimationLength() {
        return animationLengthProperty.getValue();
    }

    @Override
    public void setAnimationLength(Duration length) {
        animationLengthProperty.set(length);
    }

    @Override
    public double getAnimationRange() {
        return animationRangeProperty.getValue();
    }

    @Override
    public void setAnimationRange(double range) {
        animationRangeProperty.setValue(range);
    }

    public double getTranslateAnimationRange() {
        return translateAnimationRangeProperty.getValue();
    }

    public void setTranslateAnimationRange(double range) {
        translateAnimationRangeProperty.setValue(range);
    }

    @Deprecated
    @Override
    public double getAnimationByRange() {
        return 0;
    }

    @Deprecated
    @Override
    public void setAnimationByRange(double range) {
    }

    @Override
    public ObjectProperty<Duration> animationLengthProperty() {
        return animationLengthProperty;
    }

    @Override
    public DoubleProperty animationRangeProperty() {
        return animationRangeProperty;
    }

    public DoubleProperty translateAnimationRangeProperty() {
        return translateAnimationRangeProperty;
    }
}