package io.github.chiangkaishek327.animatedfx;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class AnimatedButton extends Button {
    ScaleTransition transition[] = new ScaleTransition[2];
    Duration animationLength = new Duration(100);

    public AnimatedButton() {
        for (int i = 0; i < transition.length; i++) {
            transition[i] = new ScaleTransition(animationLength, this);
            transition[i].setInterpolator(Interpolator.EASE_BOTH);
        }
        /*
         * 0:enlarge
         * 1:shrink
         */
        transition[0].setFromY(1);
        transition[0].setToY(1.1);
        transition[1].setFromY(1.1);
        transition[1].setToY(1);
        setOnMouseEntered(e -> transition[0].play());
        setOnMouseExited(e -> transition[1].play());
        setOnMouseReleased(e -> transition[0].play());
        setOnMousePressed(e -> transition[1].play());
    }

    public Duration getAnimationLength() {
        return animationLength;
    }

    public void setAnimationLength(Duration animationLength) {
        this.animationLength = animationLength;
    }

}
