package io.github.chiangkaishek327.animatedfx;

public interface ResizableAnimated extends Animated {
    /**
     * use this method before playing some animation
     * 
     * @param width  parent width
     * @param height parent height
     */
    public void update(double width, double height);

}
