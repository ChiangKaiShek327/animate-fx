package io.github.chiangkaishek327;

import java.io.FileInputStream;
import java.util.Random;

import io.github.chiangkaishek327.animated.control.label.AnimatedLabel;
import io.github.chiangkaishek327.animated.control.pane.AnimatedPane;
import io.github.chiangkaishek327.animated.control.pane.PaneAnimationGroup.PaneAnimationType;
import io.github.chiangkaishek327.animated.control.tabpane.AnimatedTab;
import io.github.chiangkaishek327.animated.control.tabpane.AnimatedTabPane;
import io.github.chiangkaishek327.animated.control.tabpane.AnimatedTabPane.HeaderSide;
import io.github.chiangkaishek327.animated.util.OtherUtil;
import io.github.chiangkaishek327.animated.util.SVGLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SmoothTransitionExample extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        AnimatedTabPane atp = new AnimatedTabPane();
        atp.setPrefHeight(500);
        atp.setPrefWidth(700);
        AnimatedTab at = new AnimatedTab("red", new Rectangle(100, 100, Paint.valueOf("#ff0000")));
        AnimatedTab at2 = new AnimatedTab("blue", new Rectangle(100, 100, Paint.valueOf("#0000ff")));

        AnimatedTab at3 = new AnimatedTab("green", new Rectangle(100, 100, Paint.valueOf("#00ff00")));

        AnimatedTab at4 = new AnimatedTab("yellow", new Rectangle(100, 100, Paint.valueOf("#ffff00")));
        AnchorPane ape = new AnchorPane();
        ape.widthProperty().addListener((ob, o, n) -> {
        });
        AnimatedTab at5 = new AnimatedTab("debug", ape);
        AnimatedPane ap = new AnimatedPane();
        ape.setBackground(
                new Background(new BackgroundFill(Paint.valueOf("#abc"), null, null)));
        ap.show(atp);
        FXMLLoader fxl = new FXMLLoader(SmoothTransitionExample.class.getResource("test.fxml"));
        fxl.setBuilderFactory(new JavaFXBuilderFactory());
        fxl.load();
        BorderPane bpw = fxl.getRoot();

        FXMLLoader fxl1 = new FXMLLoader(App.class.getResource("test.fxml"));
        fxl1.setBuilderFactory(new JavaFXBuilderFactory());
        fxl1.load();
        BorderPane bpw2 = fxl1.getRoot();
        AnimatedTab at7 = new AnimatedTab("FXML test", bpw2);
        at7.setCloseable(true);
        atp.getTabs().addAll(at7, at5, at2, at3, at4, at);
        atp.setDuration(Duration.millis(100));
        atp.getContentPane().setAnimationType(PaneAnimationType.PAT_OAT);
        at.setIcon(new Image(App.class.getResourceAsStream("example.png")));
        AnimatedLabel label = new AnimatedLabel();
        label.setPrefSize(300, 300);

        label.setLayoutX(800);
        primaryStage.setScene(new Scene(new AnchorPane(atp)));
        primaryStage.show();
    }

    public static void a_() {
        launch("");
    }
}