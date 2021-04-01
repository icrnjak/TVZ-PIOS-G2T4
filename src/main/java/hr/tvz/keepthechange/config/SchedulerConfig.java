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

    @Bean
    public JobDetail monthlyReportJobDetail() {
        return JobBuilder.newJob()
                .ofType(MonthlyReportJob.class)
                .withIdentity("monthlyReportJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger monthlyReportJobTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(QuartzTriggers.MONTHLY_REPORT);

        return TriggerBuilder.newTrigger()
                .forJob(monthlyReportJobDetail())
                .withIdentity("monthlyReportJobTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail deleteMonthlyReportJobDetail() {
        return JobBuilder.newJob()
                .ofType(DeleteMonthlyReportJob.class)
                .withIdentity("deleteMonthlyReportJob")
                .storeDurably()
                .build();
    }

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
