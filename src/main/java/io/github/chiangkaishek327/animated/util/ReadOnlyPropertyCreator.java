package io.github.chiangkaishek327.animated.util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class ReadOnlyPropertyCreator {
    public static <T> ReadOnlyObjectProperty<T> createObjectProperty(ObjectProperty<T> objectProperty) {
        ReadOnlyObjectWrapper<T> roow = new ReadOnlyObjectWrapper<>();
        roow.bind(objectProperty);
        return roow.getReadOnlyProperty();
    }

    public static ReadOnlyDoubleProperty createDoubleProperty(DoubleProperty doubleProperty) {
        ReadOnlyDoubleWrapper rodw = new ReadOnlyDoubleWrapper();
        rodw.bind(doubleProperty);
        return rodw.getReadOnlyProperty();

    }

}