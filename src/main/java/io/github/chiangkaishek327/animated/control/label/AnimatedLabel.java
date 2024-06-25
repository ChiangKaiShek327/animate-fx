package io.github.chiangkaishek327.animated.control.label;

import io.github.chiangkaishek327.animated.control.pane.AnimatedPane;
import io.github.chiangkaishek327.animated.control.pane.PaneAnimationGroup;
import io.github.chiangkaishek327.animated.control.pane.PaneAnimationGroup.PaneAnimationType;
import io.github.chiangkaishek327.animated.util.OtherUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class AnimatedLabel extends StackPane {
    Label[] labels = new Label[] { new Label(), new Label() };
    BooleanProperty isLabel0Idle = new SimpleBooleanProperty(false);
    BooleanProperty isAnimated = new SimpleBooleanProperty(true);

    ObjectProperty<Label> current = new SimpleObjectProperty<>(labels[0]);
    StringProperty text = new SimpleStringProperty(OtherUtil.DEFAULT_TEXT);
    ObjectProperty<PaneAnimationGroup> animationGroup = new SimpleObjectProperty<>();

    protected ObjectProperty<PaneAnimationType> animationTypeProperty = new SimpleObjectProperty<>();
    protected ObjectProperty<PaneAnimationGroup> animationGroupProperty = new SimpleObjectProperty<>();

    public AnimatedLabel() {

    }

    protected Label getCurrent() {
        return current.getValue();
    }

    protected Label getNext() {
        return labels[isLabel0Idle.getValue() ? 0 : 1];
    }
}
