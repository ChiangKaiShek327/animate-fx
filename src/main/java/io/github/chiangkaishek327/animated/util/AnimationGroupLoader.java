package io.github.chiangkaishek327.animated.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import io.github.chiangkaishek327.animated.animation.Animated;
import io.github.chiangkaishek327.animated.animation.AnimationGroup;

public class AnimationGroupLoader<T extends Animated> {
    T owner;

    public AnimationGroupLoader(T owner) {
        this.owner = owner;
    }

    public AnimationGroup<T> load(Class<? extends AnimationGroup<T>> animationGroupClass)
            throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Constructor<? extends AnimationGroup<T>> constructor = animationGroupClass.getConstructor(owner.getClass());
        return constructor.newInstance(owner);
    }

}
