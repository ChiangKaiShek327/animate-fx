package io.github.chiangkaishek327.animated.control.pane;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.BorderPane;

public class PaneTranslateAnimationGroup extends PaneAnimationGroup {
    public PaneTranslateAnimationGroup(AnimatedPane a) {
        nodeProperty.set(a);
    }

    @Override
    public void turn(PaneAnimationDirection direction, BorderPane from, BorderPane to) {
        double fromx = 0;
        double fromy = 0;
        double tox = 0;
        double toy = 0;
        double nodeheight = getNode().getHeight();
        double nodewidth = getNode().getWidth();
        double brdwdt = getBorderWidth();
        double topbrd = -nodeheight - brdwdt;
        double btmbrd = nodeheight + brdwdt;
        double rgtbrd = nodewidth + brdwdt;
        double lftbrd = -nodewidth - brdwdt;
        switch (direction) {
            case TOP_TO_BOTTOM:
                fromy = topbrd;
                toy = btmbrd;
                break;
            case BOTTOM_TO_TOP:
                fromy = btmbrd;
                toy = topbrd;
                break;
            case LEFT_TO_RIGHT:
                fromx = lftbrd;
                tox = rgtbrd;
                break;
            case RIGHT_TO_LEFT:
                fromx = rgtbrd;
                tox = lftbrd;
                break;

        }
        TranslateTransition fromTransition = new TranslateTransition(getDuration(), from);
        TranslateTransition toTransition = new TranslateTransition(getDuration(), to);
        fromTransition.setFromX(0);
        fromTransition.setFromY(0);
        fromTransition.setToX(tox);
        fromTransition.setToY(toy);
        toTransition.setToX(0);
        toTransition.setToY(0);
        toTransition.setFromX(fromx);
        toTransition.setFromY(fromy);
        ParallelTransition transitions = new ParallelTransition(fromTransition, toTransition);

        transitions.play();
        ;
    }

}