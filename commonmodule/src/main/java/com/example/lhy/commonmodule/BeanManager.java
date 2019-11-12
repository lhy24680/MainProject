package com.example.lhy.commonmodule;

/**
 * description :
 * author : lihaiyang
 * date : 2019/10/9 16:22
 */

public class BeanManager {
    private static Bean bean;

    public static void init(Bean bean) {
        BeanManager.bean = bean;
    }

    public static Bean getInstance() {
        return bean;
    }
}
