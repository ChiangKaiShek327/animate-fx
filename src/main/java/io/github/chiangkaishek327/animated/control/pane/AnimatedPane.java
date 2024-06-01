package io.github.chiangkaishek327.animated.control.pane;

import java.util.ArrayList;
import java.util.List;

import io.github.chiangkaishek327.animated.animation.Animated;
import io.github.chiangkaishek327.animated.control.pane.PaneAnimationGroup.PaneAnimationDirection;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class AnimatedPane extends Pane implements Animated {
    protected List<BorderPane> vectorList = new ArrayList<>();
    protected ObjectProperty<BorderPane> currectProperty = new SimpleObjectProperty<>();
    protected IntegerProperty vectorIndexProperty = new SimpleIntegerProperty(0);
    protected ObjectProperty<PaneAnimationGroup> animationGroupProperty = new SimpleObjectProperty<>();
    protected DoubleProperty borderWidthProperty = new SimpleDoubleProperty(0);
    private ObjectProperty<Duration> durationProperty = new SimpleObjectProperty<>(Duration.millis(100));

    public AnimatedPane() {
        super();
        initall();
    }

    /**
     * 
     * @param node show something without animation
     */
    public void show(Node node) {
        getCurrent().setCenter(node);
    }

    /**
     * 
     * @param node show something with animation
     */

    public void show(PaneAnimationDirection direction, Node node) {
        getNext().setCenter(node);
        getNext().setVisible(true);

        animationGroupProperty.getValue().turn(direction, getCurrent(), getNext());
        toNext();
    }

    private int getNextIndex() {
        if (vectorIndexProperty.get() == 0)
            return 1;
        else
            return 0;
    }

    private void toNext() {
        vectorIndexProperty.set(getNextIndex());
    }

    private void initall() {
        animationGroupProperty.addListener((ob, o, n) -> {
            if (o != null) {
                o.durationProperty().unbind();
                o.borderWidthProperty().unbind();
            }
            n.durationProperty().bind(durationProperty);
            n.borderWidthProperty().bind(borderWidthProperty);
        });
        vectorList.clear();
        animationGroupProperty.setValue(new PaneTranslateAnimationGroup(this));
        for (int i = 0; i < 2; i++) {
            vectorList.add(new BorderPane());

        }
        getChildren().addAll(vectorList);
        vectorIndexProperty.addListener((ob, o, n) -> {
            initCurrect();

        });
        initCurrect();
    }

    private void initCurrect() {

        currectProperty.set(vectorList.get(vectorIndexProperty.get()));
        getCurrent().setVisible(true);
    }

    private BorderPane getCurrent() {
        return vectorList.get(vectorIndexProperty.get());
    }

    private BorderPane getNext() {
        return vectorList.get(getNextIndex());

    }

    public Duration getDuration() {
        return durationProperty.getValue();
    };

    public void setDuration(Duration duration) {
        durationProperty.setValue(duration);
    };

    public ObjectProperty<Duration> durationProperty() {
        return durationProperty;
    };

    public double getBorderWidth() {
        return borderWidthProperty.getValue();
    };

    public void setBorderWidth(double da) {
        borderWidthProperty.setValue(da);
    };

    public DoubleProperty borderWidthProperty() {
        return borderWidthProperty;
    };
}