package pip.pip4.mbeans;

import javafx.util.Pair;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.util.HashMap;

@ManagedResource
@Component
public class PointsCounter extends NotificationBroadcasterSupport implements PointsCounterMBean {
    HashMap<String, Pair<Long, Long>> userPointsCount;
    long notificationCount;

    public PointsCounter() {
        userPointsCount = new HashMap<>();
        notificationCount = 0;
    }

    @ManagedOperation
    public void incrementUserPointsCounter(String username, boolean isPointInArea) {
        Pair<Long, Long> count = userPointsCount.getOrDefault(username, new Pair<>(0L, 0L));
        long pointsCount = count.getKey() + 1;
        if (pointsCount % 10 == 0)
            sendNotification(new Notification("mbeans.PointsCounter",
                    this,
                    notificationCount++,
                    username + " points count = " + pointsCount));
        Pair<Long, Long> newCount = new Pair<>(pointsCount, isPointInArea ? count.getValue() + 1 : count.getValue());
        userPointsCount.put(username, newCount);
    }
}
