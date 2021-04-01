package hr.tvz.keepthechange.quartzjob;

import hr.tvz.keepthechange.service.MonthlyReportService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.nio.file.Path;
import java.time.YearMonth;
import java.util.Optional;

/**
 * Quartz job which deletes all monthly reports except monthly report generated for previous month.
 * <p>
 * If current year month  is 2021-04, this job will delete existing reports for all year months except 2021-03.
 * </p>
 * <p>
 * It should be run after a corresponding {@link MonthlyReportJob}.
 * </p>
 */
public class DeleteMonthlyReportJob extends QuartzJobBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteMonthlyReportJob.class);
    private final MonthlyReportService monthlyReportService;

    public DeleteMonthlyReportJob(MonthlyReportService monthlyReportService) {
        this.monthlyReportService = monthlyReportService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("Starting delete monthly report job");

        long deleteCount = monthlyReportService.walkFiles()
                .filter(this::isNotReportForPreviousMonth)
                .map(monthlyReportService::delete)
                .filter(b -> b)
                .count();
        LOGGER.info("Deleted {} monthly report files", deleteCount);

        LOGGER.info("Delete monthly report job successfully finished");
    }

    private boolean isNotReportForPreviousMonth(String stringPath) {
        return Optional.of(stringPath)
                .map(Path::of)
                .map(Path::getFileName)
                .map(Path::toString)
                .map(fileName -> fileName.split("\\.")[0])
                .map(fileNameNoExtension -> fileNameNoExtension.split("-", 4))
                .map(fileNameSplit -> fileNameSplit[fileNameSplit.length - 1])
                .map(YearMonth::parse)
                .filter(ym -> !YearMonth.now().minusMonths(1).equals(ym))
                .isPresent();
    }
}
