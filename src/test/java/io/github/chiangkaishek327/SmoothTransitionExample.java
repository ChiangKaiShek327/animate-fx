package io.github.chiangkaishek327;

import io.github.chiangkaishek327.animated.control.button.AnimatedButton;
import io.github.chiangkaishek327.animated.control.button.ButtonAnimationGroup.ButtonAnimationType;
import io.github.chiangkaishek327.animated.control.pane.AnimatedPane;
import io.github.chiangkaishek327.animated.control.pane.PaneAnimationGroup.PaneAnimationDirection;
import io.github.chiangkaishek327.animated.control.tabpane.AnimatedTab;
import io.github.chiangkaishek327.animated.control.tabpane.AnimatedTabPane;
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

        StringProperty sp = new SimpleStringProperty();
        sp.bind(b.textProperty());
        b.setText("shit");
        b.setAnimationType(ButtonAnimationType.BAT_TRANSLATE);

        AnimatedTabPane atp = new AnimatedTabPane();
        atp.setPrefHeight(300);
        atp.setPrefWidth(400);
        AnimatedTab at = new AnimatedTab("red", atp, new Rectangle(100, 100, Paint.valueOf("#ff0000")));
        AnimatedTab at2 = new AnimatedTab("blue", atp, new Rectangle(100, 100, Paint.valueOf("#0000ff")));

        AnimatedTab at3 = new AnimatedTab("green", atp, new Rectangle(100, 100, Paint.valueOf("#00ff00")));

        AnimatedTab at4 = new AnimatedTab("yellow", atp, new Rectangle(100, 100, Paint.valueOf("#ffff00")));

        AnimatedTab at5 = new AnimatedTab("black", atp, new Rectangle(100, 100, Paint.valueOf("#000000")));

        VBox bp = new VBox(b, atp);
        bp.getStylesheets().add(SmoothTransitionExample.class.getResource("test.css").toExternalForm());
        primaryStage.setScene(new Scene(bp));

        primaryStage.show();
    }

    public static void a_() {
        launch("");
    }
}