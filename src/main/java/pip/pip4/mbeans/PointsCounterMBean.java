package pip.pip4.mbeans;

public interface PointsCounterMBean {
    void incrementUserPointsCounter(String username, boolean isPointInArea);
}
