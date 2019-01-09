import edu.wpi.first.wpilibj.command.Scheduler;

public class SchedulerTask extends Thread {
  private final Scheduler m_scheduler;
  private final long pulse = 20;

  public SchedulerTask(Scheduler scheduler) {
    super();
    if (scheduler == null) {
      throw new NullPointerException("scheduler cannot be null. Use Scheduler.getInstance().");
    }
    m_scheduler = scheduler;
  }

  public void run() {
    long nextRunTime = nextPulse();
    while (true) {
      if (System.currentTimeMillis() >= nextRunTime) {
        nextRunTime = nextPulse();
        m_scheduler.run();
      }
    }
  }

  private long nextPulse() {
    return System.currentTimeMillis() + pulse;
  }
}
