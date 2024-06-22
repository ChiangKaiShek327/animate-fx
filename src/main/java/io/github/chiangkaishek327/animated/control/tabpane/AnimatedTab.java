package io.github.chiangkaishek327.animated.control.tabpane;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.github.chiangkaishek327.animated.control.button.AnimatedButton;
import io.github.chiangkaishek327.animated.util.OtherUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * styleclasses:
 * tab header: animated-tab-header
 * --:selected: animated-tab-header:selected
 * --close button: close-button
 */
public class AnimatedTab implements Closeable {

    protected AnimatedButton tabHeader = new AnimatedButton();
    protected HBox tabHeaderGraphic = new HBox();
    protected Button closeButton = new Button();
    protected Label textLabel = new Label();
    protected ObjectProperty<Image> iconProperty = new SimpleObjectProperty<>();
    protected ImageView iconView = new ImageView();
    protected SimpleStringProperty style = new SimpleStringProperty();
    protected ObjectProperty<AnimatedTabPane> ownerProperty = new SimpleObjectProperty<>();
    protected ObjectProperty<Node> contentProperty = new SimpleObjectProperty<>();
    protected StringProperty titleProperty = new SimpleStringProperty();
    protected BooleanProperty selectedProperty = new SimpleBooleanProperty(false);

    protected ReadOnlyBooleanProperty selectedReadOnlyProperty = ReadOnlyBooleanProperty
            .readOnlyBooleanProperty(selectedProperty);
    protected BooleanProperty closeableProperty = new SimpleBooleanProperty(false);
    protected DoubleProperty iconSizeProperty = new SimpleDoubleProperty(15);

    public static final PseudoClass PSEUDO_CLASS_SELECTED = PseudoClass.getPseudoClass("selected");
    public static final String ATSC_HEADER = "animated-tab-header", ATSC_CLOSE_BUTTON = "close-button";

    public AnimatedTab(String title) {
        this(title, new AnchorPane(), null);
    }

    public AnimatedTab(String title, Node content) {
        this(title, content, null);
    }

    /**
     * 
     * @param title   title of this tab
     * @param content content this tab display
     */
    public AnimatedTab(String title, Node content, Image icon) {
        tabHeader.setText("");
        tabHeaderGraphic.setAlignment(Pos.CENTER);
        closeButton.getStyleClass().addAll(ATSC_CLOSE_BUTTON);
        closeButton.setPrefSize(10, 10);
        closeButton.setPadding(new Insets(0));
        closeButton.setOnAction(e -> close());
        closeButton.setFocusTraversable(false);
        contentProperty.setValue(content);
        ownerProperty.addListener((ob, o, n) -> {
            if (o != null) {
                o.getTabs().remove(this);
            }
        });
        style.addListener((ob, o, n) -> {
            content.setStyle(n);
        });
        closeButton.visibleProperty().bind(closeableProperty);
        tabHeader.getStyleClass().add(ATSC_HEADER);
        tabHeader.setGraphic(tabHeaderGraphic);
        tabHeaderGraphic.getChildren().addAll(textLabel);
        iconProperty.addListener((ob, o, n) -> {
            if (n == null && tabHeaderGraphic.getChildren().contains(iconView)) {
                tabHeaderGraphic.getChildren().remove(iconView);
            } else {
                tabHeaderGraphic.getChildren().add(0, iconView);
            }

        });
        closeableProperty.addListener((ob, o, n) -> {
            if (n) {
                tabHeaderGraphic.getChildren().add(closeButton);
            } else {
                tabHeaderGraphic.getChildren().remove(closeButton);
            }
        });
        iconView.fitHeightProperty().bind(iconSizeProperty);
        iconView.fitWidthProperty().bind(iconSizeProperty);
        iconView.imageProperty().bind(iconProperty);
        textLabel.textProperty().bind(titleProperty);
        selectedProperty.addListener((ob, o, n) -> {

            tabHeader.pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, n);

        });
        tabHeader.setOnAction(e -> {
            if (!selectedProperty.get())
                select();
        });
        iconProperty.setValue(icon);
        setTitle(title);
    }

    /**
     * let this tab be selected
     */
    public void select() {
        ownerProperty.get().load(this);
        tabHeader.pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, true);

    }

    public AnimatedButton getButton() {
        return tabHeader;
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

    protected void setSelected(boolean s) {
        selectedProperty.setValue(s);
    }

    public boolean isSelected() {
        return selectedProperty.get();
    }

    public ReadOnlyBooleanProperty selectedProperty() {
        return selectedReadOnlyProperty;
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
        return tabHeader.getDuration();
    };

    public void setDuration(Duration duration) {
        tabHeader.setDuration(duration);
    };

    public ObjectProperty<Duration> durationProperty() {
        return tabHeader.durationProperty();
    };

    public void setIcon(Image icon) {
        iconProperty.set(icon);
    }

    public Image getIcon() {
        return iconProperty.get();
    }

    public ObjectProperty<Image> iconProperty() {
        return iconProperty;
    }

    public void setCloseable(boolean closeable) {
        closeableProperty.setValue(closeable);
    }

    public boolean isCloseable() {
        return closeableProperty.getValue();
    }

    public BooleanProperty closeableProperty() {
        return closeableProperty;
    }

    /**
     * close this page (this method will be actived when the close button be
     * clicked, of course, you can do this by urself)
     */
    @Override
    public void close() {
        getOwner().getTabs().remove(this);
    }

}
