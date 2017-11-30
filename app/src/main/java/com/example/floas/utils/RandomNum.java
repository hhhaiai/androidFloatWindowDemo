package com.example.floas.utils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @Copyright © 2015 Umeng Inc. All rights reserved.
 * @Description: 随机生成器
 * @Create: 2015年7月8日 下午6:14:09
 * @Author: sanbo
 */
public class RandomNum {

    private static Random random = new Random(System.nanoTime());

    /**
     * 随机取时间
     *
     * @return
     */
    public static int randomTime(int min, int max) {
        return randomInt(min, max);
    }

    /**
     * 基础方法，随机取int值
     *
     * @return
     */
    public static int randomInt(int min, int max) {
        if (random == null) {
            random = new Random(System.nanoTime());
        }
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 随机数
     * </p>
     * 取值范围: 0<=x<max
     */
    public static int nextInt(int max) {
        return randomInt(0, max - 1);
    }

    /**
     * 取固定范围的double值,四舍五入
     *
     * @return
     */
    public static double randomDouble(double min, double max) {
        if (random == null) {
            random = new Random(System.nanoTime());
        }
        double num = random.nextDouble() * (max - min) + min;
        if (Double.isNaN(num)) {
            return 0;
        }
        BigDecimal bd = new BigDecimal(num);
        num = bd.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

        return num;
    }
}
