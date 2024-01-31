package io.github.chiangkaishek327.animatedfx;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

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

    /**
     * Need a parent for an
     */
    public AnimatedPane() {

        for (int i = 0; i < transition.length; i++) {
            transition[i] = new TranslateTransition(AnimationUtil.getAnimationLength(), this);

            if (i % 2 != 0) {
                transition[i].setToX(0);
                transition[i].setToY(0);
            } else {
                transition[i].setFromX(0);
                transition[i].setFromY(0);

            }
        }
    }

    public void setGraphic(Node node, MoveDirection d) {
        transition[d.indexs[0]].play();
        new Thread() {
            public void run() {
                try {
                    Thread.sleep((long) AnimationUtil.getAnimationLength().toMillis());
                    Platform.runLater(() -> transition[d.indexs[1]].play());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        ;
    }

    private double[] getParentSize() {

        double[] sz = new double[2];
        if (getParent() != null) {
            Region r = (Region) getParent();
            sz[0] = r.getWidth();
            sz[1] = r.getHeight();
        } else {
            sz[0] = getWidth();
            sz[1] = getHeight();
        }
        return sz;
    }
x
    public void update(double width, double height) {
        double[] whs = getParentSize();
        double pwidth = whs[0], pheight = whs[1];

    }

}
