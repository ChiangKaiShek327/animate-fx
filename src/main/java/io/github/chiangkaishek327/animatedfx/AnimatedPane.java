package io.github.chiangkaishek327.animatedfx;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class AnimatedPane extends BorderPane {
    public enum MoveDirection {
        MD_LEFT_TO_RIGHT(new int[] { 0, 1 }), MD_RIGHT_TO_LEFT(new int[] { 2, 3 }),
        MD_TOP_TO_BOTTOM(new int[] { 4, 5 }), MD_BOTTOM_TO_TOP(new int[] { 6, 7 });

        private final int[] indexs;

        private MoveDirection(int[] indexs) {
            this.indexs = indexs;
        }

    }

    TranslateTransition[] transition = new TranslateTransition[8];
    Duration animationLength = new Duration(100);

    public AnimatedPane() {
        for (int i = 0; i < transition.length; i++) {
            transition[i] = new TranslateTransition(animationLength, this);
            transition[i].setInterpolator(Interpolator.EASE_BOTH);
        }
        for (MoveDirection md : MoveDirection.values()) {
            transition[md.indexs[0]].setFromX(0);
            transition[md.indexs[1]].setToX(0);
            transition[md.indexs[0]].setFromY(0);
            transition[md.indexs[1]].setToY(0);

        }

    }

    /**
     * 
     * @param node what need to show
     * @param d    direction (like: MD_LEFT_TO_RIGHT)
     */
    public void setGraphic(Node node, MoveDirection d) {
        transition[d.indexs[0]].play();
        new Thread() {
            public void run() {
                try {
                    Thread.sleep((long) animationLength.toMillis());

                    Platform.runLater(() -> {
                        setCenter(node);
                        transition[d.indexs[1]].play();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();
        ;
    }

    public void animatedSetVisible(boolean visible, MoveDirection d) {

        transition[d.indexs[visible ? 1 : 0]].play();
        Thread ah = new Thread(() -> {
            try {
                if (visible) {
                    Platform.runLater(() -> setVisible(true));

                } else {
                    Thread.sleep((long) animationLength.toMillis());
                    Platform.runLater(() -> setVisible(false));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ah.start();

    }

    /**
     * use this method before doing sth animated
     * 
     * @param width  parent width
     * @param height parent height
     */
    public void update(double width, double height) {
        transition[MoveDirection.MD_LEFT_TO_RIGHT.indexs[0]].setToX(width);

        transition[MoveDirection.MD_LEFT_TO_RIGHT.indexs[1]].setFromX(-getLayoutX() - getWidth());

        transition[MoveDirection.MD_RIGHT_TO_LEFT.indexs[0]].setToX(-getLayoutX() - getWidth());

        transition[MoveDirection.MD_RIGHT_TO_LEFT.indexs[1]].setFromX(width);

        transition[MoveDirection.MD_BOTTOM_TO_TOP.indexs[0]].setToY(-getLayoutY() - getHeight());

        transition[MoveDirection.MD_BOTTOM_TO_TOP.indexs[1]].setFromY(height);

        transition[MoveDirection.MD_TOP_TO_BOTTOM.indexs[0]].setToY(height);

        transition[MoveDirection.MD_TOP_TO_BOTTOM.indexs[1]].setFromY(-getLayoutY() - getHeight());
    }

    public Duration getAnimationLength() {
        return animationLength;
    }

    public void setAnimationLength(Duration animationLength) {
        this.animationLength = animationLength;
    }

}
