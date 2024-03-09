package io.github.chiangkaishek327.animatedfx;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class AnimatedButton extends Button implements Animated {
    ScaleTransition transitions[] = new ScaleTransition[2];
    ObjectProperty<Duration> animationLengthProperty = new SimpleObjectProperty<>(new Duration(100));
    DoubleProperty animationRangeProperty = new SimpleDoubleProperty(1.1);

    public AnimatedButton(String text) {
        setText(text);
        for (int i = 0; i < transitions.length; i++) {
            transitions[i] = new ScaleTransition();
            transitions[i].setNode(this);
            transitions[i].durationProperty().bind(animationLengthProperty);
            transitions[i].setInterpolator(Interpolator.EASE_BOTH);
        }
        /*
         * 0:enlarge
         * 1:shrink
         */
        transitions[0].setFromY(1);
        transitions[0].setToY(animationRangeProperty.doubleValue());
        transitions[1].setFromY(animationRangeProperty.doubleValue());
        transitions[1].setToY(1);
        setOnMouseEntered(e -> transitions[0].play());
        setOnMouseExited(e -> transitions[1].play());
        setOnMouseReleased(e -> transitions[0].play());
        setOnMousePressed(e -> transitions[1].play());
        animationRangeProperty.addListener((ob, o, n) -> {
            transitions[0].setToY(animationRangeProperty.doubleValue());
            transitions[1].setFromY(animationRangeProperty.doubleValue());
        });
        getStyleClass().add("animated-button");
    }

    public AnimatedButton() {
        this("default");
    }

    @Override
    public Transition[] getTransitions() {
        return transitions;
    }

    /**
     * scaling button
     */
    @Override
    public Duration getAnimationLength() {
        return animationLengthProperty.getValue();
    }

    @Override
    public void setAnimationLength(Duration length) {
        animationLengthProperty.setValue(length);
    }

    /**
     * scale
     */
    @Override
    public double getAnimationRange() {
        return animationRangeProperty.getValue();
    }

    @Override
    public void setAnimationRange(double range) {
        animationRangeProperty.setValue(range);
    }

    @Override
    public ObjectProperty<Duration> animationLengthProperty() {
        return animationLengthProperty;
    }

    @Override
    public DoubleProperty animationRangeProperty() {
        return animationRangeProperty;
    }

}
