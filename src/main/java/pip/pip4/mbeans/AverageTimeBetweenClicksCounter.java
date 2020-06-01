package pip.pip4.mbeans;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@ManagedResource
@Component
public class AverageTimeBetweenClicksCounter implements AverageTimeBetweenClicksCounterMBean {
    long pointsCount;
    double averageTimeBetweenClicks;
    long lastClickTime;

    public AverageTimeBetweenClicksCounter() {
        pointsCount = 0;
        averageTimeBetweenClicks = 0f;
        lastClickTime = 0;
    }

    @ManagedOperation
    public void updateAverageTimeBetweenClicks(long clickTime) {
        long diffBetweenClicks = clickTime - lastClickTime;
        lastClickTime = clickTime;
        double newAverageTime = (averageTimeBetweenClicks * pointsCount + diffBetweenClicks) / (pointsCount + 1);
        pointsCount++;
        averageTimeBetweenClicks = newAverageTime;
        System.out.println("average time: " + averageTimeBetweenClicks);
    }
}
