package io.github.chiangkaishek327.animatedfx;

import io.github.chiangkaishek327.animatedfx.utils.DoSomethingTransition;
import io.github.chiangkaishek327.animatedfx.utils.Utils;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
    DoubleProperty animationByRangeProperty = new SimpleDoubleProperty(20);

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

            transitions[md.indexs[1]].setByY(animationByRangeProperty.doubleValue());
        }
        transitions[MoveDirection.MD_BOTTOM_TO_TOP.indexs[1]].setByY(-animationByRangeProperty.doubleValue());

        transitions[MoveDirection.MD_RIGHT_TO_LEFT.indexs[1]].setByX(-animationByRangeProperty.doubleValue());

        transitions[MoveDirection.MD_TOP_TO_BOTTOM.indexs[1]].setByY(animationByRangeProperty.doubleValue());

        transitions[MoveDirection.MD_LEFT_TO_RIGHT.indexs[1]].setByX(animationByRangeProperty.doubleValue());
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
        Utils.playSequenitalTransition(this, new Transition[] {
                transitions[d.indexs[0]], new DoSomethingTransition() {

                    @Override
                    public void someThingYouWantToDo() {
                        setCenter(node);
                    }

                }, transitions[d.indexs[1]]
        });
    }

    /**
     * 
     * @param visible is this visible
     * @param d       direction move out/in the parent
     */
    public void animatedSetVisible(boolean visible, MoveDirection d) {
        Transition dsst = new DoSomethingTransition() {

            @Override
            public void someThingYouWantToDo() {
                setVisible(visible);
            }

        };
        if (visible) {
            Utils.playSequenitalTransition(this, new Transition[] {
                    dsst, transitions[d.indexs[1]]
            });
        } else {

            Utils.playSequenitalTransition(this, new Transition[] {
                    transitions[d.indexs[0]], dsst
            });
        }

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

    @Override
    public double getAnimationByRange() {
        return animationByRangeProperty.getValue();
    }

    @Override
    public void setAnimationByRange(double animationByRange) {
        this.animationByRangeProperty.setValue(animationByRange);
    }

}
