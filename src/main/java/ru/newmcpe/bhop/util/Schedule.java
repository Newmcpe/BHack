package ru.newmcpe.bhop.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Schedule {
    public static ScheduledExecutorService executorService = Executors
            .newScheduledThreadPool(25);

    public static void runWithDelay(Runnable runnable, long delay, TimeUnit unit) {
        executorService.schedule(runnable, delay, unit);
    }

    public static ScheduledFuture runRepeatingTask(Runnable runnable, long delay, TimeUnit unit) {
        return executorService.scheduleWithFixedDelay(runnable, 0, delay, unit);
    }

    public static void runRepeatingTask(Runnable runnable, long initialDelay, long delay, TimeUnit unit) {
        executorService.scheduleWithFixedDelay(runnable, initialDelay, delay, unit);
    }

    public static void run(Runnable runnable) {
        executorService.schedule(runnable, 0, TimeUnit.SECONDS);
    }
}
