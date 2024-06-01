package io.github.chiangkaishek327.animated.control.button;

import javafx.animation.TranslateTransition;

public class ButtonTranslateAnimationGroup extends ButtonAnimationGroup {

    private TranslateTransition up = new TranslateTransition(getDuration()),
            down = new TranslateTransition(getDuration());

    public ButtonTranslateAnimationGroup(AnimatedButton node) {
        setChangeScale(3);
        nodeProperty.setValue(node);
        for (TranslateTransition st : new TranslateTransition[] { up, down }) {
            st.setNode(node);
            st.durationProperty().bind(durationProperty());
        }
        up.setFromY(0);
        up.toYProperty().bind(changeScaleProperty().multiply(-1));
        down.fromYProperty().bind(changeScaleProperty().multiply(-1));
        down.setToY(0);
        node.setOnMouseEntered(e -> up.play());
        node.setOnMouseExited(e -> down.play());
        node.setOnMousePressed(e -> down.play());
        node.setOnMouseReleased(e -> up.play());
    }

}
