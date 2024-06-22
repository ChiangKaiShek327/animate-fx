package io.github.chiangkaishek327.animated.control.pane;

import java.util.ArrayList;
import java.util.List;

import io.github.chiangkaishek327.animated.animation.Animated;
import io.github.chiangkaishek327.animated.control.pane.PaneAnimationGroup.PaneAnimationType;
import io.github.chiangkaishek327.animated.util.AnimationGroupLoader;
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
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class AnimatedPane extends Pane implements Animated {
    protected List<BorderPane> vectorList = new ArrayList<>();
    protected ObjectProperty<BorderPane> currectProperty = new SimpleObjectProperty<>();
    protected IntegerProperty vectorIndexProperty = new SimpleIntegerProperty(0);
    protected ObjectProperty<PaneAnimationGroup> animationGroupProperty = new SimpleObjectProperty<>();
    protected DoubleProperty borderWidthProperty = new SimpleDoubleProperty(0);
    private ObjectProperty<Duration> durationProperty = new SimpleObjectProperty<>(Duration.millis(100));
    protected ObjectProperty<PaneAnimationType> animationTypeProperty = new SimpleObjectProperty<>();
    AnimationGroupLoader<AnimatedPane> animationGroupLoader = new AnimationGroupLoader<AnimatedPane>(
            AnimatedPane.this);

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
        update();
        getCurrent().setCenter(node);
    }

    /**
     * 
     * @param node show something with animation
     */

    public void show(PaneAnimationDirection direction, Node node) {
        update();
        updateNext();
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
        widthProperty().addListener((ob, o, n) -> {
            update();
        });

        heightProperty().addListener((ob, o, n) -> {
            update();
        });
        animationGroupProperty.addListener((ob, o, n) -> {
            if (o != null) {
                o.durationProperty().unbind();
                o.borderWidthProperty().unbind();
            }
            n.durationProperty().bind(durationProperty);
            n.borderWidthProperty().bind(borderWidthProperty);
        });
        animationTypeProperty.addListener((ob, o, n) -> {
            try {
                PaneAnimationGroup newAnimationGroup = ((PaneAnimationGroup) animationGroupLoader.load(n.clazz));
                animationGroupProperty.setValue(newAnimationGroup);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        vectorList.clear();
        for (int i = 0; i < 2; i++) {
            BorderPane bp = new BorderPane();
            vectorList.add(bp);
            bp.prefHeightProperty().addListener((ob, o, n) -> {
                Node center = bp.getCenter();
                if (center instanceof Region) {
                    ((Region) center).setPrefHeight(n.doubleValue());
                }
            });

            bp.prefWidthProperty().addListener((ob, o, n) -> {
                Node center = bp.getCenter();
                if (center instanceof Region) {
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

    public void update() {
        getCurrent().setPrefHeight(getHeight());

        getCurrent().setPrefWidth(getWidth());
    }

    public void updateNext() {
        getNext().setPrefHeight(getHeight());

        getNext().setPrefWidth(getWidth());
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

    /**
     * 
     * @param animationType You have 2 choice:
     *                      <p>
     *                      1.PAT_TRANSLATE
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
}