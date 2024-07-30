package io.github.chiangkaishek327.animated.control.pane;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.css.PseudoClass;

public class DragablePane extends AnimatedPane {

    BooleanProperty isDragable = new SimpleBooleanProperty();
    double wMIX, wMIY;
    public static final String DPSC_BACKGROUND = "dragable-pane",
            DPSC_PSDO_ONDRAG = "selected";
    public static final PseudoClass PSEUDO_CLASS_ONDRAG = PseudoClass.getPseudoClass("selected");

    public DragablePane() {
        super();
        getStyleClass().addAll(DPSC_BACKGROUND);
        setOnMousePressed(e -> {
            wMIX = e.getX();
            wMIY = e.getY();
        });
        setOnMouseDragged(e -> {
            if (isDragable()) {
                setLayoutX(e.getSceneX() - wMIX);

                setLayoutY(e.getSceneY() - wMIY);

            }
        });

    }

    public boolean isDragable() {
        return isDragable.getValue();
    }

    public void setDragable(boolean dragable) {
        isDragable.setValue(dragable);
    }

    public BooleanProperty dragableProperty() {
        return isDragable;
    }
}
