package io.github.chiangkaishek327.animatedfx.tabpane;

import java.util.ArrayList;
import java.util.TreeMap;

import io.github.chiangkaishek327.animatedfx.AnimatedPane;
import io.github.chiangkaishek327.animatedfx.ResizableAnimated;
import io.github.chiangkaishek327.animatedfx.AnimatedPane.MoveDirection;
import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class AnimatedTabPane<K> extends BorderPane implements ResizableAnimated {
    private HBox buttonBox = new HBox();
    private AnimatedPane content = new AnimatedPane();
    private ObservableMap<K, AnimatedTab<K>> tabMap = FXCollections.observableMap(new TreeMap<>());
    private BooleanProperty isTurnToNewPageAuto = new SimpleBooleanProperty(true);
    private AnimatedTabPane<K> tatp = this;
    private ObjectProperty<AnimatedTab<K>> selectedProperty = new SimpleObjectProperty<>();
    private IntegerProperty selectedIndexProperty = new SimpleIntegerProperty(0);
    private ObjectProperty<EventHandler<ActionEvent>> onTabSelectedProperty = new SimpleObjectProperty<>(e -> {
    });

    /**
     * Hmmmmmm...
     *
     */
    public AnimatedTabPane() {
        getStyleClass().add("animated-tabpane");
        buttonBox.getStyleClass().add("animated-tabpane-header");
        setTop(buttonBox);
        setCenter(content);
        tabMap.addListener(new MapChangeListener<K, AnimatedTab<K>>() {
            @Override
            public void onChanged(Change<? extends K, ? extends AnimatedTab<K>> change) {
                if (change.wasAdded()) {
                    change.getValueAdded().setParentTabPane(tatp);
                    if (isTurnToNewPageAuto.getValue()) {
                        load(change.getValueAdded().getKey());
                    }
                }
                buttonBox.getChildren().setAll(tabMap.values().stream().map(AnimatedTab<K>::getButton).toList());

            }

        });
    }

    /**
     * 
     * @param key key of the tab what tab map contains
     */
    public void load(K key) {

        AnimatedTab<K> selected = tabMap.get(key);
        for (AnimatedTab<K> tab : tabMap.values()) {
            tab.setSelected(false);
        }
        selected.setSelected(true);
        selectedProperty.setValue(selected);
        int selectedIndex = new ArrayList<>(tabMap.values()).indexOf(selected);
        if (selectedIndex > selectedIndexProperty.intValue()) {
            loadContent(selected.getContent(), MoveDirection.MD_RIGHT_TO_LEFT);
        } else if (selectedIndex < selectedIndexProperty.intValue()) {
            loadContent(selected.getContent(), MoveDirection.MD_LEFT_TO_RIGHT);
        } else {
            loadContent(selected.getContent(), null);
        }
        selectedIndexProperty.setValue(selectedIndex);
        selectedProperty.getValue().getButton().getTransitions()[1].play();
    }

    /**
     * 
     * @return the map contains all tabs
     */
    public ObservableMap<K, AnimatedTab<K>> tabs() {
        return tabMap;
    }

    /**
     * 
     * @param tab tab need to be added
     */
    public void addTab(AnimatedTab<K> tab) {
        tabMap.put(tab.getKey(), tab);
    }

    /**
     * 
     * @param tab tabs need to be added
     */
    @SuppressWarnings("unchecked")
    public void addTabs(AnimatedTab<K>... tab) {
        for (AnimatedTab<K> animatedTab : tab) {
            addTab(animatedTab);
        }
    }

    /**
     * 
     * @param content$      content need to be loaded
     * @param moveDirection {@link io.github.chiangkaishek327.animatedfx.AnimatedPane}
     */
    public void loadContent(Parent content$, MoveDirection moveDirection) {
        if (moveDirection == null) {
            this.content.setCenter(content$);
        } else {
            this.content.setGraphic(content$, moveDirection);
        }

    }

    public AnimatedPane getContentAnimatedPane() {
        return content;
    }

    /**
     * 
     * @param eventHandler trigger when a tab was selected
     */
    public void setOnTabSelected(EventHandler<ActionEvent> eventHandler) {
        onTabSelectedProperty.setValue(eventHandler);
    }

    public ObjectProperty<EventHandler<ActionEvent>> onTabSelectedProperty() {
        return onTabSelectedProperty;
    }

    /**
     * for the content pane
     */
    @Override
    public void update(double height, double width) {
        content.update(width, height);
    }

    @Override
    public Transition[] getTransitions() {
        return content.getTransitions();
    }

    @Override
    public Duration getAnimationLength() {
        return content.getAnimationLength();
    }

    /**
     * for the content pane
     */
    @Override
    public void setAnimationLength(Duration length) {
        content.setAnimationLength(length);
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
        return content.animationLengthProperty();
    }

    @Override
    public DoubleProperty animationRangeProperty() {
        return null;
    }

}
