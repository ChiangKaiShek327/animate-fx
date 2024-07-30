package io.github.chiangkaishek327.animated.control.label;

import io.github.chiangkaishek327.animated.animation.Animated;
import io.github.chiangkaishek327.animated.control.pane.PaneAnimationGroup;
import io.github.chiangkaishek327.animated.control.pane.PaneAnimationGroup.PaneAnimationDirection;
import io.github.chiangkaishek327.animated.control.pane.PaneAnimationGroup.PaneAnimationType;
import io.github.chiangkaishek327.animated.util.OtherUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class AnimatedLabel extends Region implements Animated {
    Label[] labels = new Label[] { new Label(), new Label() };
    BooleanProperty isLabel0Idle = new SimpleBooleanProperty(false);
    BooleanProperty isAnimated = new SimpleBooleanProperty(true);

    ObjectProperty<Label> current = new SimpleObjectProperty<>(labels[0]);
    StringProperty text = new SimpleStringProperty(OtherUtil.DEFAULT_TEXT);

    protected ObjectProperty<PaneAnimationType> animationTypeProperty = new SimpleObjectProperty<>();
    protected ObjectProperty<PaneAnimationGroup> animationGroupProperty = new SimpleObjectProperty<>();
    protected ObjectProperty<Duration> durationProperty = new SimpleObjectProperty<>(Duration.millis(100));
    protected ObjectProperty<Font> fontProperty = new SimpleObjectProperty<>(Font.getDefault());
    public static final String ALSC_BACKGROUND = "animated-label",
            ALSC_LABEL = "animated-label-label";

    public AnimatedLabel() {
        getStyleClass().add(ALSC_BACKGROUND);
        fontProperty.addListener((ob, o, n) -> {

            for (Label label : labels) {
                label.setFont(n);
                label.prefHeightProperty().bind(this.heightProperty());
                label.prefWidthProperty().bind(this.widthProperty());
                label.getStyleClass().add(ALSC_LABEL);
            }
        });
        styleProperty().addListener((ob, o, n) -> {

            for (Label label : labels) {
                label.setStyle(n);
            }
        });
        animationGroupProperty.addListener((ob, o, n) -> {
            if (o != null) {
                o.durationProperty().unbind();
            }
            n.durationProperty().bind(durationProperty);
        });
        animationTypeProperty.addListener((ob, o, n) -> {
            try {
                animationGroupProperty.setValue(n.clazz.getConstructor().newInstance());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        getChildren().addAll(labels);
        text.addListener((ob, o, n) -> {
            if (isAnimated.getValue()) {
                Label next = getNext();
                next.setText(n);
                PaneAnimationGroup pag = animationGroupProperty.getValue();
                pag.setWidth(current.get().getWidth());
                pag.setHeight(current.get().getHeight());
                pag.turn(PaneAnimationDirection.TOP_TO_BOTTOM, getCurrent(), next);
                toNext();

            }
        });
        animationTypeProperty.setValue(PaneAnimationType.PAT_OAT);
    }

    protected Label getCurrent() {
        return labels[isLabel0Idle.getValue() ? 1 : 0];
    }

    protected Label getNext() {
        return labels[isLabel0Idle.getValue() ? 0 : 1];
    }

    protected void toNext() {
        isLabel0Idle.setValue(!isLabel0Idle.getValue());
    }

    public void setText(String s) {
        text.setValue(s);
    }

    public String getText() {
        return text.getValue();
    }

    public StringProperty textProperty() {
        return text;
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

    public Font getFont() {
        return fontProperty.getValue();
    };

    public void setFont(Font font) {
        fontProperty.setValue(font);
    };

    public ObjectProperty<Font> fontProperty() {
        return fontProperty;
    }

    public boolean isAnimated() {
        return isAnimated.getValue();
    }

    public void setAnimated(boolean animated) {
        isAnimated.setValue(animated);
    }

    public BooleanProperty animatedProperty() {
        return animatedProperty();
    }
}
