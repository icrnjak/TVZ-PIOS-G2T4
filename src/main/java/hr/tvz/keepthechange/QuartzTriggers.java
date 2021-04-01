package hr.tvz.keepthechange;

/**
 * Contains CRON triggers for quartz jobs.
 * <p>
 * REMINDER: CRON trigger syntax goes like this: <br/>
 * second minute hour dayOfMonth month dayOfWeek year
 * </p>
 */
public final class QuartzTriggers {
    public static final String MONTHLY_REPORT = "0 0 0 1 * ? *";
    public static final String DELETE_MONTHLY_REPORT = "0 0 2 1 * ? *";
}
