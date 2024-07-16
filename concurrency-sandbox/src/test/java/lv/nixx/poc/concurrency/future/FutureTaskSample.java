package lv.nixx.poc.concurrency.future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FutureTaskSample {

    //Code to execute in Thread
    private final Callable<String> callableTask = () -> "Response from thread";

    @Test
    void futureTaskSample() {
        FutureTask<String> futureTask = new FutureTask<>(callableTask);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(futureTask);

        try {
            //Current method is blocked until we receive the response
            String result = futureTask.get();
            assertEquals("Response from thread", result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    @Test
    void scheduledFutureSample() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        ScheduledFuture<String> scheduledFuture = scheduledExecutorService.schedule(callableTask, 1, TimeUnit.SECONDS);
        try {
            //Current method is blocked until we receive the response
            String result = scheduledFuture.get();
            assertEquals("Response from thread", result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            scheduledExecutorService.shutdown();
        }
    }

}
