package io.github.chiangkaishek327.animated.control.tabpane;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.github.chiangkaishek327.animated.control.button.AnimatedButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.event.EventDispatchChain;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class AnimatedTab implements Styleable, EventTarget {
    protected ObservableList<String> styleClasses = FXCollections.observableArrayList();
    protected SimpleStringProperty id = new SimpleStringProperty();

    protected List<CssMetaData<?, ?>> cssMetaData = new ArrayList<>();
    protected AnimatedButton thisButton = new AnimatedButton();
    protected ObservableSet<PseudoClass> pseudoClassStates = FXCollections.observableSet(new HashSet<>());
    // v really used
    protected SimpleStringProperty style = new SimpleStringProperty();
    protected ObjectProperty<AnimatedTabPane> ownerProperty = new SimpleObjectProperty<>();
    protected ObjectProperty<Node> contentProperty = new SimpleObjectProperty<>();
    protected StringProperty titleProperty = new SimpleStringProperty();
    protected BooleanProperty selectedStyleUsed = new SimpleBooleanProperty(false);
    public static final PseudoClass PSEUDO_CLASS_SELECTED = PseudoClass.getPseudoClass("selected");

    public AnimatedTab(AnimatedTabPane owner) {
        this("null", owner);
    }

    public AnimatedTab(String title, AnimatedTabPane owner) {
        this(title, owner, new Pane());
    }

    /**
     * 
     * @param title   title of this tab
     * @param owner   owner of this tab (like: new AnimatedTab(animatedTabPane0);)
     * @param content content this tab display
     */
    public AnimatedTab(String title, AnimatedTabPane owner, Node content) {
        ownerProperty.addListener((ob, o, n) -> {
            if (o != null) {
                o.getTabs().remove(this);
            }
        });
        style.addListener((ob, o, n) -> {
            content.setStyle(n);
        });
        thisButton.getStyleClass().add("animated-tab-header");
        thisButton.setText(title);
        contentProperty.setValue(content);
        setOwner(owner);
        owner.getTabs().add(this);
        thisButton.setOnAction(e -> {
            select();
        });
        selectedStyleUsed.addListener((ob, o, n) -> {

            thisButton.pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, n);

        });
    }

    /**
     * let this tab be selected
     */
    public void select() {
        ownerProperty.get().load(this);
        thisButton.pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, true);

    }

    public AnimatedButton getButton() {
        return thisButton;
    }

    public void setContent(Node node) {
        contentProperty.setValue(node);
    }

    public Node getContent() {
        return contentProperty.get();
    }

    public ObjectProperty<Node> contentProperty() {
        return contentProperty;
    }

    public void setSelectedStyleUsed(boolean s) {
        selectedStyleUsed.setValue(s);
    }

    public boolean isSelectedStyleUsed() {
        return selectedStyleUsed.get();
    }

    public BooleanProperty selectedStyleUsed() {
        return selectedStyleUsed;
    }

    public void setTitle(String title) {
        titleProperty.setValue(title);
    }

    public String getTitle() {
        return titleProperty.get();
    }

    public StringProperty titleProperty() {
        return titleProperty;
    }

    public void setOwner(AnimatedTabPane owner) {
        ownerProperty.set(owner);
    }

    public AnimatedTabPane getOwner() {
        return ownerProperty.get();
    }

    public ObjectProperty<AnimatedTabPane> ownerProperty() {
        return ownerProperty;
    }

    public Duration getDuration() {
        return thisButton.getDuration();
    };

    public void setDuration(Duration duration) {
        thisButton.setDuration(duration);
    };

    public ObjectProperty<Duration> durationProperty() {
        return thisButton.durationProperty();
    };

    @Override
    public String getId() {
        return id.getValue();
    }

    @Override
    public ObservableList<String> getStyleClass() {
        return styleClasses;
    }

    @Override
    public String getStyle() {
        return style.getValue();
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return cssMetaData;
    }

    @Override
    public Styleable getStyleableParent() {
        return ownerProperty.getValue();

    }

    @Override
    public ObservableSet<PseudoClass> getPseudoClassStates() {
        return pseudoClassStates;

    }

    @Override
    public String getTypeSelector() {
        return "animated-tab";
    }

    @Override
    public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return tail.append(thisButton.getEventDispatcher());
    }

}
