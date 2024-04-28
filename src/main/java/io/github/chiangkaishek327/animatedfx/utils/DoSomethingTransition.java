package io.github.chiangkaishek327.animatedfx.utils;

import javafx.animation.Transition;

/**
 * You can ignore this
 */
public abstract class DoSomethingTransition extends Transition {
    boolean done = false;

    @Override
    protected void interpolate(double frac) {
        if (!done) {
            someThingYouWantToDo();
            done = true;
        }
    }

    public void reset() {
        done = false;
    }

    public abstract void someThingYouWantToDo();
}
