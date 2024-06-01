package io.github.chiangkaishek327.animated.util;

import javafx.animation.Transition;

public abstract class DoSthTransition extends Transition {
    boolean did = false;

    @Override
    protected void interpolate(double frac) {
        if (!did) {
            something();
            did = true;
        }
    }

    protected abstract void something();

    public void reuse() {
        did = false;
    }

    public static DoSthTransition newDST(Runnable thing) {
        return new DoSthTransition() {

            @Override
            protected void something() {
                thing.run();
            }

        };
    }
}
