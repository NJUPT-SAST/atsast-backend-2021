package com.sast.atSast.util;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/09/11/20:56
 * @Description:
 */
public class RandomUtils {
    private static boolean[] randomArr = new boolean[]{true, false, false, false, false, true, false, false, false, false};

    //有20%返回true
    public boolean getRandomResult20PerTrue() {
        return randomArr[(int) Math.random() * 10];
    }
}
