package io.github.chiangkaishek327.animatedfx;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class AnimatedPane extends BorderPane implements ResizableAnimated {
    public enum MoveDirection {
        MD_LEFT_TO_RIGHT(new int[] { 0, 1 }), MD_RIGHT_TO_LEFT(new int[] { 2, 3 }),
        MD_TOP_TO_BOTTOM(new int[] { 4, 5 }), MD_BOTTOM_TO_TOP(new int[] { 6, 7 });

        private final int[] indexs;

        private MoveDirection(int[] indexs) {
            this.indexs = indexs;
        }

    }

    TranslateTransition[] transitions = new TranslateTransition[8];
    ObjectProperty<Duration> animationLengthProperty = new SimpleObjectProperty<>(new Duration(100));
    ObjectProperty<Node> contentProperty = new SimpleObjectProperty<>();

    public AnimatedPane() {
        for (int i = 0; i < transitions.length; i++) {
            transitions[i] = new TranslateTransition();
            transitions[i].setNode(this);
            transitions[i].durationProperty().bind(animationLengthProperty);
            transitions[i].setInterpolator(Interpolator.EASE_BOTH);
        }
        for (MoveDirection md : MoveDirection.values()) {
            transitions[md.indexs[0]].setFromX(0);
            transitions[md.indexs[1]].setToX(0);
            transitions[md.indexs[0]].setFromY(0);
            transitions[md.indexs[1]].setToY(0);

        }
        getStyleClass().add("animated-pane");
        contentProperty.addListener((ob, o, n) -> {
            setCenter(n);
        });
    }

    /**
     * 
     * @param node what need to show
     * @param d    direction (like: MD_LEFT_TO_RIGHT)
     */
    public void setGraphic(Node node, MoveDirection d) {
        transitions[d.indexs[0]].play();
        new Thread() {
            public void run() {
                try {
                    Thread.sleep((long) animationLengthProperty.getValue().toMillis());

                    Platform.runLater(() -> {
                        if (node != getCenter()) {
                            contentProperty.setValue(node);
                        }

                        transitions[d.indexs[1]].play();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();
        ;
    }

    /**
     * 
     * @param visible is this visible
     * @param d       direction move out/in the parent
     */
    public void animatedSetVisible(boolean visible, MoveDirection d) {

        transitions[d.indexs[visible ? 1 : 0]].play();
        Thread ah = new Thread(() -> {
            try {
                if (visible) {
                    Platform.runLater(() -> setVisible(true));

                } else {
                    Thread.sleep((long) animationLengthProperty.getValue().toMillis());
                    Platform.runLater(() -> setVisible(false));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ah.start();

    }

    public void update(double width, double height) {
        /*
         * lw: x cor when this hide on left of parent
         * lh: y cor when this hide on top of parent
         * indexs0: move out from the parent
         * indexs1: come back
         */
        double lw = getLayoutX() - width, lh = getLayoutY() - height;
        transitions[MoveDirection.MD_LEFT_TO_RIGHT.indexs[0]].setToX(width);

        transitions[MoveDirection.MD_LEFT_TO_RIGHT.indexs[1]].setFromX(lw);

        transitions[MoveDirection.MD_RIGHT_TO_LEFT.indexs[0]].setToX(lw);

        transitions[MoveDirection.MD_RIGHT_TO_LEFT.indexs[1]].setFromX(width);

        transitions[MoveDirection.MD_BOTTOM_TO_TOP.indexs[0]].setToY(lh);

        transitions[MoveDirection.MD_BOTTOM_TO_TOP.indexs[1]].setFromY(height);

        transitions[MoveDirection.MD_TOP_TO_BOTTOM.indexs[0]].setToY(height);

        transitions[MoveDirection.MD_TOP_TO_BOTTOM.indexs[1]].setFromY(lh);
    }

    @Override
    public Transition[] getTransitions() {
        return transitions;
    }

    @Override
    public Duration getAnimationLength() {
        return animationLengthProperty.getValue();
    }

    @Override
    public void setAnimationLength(Duration length) {
        animationLengthProperty.setValue(length);
    }

    @Override
    public double getAnimationRange() {
        return 0;
    }

    @Override
    public void setAnimationRange(double range) {

    }

    @Override
    public ObjectProperty<Duration> animationLengthProperty() {
        return animationLengthProperty;
    }

    @Override
    public DoubleProperty animationRangeProperty() {
        return null;
    }

}
