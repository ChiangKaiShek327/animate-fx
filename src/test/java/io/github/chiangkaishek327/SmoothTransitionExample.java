package io.github.chiangkaishek327;

import io.github.chiangkaishek327.animated.control.button.AnimatedButton;
import io.github.chiangkaishek327.animated.control.button.ButtonAnimationGroup.ButtonAnimationType;
import io.github.chiangkaishek327.animated.control.pane.AnimatedPane;
import io.github.chiangkaishek327.animated.control.pane.PaneAnimationGroup.PaneAnimationDirection;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SmoothTransitionExample extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnimatedButton b = new AnimatedButton("fuck");

        VBox bp = new VBox(b);
        StringProperty sp = new SimpleStringProperty();
        sp.bind(b.textProperty());
        b.setText("shit");
        b.setAnimationType(ButtonAnimationType.BAT_TRANSLATE);

        AnimatedPane ap = new AnimatedPane();
        bp.getChildren().add(ap);
        ap.setPrefSize(300, 300);
        ap.show(new Rectangle(100, 100, Paint.valueOf("#ff0000")));
        b.setOnAction(e -> ap.show(
                PaneAnimationDirection.BOTTOM_TO_TOP, new Rectangle(100, 100, Paint.valueOf("#00ff00"))));
        primaryStage.setScene(new Scene(bp));

        primaryStage.show();
    }

    public static void a_() {
        launch("");
    }
}