package sdd.team1.david.util;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MyTaskExecutor
{
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
    MyTask myTask;
    private boolean alreadyExecuted;

    public void cleanup(){
        this.alreadyExecuted=false;
    }

    public MyTaskExecutor(MyTask myTask$)
    {
        myTask = myTask$;
        alreadyExecuted=false;
    }

    public void startExecutionAt(int targetHour, int targetMin, int targetSec)
    {
        Runnable taskWrapper = () -> {
            if(!alreadyExecuted){
                myTask.execute();
                alreadyExecuted=true;
            }
            startExecutionAt(targetHour, targetMin, targetSec);
        };
        long delay = computeNextDelay(targetHour, targetMin, targetSec);
        executorService.schedule(taskWrapper, delay, TimeUnit.SECONDS);
    }

    private long computeNextDelay(int targetHour, int targetMin, int targetSec)
    {
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.systemDefault();
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNextTarget = zonedNow.withHour(targetHour).withMinute(targetMin).withSecond(targetSec);
        if(zonedNow.compareTo(zonedNextTarget) > 0) {
            zonedNextTarget = zonedNextTarget.plusDays(1);
        }

        Duration duration = Duration.between(zonedNow, zonedNextTarget);
        return duration.getSeconds();
    }

    public void stop()
    {
        executorService.shutdown();
        cleanup();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
           // Logger.getLogger(MyTaskExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}