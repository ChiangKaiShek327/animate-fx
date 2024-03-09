package io.github.chiangkaishek327.animatedfx.tabpane;

import java.io.Closeable;

import io.github.chiangkaishek327.animatedfx.Animated;
import io.github.chiangkaishek327.animatedfx.AnimatedButton;
import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AnimatedTab<K> implements EventTarget, Animated, Closeable {
    private AnimatedButton button = new AnimatedButton();
    private AnimatedButton closeButton = new AnimatedButton();
    private StringProperty titleProperty = new SimpleStringProperty("default");
    private BooleanProperty selectedProperty = new SimpleBooleanProperty(false);
    private BooleanProperty closeableProperty = new SimpleBooleanProperty(false);
    private final K key;
    private Parent content = new AnchorPane();
    private ObjectProperty<AnimatedTabPane<K>> tabPaneProperty = new SimpleObjectProperty<>();
    private ObjectProperty<EventHandler<ActionEvent>> onClosedProperty = new SimpleObjectProperty<>();

    /**
     * @param title title of this tab
     * @param key   when you add this to
     *              {@link io.github.chiangkaishek327.animatedfx.tabpane.AnimatedTabPane},
     *              this will be the key of this in the keymap of the tabpane
     *              <p>
     *              Of course,I dont know why I do that,maybe...it makes "managing
     *              the
     *              tabs" became easier?
     *              <p>
     *              I suggest you to use a random UUID String as a key if you
     *              needn't
     * 
     */
    public AnimatedTab(String title, K key) {
        this.key = key;
        button.textProperty().bind(titleProperty);
        titleProperty.setValue(title);
        button.getStyleClass().add("animated-tab");
        content.getStyleClass().clear();
        content.getStyleClass().add("animated-tab-content");
        closeButton.getStyleClass().add("animated-tab-close-button");
        button.setOnAction(e -> {
            selectedProperty.setValue(true);
            try {
                tabPaneProperty.getValue().load(key);
                tabPaneProperty.getValue().onTabSelectedProperty().getValue()
                        .handle(new ActionEvent(e, this));
            } catch (NullPointerException emkew) {
            }

        });
        button.setOnMouseExited(e -> {
            if (!selectedProperty.getValue())
                button.getTransitions()[1].play();
        });
        button.setOnMouseEntered(e -> {
            if (!selectedProperty.getValue())
                button.getTransitions()[0].play();
        });
        closeButton.visibleProperty().bind(closeableProperty);
        closeButton.setText("X");

        button.setGraphic(closeButton);
        button.setContentDisplay(ContentDisplay.RIGHT);
        onClosedProperty.setValue(e -> {
            close();
        });
        closeButton.setOnAction(onClosedProperty.getValue());
        selectedProperty.addListener((ob, o, n) -> {
            button.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), n);
        });
    }

    public AnimatedTab(K key) {
        this("default", key);
    }

    /**
     * 
     * @return content this tab show
     */
    public Parent getContent() {
        return content;
    }

    /**
     * 
     * @param content content this tab show
     */
    public void setContent(Parent content) {
        this.content = content;
    }

    /**
     * 
     * @return display on the top of tabpane, press to turn to this tab
     */
    public AnimatedButton getButton() {
        return button;
    }

    /**
     * 
     * @return title string property
     */
    public StringProperty titleProperty() {
        return titleProperty;
    }

    /**
     * 
     * @return as the name
     */
    public BooleanProperty closeableProperty() {
        return closeableProperty;
    }

    /**
     * 
     * @return as the name
     */
    public BooleanProperty selectedProperty() {
        return selectedProperty;
    }

    /**
     * no to use this method
     * 
     * @param parentTabPane
     */
    public void setParentTabPane(AnimatedTabPane<K> parentTabPane) {
        tabPaneProperty.setValue(parentTabPane);
    }

    /**
     * 
     * @return the tabpane own this
     */
    public AnimatedTabPane<K> getParentTabPane() {
        return tabPaneProperty.getValue();
    }

    /**
     * no to use this method
     * 
     * @param selected
     */
    public void setSelected(boolean selected) {
        selectedProperty.setValue(selected);
    }

    /**
     * 
     * @return is this has been selected
     */
    public boolean isSelected() {
        return selectedProperty.getValue();
    }

    /**
     * 
     * @param closeable is the tab can be closed
     */
    public void setCloseable(boolean closeable) {
        closeableProperty.setValue(closeable);
    }

    /**
     * 
     * @return is the tab can be closed
     */
    public boolean isCloseable() {
        return closeableProperty.getValue();
    }

    /**
     * 
     * @return key of this,you can use this key to find this tab in tab map of the
     *         {@link io.github.chiangkaishek327.animatedfx.tabpane.AnimatedTabPane}
     */
    public K getKey() {
        return key;
    }

    /**
     * 
     * @param eventHandler trigger on closed
     */
    public void setOnClosed(EventHandler<ActionEvent> eventHandler) {
        onClosedProperty.setValue(eventHandler);
    }

    /**
     * 
     * @return eventHandler what trigger on closed
     */
    public EventHandler<ActionEvent> getOnClosed() {
        return onClosedProperty.getValue();
    }

    /**
     * 
     * @return you know
     */
    public ObjectProperty<EventHandler<ActionEvent>> onClosedProperty() {
        return onClosedProperty;
    }

    /**
     * 
     * @return trigger the event "onClosed" when pressed
     */
    public AnimatedButton getCloseButton() {
        return closeButton;
    }

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return tail;
    }

    @Override
    public Transition[] getTransitions() {
        return button.getTransitions();
    }

    @Override
    public Duration getAnimationLength() {
        return button.getAnimationLength();
    }

    @Override
    public void setAnimationLength(Duration length) {
        button.setAnimationLength(length);
    }

    @Override
    public double getAnimationRange() {
        return button.getAnimationRange();
    }

    @Override
    public void setAnimationRange(double range) {
        button.setAnimationRange(range);
    }

    @Override
    public ObjectProperty<Duration> animationLengthProperty() {
        return button.animationLengthProperty();
    }

    @Override
    public DoubleProperty animationRangeProperty() {
        return button.animationRangeProperty();
    }

    @Override
    @SuppressWarnings("all")
    public void close() {
        getParentTabPane().tabs().remove(key);
        getParentTabPane().load((K) getParentTabPane().tabs().keySet().toArray()[0]);
        selectedProperty.setValue(false);
        tabPaneProperty.setValue(null);
    }

}
