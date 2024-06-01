package io.github.chiangkaishek327.animated.control.tabpane;

import io.github.chiangkaishek327.animated.control.pane.AnimatedPane;
import io.github.chiangkaishek327.animated.control.pane.PaneAnimationGroup.PaneAnimationDirection;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class AnimatedTabPane extends VBox {
    protected HBox buttonBox = new HBox();
    protected AnimatedPane animatedPane = new AnimatedPane();
    protected ObservableList<AnimatedTab> tabs = FXCollections.observableArrayList();
    protected ObjectProperty<AnimatedTab> selectedProperty = new SimpleObjectProperty<>();
    protected IntegerProperty currentIndexProperty = new SimpleIntegerProperty(-1);
    private ObjectProperty<Duration> durationProperty = new SimpleObjectProperty<>(Duration.millis(100));

    public AnimatedTabPane() {
        getStyleClass().add("animated-tab-pane");
        animatedPane.getStyleClass().add("animated-tab-pane-content");
        buttonBox.getStyleClass().add("animated-tab-pane-header");
        getChildren().addAll(buttonBox, animatedPane);
        widthProperty().addListener((ob, o, n) -> {
            double width = n.doubleValue();
            buttonBox.setPrefWidth(width);
            animatedPane.setPrefWidth(width);
        });
        heightProperty().addListener((ob, o, n) -> {
            double height = n.doubleValue();
            animatedPane.setPrefHeight(height - buttonBox.getHeight());
        });
        tabs.addListener((ListChangeListener.Change<? extends AnimatedTab> c) -> {
            c.next();
            if (c.wasAdded()) {
                if (currentIndexProperty.getValue() == -1) {
                    currentIndexProperty.setValue(0);
                }
                for (AnimatedTab animatedTab : c.getAddedSubList()) {
                    animatedTab.setOwner(this);
                }
                buttonBox.getChildren().addAll(c.getAddedSubList().stream().map(AnimatedTab::getButton).toList());
                c.getAddedSubList().get(0).select();
            } else if (c.wasRemoved()) {

                if (c.getList().size() == 0) {
                    currentIndexProperty.setValue(-1);
                }
                buttonBox.getChildren().removeAll(c.getRemoved().stream().map(AnimatedTab::getButton).toList());
            }
        });
        selectedProperty.addListener((ob, o, n) -> {
            if (o != null) {
                o.setSelectedStyleUsed(false);
            }
            n.setSelectedStyleUsed(true);
        });
        durationProperty.addListener((ob, o, n) -> {
            animatedPane.setDuration(n);
        });
    }

    public ObservableList<AnimatedTab> getTabs() {
        return tabs;
    }

    public AnimatedTab getSelected() {
        return selectedProperty.getValue();
    };

    /**
     * you shouldn't use this method, you should use the AnimatedTab's method
     * "select" instead of that
     * 
     * @param tab tab need be loaded (this tab must in the tab list)
     */
    @Deprecated(since = "someone else me using this method wasn't recommended, so I deprecated this")
    public void load(AnimatedTab tab) {
        if (tabs.contains(tab)) {
            int indexNext = tabs.indexOf(tab);
            PaneAnimationDirection pad;
            if (indexNext < currentIndexProperty.get()) {
                pad = PaneAnimationDirection.LEFT_TO_RIGHT;

            } else if (indexNext > currentIndexProperty.get()) {
                pad = PaneAnimationDirection.RIGHT_TO_LEFT;
            } else {
                pad = PaneAnimationDirection.NONE;
            }
            animatedPane.show(pad, tab.getContent());
            selectedProperty.setValue(tab);
            currentIndexProperty.setValue(tabs.indexOf(tab));
        } else {
            throw new IllegalArgumentException(
                    "tab not found " + tab.toString() + "\ndid you used this method without premission?😡");
        }
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

    public int getCurrentPageIndex() {
        return currentIndexProperty.getValue();
    };

    public IntegerProperty currentPageIndexProperty() {
        return currentIndexProperty;
    };

}
