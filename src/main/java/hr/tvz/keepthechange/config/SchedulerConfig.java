package hr.tvz.keepthechange.config;

import hr.tvz.keepthechange.QuartzTriggers;
import hr.tvz.keepthechange.quartzjob.DeleteMonthlyReportJob;
import hr.tvz.keepthechange.quartzjob.MonthlyReportJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures quartz scheduler and quartz jobs.
 */
@Configuration
public class SchedulerConfig {

    /**
     * Returns a {@link JobDetail} interface for monthly report
     * @return job detail interface
     */
    @Bean
    public JobDetail monthlyReportJobDetail() {
        return JobBuilder.newJob()
                .ofType(MonthlyReportJob.class)
                .withIdentity("monthlyReportJob")
                .storeDurably()
                .build();
    }

    /**
     * Returns a {@link Trigger} making it possible to periodically schedule monthly report creating
     * @return chron trigger for creating monthly report
     */
    @Bean
    public Trigger monthlyReportJobTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(QuartzTriggers.MONTHLY_REPORT);

        return TriggerBuilder.newTrigger()
                .forJob(monthlyReportJobDetail())
                .withIdentity("monthlyReportJobTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    /**
     * Remove instance of monthly report
     * @return {@link JobDetail}
     */
    @Bean
    public JobDetail deleteMonthlyReportJobDetail() {
        return JobBuilder.newJob()
                .ofType(DeleteMonthlyReportJob.class)
                .withIdentity("deleteMonthlyReportJob")
                .storeDurably()
                .build();
    }

    /**
     * Periodically remove instance of monthly report
     * @return trigger builder
     */
    @Bean
    public Trigger deleteMonthlyReportJobTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(QuartzTriggers.DELETE_MONTHLY_REPORT);

        return TriggerBuilder.newTrigger()
                .forJob(deleteMonthlyReportJobDetail())
                .withIdentity("deleteMonthlyReportJobTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
