package com.chat.worker;

import java.util.concurrent.*;

public class WorkerPool {
    private static final ExecutorService POOL = Executors.newFixedThreadPool(4);

    public static void submit(Runnable task) {
        POOL.submit(task);
    }
}
