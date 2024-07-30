package io.github.chiangkaishek327.animated.control.pane;

import java.util.ArrayList;
import java.util.List;

import io.github.chiangkaishek327.animated.animation.Animated;
import io.github.chiangkaishek327.animated.control.pane.PaneAnimationGroup.PaneAnimationType;
import io.github.chiangkaishek327.animated.control.pane.PaneAnimationGroup.PaneAnimationDirection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class AnimatedPane extends Pane implements Animated {
    protected List<BorderPane> vectorList = new ArrayList<>();
    protected ObjectProperty<BorderPane> currentProperty = new SimpleObjectProperty<>();
    protected IntegerProperty vectorIndexProperty = new SimpleIntegerProperty(0);
    protected DoubleProperty borderWidthProperty = new SimpleDoubleProperty(0);
    protected ObjectProperty<Duration> durationProperty = new SimpleObjectProperty<>(Duration.millis(100));
    protected ObjectProperty<PaneAnimationType> animationTypeProperty = new SimpleObjectProperty<>();
    protected ObjectProperty<PaneAnimationGroup> animationGroupProperty = new SimpleObjectProperty<>();
    protected BooleanProperty isSetContentWidthAutomatically = new SimpleBooleanProperty(true);
    protected BooleanProperty isSetContentHeightAutomatically = new SimpleBooleanProperty(true);

    public AnimatedPane() {
        super();
        initall();

        setAnimationType(PaneAnimationType.PAT_TRANSLATE);
    }

    /**
     * 
     * @param node show something without animation
     */
    public void show(Node node) {

        getNext().setCenter(node);
        toNext();
    }

    public void orgn_show(Node node) {

        getCurrent().setCenter(node);
    }

    /**
     * 
     * @param node show something with animation
     */

    public void show(PaneAnimationDirection direction, Node node) {

        getNext().setCenter(node);
        animationGroupProperty.getValue().turn(direction, getCurrent(), getNext());
        toNext();
    }

    protected int getNextIndex() {
        if (vectorIndexProperty.get() == 0)
            return 1;
        else
            return 0;
    }

    protected void toNext() {
        vectorIndexProperty.set(getNextIndex());
    }

    protected void initall() {

        animationGroupProperty.addListener((ob, o, n) -> {
            if (o != null) {
                o.durationProperty().unbind();
                o.borderWidthProperty().unbind();
                o.widthProperty().unbind();
                o.heightProperty().unbind();
            }
            n.durationProperty().bind(durationProperty);
            n.borderWidthProperty().bind(borderWidthProperty);
            n.widthProperty().bind(widthProperty());
            n.heightProperty().bind(heightProperty());
        });
        animationTypeProperty.addListener((ob, o, n) -> {
            try {
                PaneAnimationGroup newAnimationGroup = n.clazz.getConstructor().newInstance();
                animationGroupProperty.setValue(newAnimationGroup);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        vectorList.clear();
        for (int i = 0; i < 2; i++) {
            BorderPane bp = new BorderPane();
            vectorList.add(bp);
            bp.prefHeightProperty().bind(heightProperty());
            bp.prefWidthProperty().bind(widthProperty());
            bp.prefHeightProperty().addListener((ob, o, n) -> {
                Node center = bp.getCenter();
                if (center instanceof Region && isSetContentHeightAutomatically.get()) {
                    ((Region) center).setPrefHeight(n.doubleValue());
                }
            });

            bp.prefWidthProperty().addListener((ob, o, n) -> {
                Node center = bp.getCenter();
                if (center instanceof Region && isSetContentWidthAutomatically.get()) {
                    ((Region) center).setPrefWidth(n.doubleValue());
                }
            });

        }
        getChildren().addAll(vectorList);
        vectorIndexProperty.addListener((ob, o, n) -> {
            initCurrect();

        });
        initCurrect();
    }

    protected void initCurrect() {

        currentProperty.set(vectorList.get(vectorIndexProperty.get()));
        getCurrent().setVisible(true);
    }

    protected BorderPane getCurrent() {
        return vectorList.get(vectorIndexProperty.get());
    }

    protected BorderPane getNext() {
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

    /**
     * 
     * @param animationType You have 2 choice:
     *                      <p>
     * 
     *                      1.PAT_TRANSLATE
     *                      <p>
     *                      2.PAT_OAT (It means: Opacity%Translate)
     */
    public void setAnimationType(PaneAnimationType animationType) {
        animationTypeProperty.setValue(animationType);
    }

    public PaneAnimationType getAnimationType() {
        return animationTypeProperty.getValue();
    }

    public ObjectProperty<PaneAnimationType> animationTypeProperty() {
        return animationTypeProperty;
    }

    public boolean isSetContentHeightAutomatically() {
        return isSetContentHeightAutomatically.getValue();
    }

    public void setSetContentHeightAutomatically(boolean SetContentHeightAutomatically) {
        isSetContentHeightAutomatically.setValue(SetContentHeightAutomatically);
    }

    public BooleanProperty setContentHeightAutomaticallyProperty() {
        return isSetContentHeightAutomatically;
    }

    public boolean isSetContentWidthAutomatically() {
        return isSetContentWidthAutomatically.getValue();
    }

    public void setSetContentWidthAutomatically(boolean SetContentWidthAutomatically) {
        isSetContentWidthAutomatically.setValue(SetContentWidthAutomatically);
    }

    public BooleanProperty setContentWidthAutomaticallyProperty() {
        return isSetContentWidthAutomatically;
    }
}