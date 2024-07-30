package io.github.chiangkaishek327.animated.control.pane;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.scene.Node;

/**
 * Actually, I think this name is bad also
 * <p>
 * this can change the opacity of content
 */
public class PaneOATAnimationGroup extends PaneTranslateAnimationGroup {
    public PaneOATAnimationGroup() {

    }

    @Override
    public void turn(PaneAnimationDirection direction, Node from, Node to) {
        setVectorVisible(from, to);
        FadeTransition fft = new FadeTransition(getDuration(), from);

        FadeTransition tft = new FadeTransition(getDuration(), to);
        fft.setFromValue(1);
        fft.setToValue(0);
        tft.setFromValue(0);
        tft.setToValue(1);
        ParallelTransition transitions = generateTransitions(direction, from, to);
        transitions.getChildren().addAll(fft, tft);
        transitions.play();
        ;
    }

}