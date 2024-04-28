package io.github.chiangkaishek327.animatedfx.utils;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.Node;

public class Utils {
    public static void playSequenitalTransition(Node node, Transition... transitions) {
        SequentialTransition sequenitalTransition = new SequentialTransition(node);
        sequenitalTransition.getChildren().addAll(transitions);
        sequenitalTransition.play();
    }

    public static void playParallelTransition(Node node, Transition... transitions) {
        ParallelTransition parallelTransition = new ParallelTransition(node);
        parallelTransition.getChildren().addAll(transitions);
        parallelTransition.play();
    }
}
