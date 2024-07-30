package io.github.chiangkaishek327.animated.control.tabpane;

import io.github.chiangkaishek327.animated.animation.Animated;
import io.github.chiangkaishek327.animated.control.pane.AnimatedPane;
import io.github.chiangkaishek327.animated.control.pane.PaneAnimationGroup.PaneAnimationDirection;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * styleclasses:
 * AnimatedTabPane: animated-tab-pane
 * --content: animated-tab-pane-content
 * --buttonbox: animated-tab-pane-header
 */
public class AnimatedTabPane extends BorderPane implements Animated {
    protected HBox buttonBox = new HBox();
    protected AnimatedPane animatedPane = new AnimatedPane();
    protected ObservableList<AnimatedTab> tabs = FXCollections.observableArrayList();
    protected ObjectProperty<AnimatedTab> selectedProperty = new SimpleObjectProperty<>();
    protected IntegerProperty currentIndexProperty = new SimpleIntegerProperty(-1);
    protected StringProperty titleProperty = new SimpleStringProperty("");
    private ObjectProperty<Duration> durationProperty = new SimpleObjectProperty<>(Duration.millis(100));

    private ObjectProperty<HeaderSide> headerSideProperty = new SimpleObjectProperty<>(HeaderSide.ATPHS_TOP);
    public static final String ATPSC_ANIMATED_TABPANE = "animated-tab-pane",
            ATPSC_CONTENT = "animated-tab-pane-content", ATPSC_BUTTONBOX = "animated-tab-pane-header";

    public enum HeaderSide {
        ATPHS_TOP, ATPHS_BOTTOM
    }

    @SuppressWarnings("all")
    public AnimatedTabPane() {
        initBB();
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        headerSideProperty.addListener((ob, o, n) -> {
            if (true) {
                throw new UnsupportedOperationException("uncompleted function");
            } else
                switch (n) {
                    case ATPHS_TOP:

                        setTop(buttonBox);
                        break;
                    case ATPHS_BOTTOM:
                        setBottom(buttonBox);
                        break;

                }
        });
        getStyleClass().add(ATPSC_ANIMATED_TABPANE);
        animatedPane.getStyleClass().add(ATPSC_CONTENT);
        setTop(buttonBox);
        setCenter(animatedPane);
        widthProperty().addListener((ob, o, n) -> {
            double width = n.doubleValue();

            animatedPane.setPrefWidth(width);
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
                    animatedPane.show(null);
                }
                buttonBox.getChildren().removeAll(c.getRemoved().stream().map(AnimatedTab::getButton).toList());
            }
        });
        selectedProperty.addListener((ob, o, n) -> {
            if (o != null) {
                o.setSelected(false);
            }
            n.setSelected(true);
        });
        animatedPane.durationProperty().bind(durationProperty);
    }

    public ObservableList<AnimatedTab> getTabs() {
        return tabs;
    }

    public AnimatedTab getSelected() {
        return selectedProperty.getValue();
    };

    protected void initBB() {
        buttonBox.getStyleClass().add(ATPSC_BUTTONBOX);
    }

    protected void load(AnimatedTab tab) {
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
            if (selectedProperty.get() != null)
                selectedProperty.get().setSelected(false);
            selectedProperty.setValue(tab);

            currentIndexProperty.setValue(tabs.indexOf(tab));
            tab.setSelected(true);
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

    public HeaderSide getHeaderSide() {
        return headerSideProperty.getValue();
    };

    public void setHeaderSide(HeaderSide hs) {
        headerSideProperty.setValue(hs);
    };

    public ObjectProperty<HeaderSide> headerSideProperty() {
        return headerSideProperty;
    };

    public int getCurrentPageIndex() {
        return currentIndexProperty.getValue();
    };

    public IntegerProperty currentPageIndexProperty() {
        return currentIndexProperty;
    };

    public AnimatedPane getContentPane() {
        return animatedPane;
    }

    public HBox getButtonBox() {
        return buttonBox;
    }
}
