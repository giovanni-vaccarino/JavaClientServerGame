package polimi.ingsoft.server.common;

import polimi.ingsoft.server.common.MatchServer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MatchTimer {
    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> timerHandle;
    private final Runnable timerTask;

    public MatchTimer(MatchServer matchServer) {
        scheduler = Executors.newScheduledThreadPool(1);
        timerTask = matchServer::notifyTimerEnd;
    }

    public void startTimer(Integer delayInSeconds) {
        stopTimer();

        timerHandle = scheduler.schedule(timerTask, delayInSeconds, TimeUnit.SECONDS);
    }

    public void stopTimer() {
        if (timerHandle != null && !timerHandle.isDone()) {
            timerHandle.cancel(true);
        }
    }
}
