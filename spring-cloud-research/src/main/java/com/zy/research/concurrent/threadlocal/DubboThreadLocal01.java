package com.zy.research.concurrent.threadlocal;

import org.apache.dubbo.common.threadlocal.InternalThreadLocal;

public class DubboThreadLocal01 {
    private static final InternalThreadLocal<Integer> INTERNAL_THREAD_LOCAL = new InternalThreadLocal<> ();

    public static void main(String[] args) {
        // dubbo 的 InternalThreadLocal 参考了 netty 中的设计
        // 最佳实践: 在 finally 块中 remove
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    INTERNAL_THREAD_LOCAL.set(i);
                    System.out.println(Thread.currentThread().getName() + " --> " + INTERNAL_THREAD_LOCAL.get());
                }
            } finally {
                INTERNAL_THREAD_LOCAL.remove();
            }
        }, "t1").start();
    }
}
