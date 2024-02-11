package io.github.chiangkaishek327;

import io.github.chiangkaishek327.animatedfx.AnimatedButton;
import io.github.chiangkaishek327.animatedfx.AnimatedPane;
import io.github.chiangkaishek327.animatedfx.AnimatedPane.MoveDirection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Rnr extends Application {
    AnimatedPane ap = new AnimatedPane();

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnimatedButton bb = new AnimatedButton();
        AnimatedButton bc = new AnimatedButton();
        AnimatedButton bd = new AnimatedButton();
        AnimatedButton be = new AnimatedButton();
        AnimatedButton bf = new AnimatedButton();
        bf.setText("iav");
        bf.setOnAction(e -> {
            ap.update(ap.getWidth(), ap.getHeight());
            ap.animatedSetVisible(false, MoveDirection.MD_BOTTOM_TO_TOP);
            new Thread(() -> {
                try {
                    Thread.sleep((long) ap.getAnimationLength().toMillis());
                    ap.animatedSetVisible(true, MoveDirection.MD_BOTTOM_TO_TOP);
                } catch (InterruptedException e1) {

                    e1.printStackTrace();
                }
            }).start();

        });
        VBox ct = new VBox();
        bb.setText("tob");
        bc.setText("bot");
        bd.setText("lor");
        be.setText("rol");
        bb.setOnAction(e -> {
            ap.update(ap.getWidth(), ap.getHeight());
            ap.setGraphic(ct, MoveDirection.MD_TOP_TO_BOTTOM);

        });

        bc.setOnAction(e -> {
            ap.update(ap.getWidth(), ap.getHeight());
            ap.setGraphic(ct, MoveDirection.MD_BOTTOM_TO_TOP);

        });
        bd.setOnAction(e -> {
            ap.update(ap.getWidth(), ap.getHeight());
            ap.setGraphic(ct, MoveDirection.MD_LEFT_TO_RIGHT);

        });
        be.setOnAction(e -> {
            ap.update(ap.getWidth(), ap.getHeight());
            ap.setGraphic(ct, MoveDirection.MD_RIGHT_TO_LEFT);

        });
        ct.getChildren().addAll(bb, bc, bd, be, bf);
        ct.getChildren().forEach(e -> ((Button) e).setPrefHeight(30));
        ap.setCenter(ct);
        Scene scene = new Scene(ap);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @SuppressWarnings("all")
    public static void l() {
        launch(null);
    }
}
