package io.github.chiangkaishek327.animated.util;

import java.util.ArrayList;
import java.util.List;

public class OtherUtil {
    public static final String DEFAULT_TEXT = "default text";

    public static <T> List<T> getOtherElements(List<T> list, int index) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i != index) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    public static void delayConviently(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
