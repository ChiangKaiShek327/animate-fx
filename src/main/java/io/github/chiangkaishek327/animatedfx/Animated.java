package io.github.chiangkaishek327.animatedfx;

import javafx.util.Duration;

import javafx.animation.Transition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;

public interface Animated {
    /**
     * 
     * @return all transitions need to be used
     */
    public Transition[] getTransitions();

    /**
     * 
     * @return animation length, like: the length of the time was needed by moving
     *         a node
     */
    public Duration getAnimationLength();

    /**
     * 
     * @param length animation length
     */
    public void setAnimationLength(Duration length);

    /**
     * 
     * @return animation changing range, like: the scale of the node
     */
    public double getAnimationRange();

    /**
     * 
     * @param range animation changing range
     */
    public void setAnimationRange(double range);

    /**
     * 
     * @return like this:transitions.setByX(114);
     */
    public double getAnimationByRange();

    /**
     * 
     * @param range like this:transitions.setByX(114);
     */
    public void setAnimationByRange(double range);

    /**
     * 
     * @return animation length property
     */
    public ObjectProperty<Duration> animationLengthProperty();

    /**
     * 
     * @return animation range property
     */
    public DoubleProperty animationRangeProperty();
}
