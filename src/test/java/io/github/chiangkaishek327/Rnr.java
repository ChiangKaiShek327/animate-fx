package io.github.chiangkaishek327;

import io.github.chiangkaishek327.animatedfx.AnimatedButton;
import io.github.chiangkaishek327.animatedfx.AnimatedPane;
import io.github.chiangkaishek327.animatedfx.AnimatedPane.MoveDirection;
import io.github.chiangkaishek327.animatedfx.tabpane.AnimatedTab;
import io.github.chiangkaishek327.animatedfx.tabpane.AnimatedTabPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Rnr extends Application {
    AnimatedPane ap = new AnimatedPane();

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnimatedButton bf = new AnimatedButton();
        VBox ct = new VBox();
        if (true) {
            AnimatedTabPane<String> atp = new AnimatedTabPane<>();
            bf.setText("iav");
            bf.setOnAction(e -> {
                ap.update(ap.getWidth(), ap.getHeight());
                ap.animatedSetVisible(false, MoveDirection.MD_BOTTOM_TO_TOP);
                new Thread(() -> {
                    try {
                        Thread.sleep((long) ap.getAnimationLength().toMillis());
                        ap.animatedSetVisible(true, MoveDirection.MD_TOP_TO_BOTTOM);
                    } catch (InterruptedException e1) {

                        e1.printStackTrace();
                    }
                }).start();

            });

            ct.getChildren().addAll(bf);
            ct.getChildren().forEach(e -> ((Button) e).setPrefHeight(30));
            {
                AnimatedTab<String> at = new AnimatedTab<>("redt");
                Rectangle r0 = new Rectangle(400, 400);
                r0.setFill(Paint.valueOf("#ff0000"));
                Rectangle r1 = new Rectangle(400, 400);
                r1.setFill(Paint.valueOf("#0000ff"));

                Rectangle r2 = new Rectangle(400, 400);
                r2.setFill(Paint.valueOf("#00ff00"));
                at.setContent(new AnchorPane(r0));
                AnimatedTab<String> at2 = new AnimatedTab<>("blut");
                at2.setContent(new AnchorPane(r1));
                AnimatedTab<String> at3 = new AnimatedTab<>("gret");
                at3.setContent(new AnchorPane(r2));
                at3.setCloseable(true);
                atp.addTabs(at, at2, at3);
            }
            ap.setTop(ct);
            ap.setCenter(new Rectangle(100, 100));
            ap.getStylesheets()
                    .add(Rnr.class.getResource("s3t.css").toExternalForm());
            Scene scene = new Scene(ap);
            primaryStage.setScene(scene);
            primaryStage.show();
            Thread.sleep(1000);
            atp.update(100, 100);
            ap.setGraphic(atp, MoveDirection.MD_BOTTOM_TO_TOP);
        }
    }

    @SuppressWarnings("all")
    public static void l() {
        launch(null);
    }
}
