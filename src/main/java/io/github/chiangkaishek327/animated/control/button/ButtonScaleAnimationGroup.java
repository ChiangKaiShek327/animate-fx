package io.github.chiangkaishek327.animated.control.button;

import javafx.animation.ScaleTransition;

public class ButtonScaleAnimationGroup extends ButtonAnimationGroup {
    private ScaleTransition tobig = new ScaleTransition(getDuration()), tosmall = new ScaleTransition(getDuration());

    public ButtonScaleAnimationGroup(AnimatedButton node) {
        setChangeScale(1.2);
        for (ScaleTransition st : new ScaleTransition[] { tobig, tosmall }) {
            st.setNode(node);
            st.durationProperty().bind(durationProperty());
        }
        tobig.setFromY(1);
        tobig.toYProperty().bind(changeScaleProperty());
        tosmall.fromYProperty().bind(changeScaleProperty());
        tosmall.setToY(1);
        node.setOnMouseEntered(e -> tobig.play());
        node.setOnMouseExited(e -> tosmall.play());
        node.setOnMousePressed(e -> tosmall.play());
        node.setOnMouseReleased(e -> tobig.play());
    }

}
