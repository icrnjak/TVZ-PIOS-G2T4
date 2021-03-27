package hr.tvz.keepthechange.quartzjob;

import hr.tvz.keepthechange.entity.Transaction;
import hr.tvz.keepthechange.service.MonthlyReportService;
import hr.tvz.keepthechange.service.TransactionService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Quartz job which creates a xlsx report containing all incomes and expenses for a previous month.
 */
public class MonthlyReportJob extends QuartzJobBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonthlyReportJob.class);
    private final TransactionService transactionService;
    private final MonthlyReportService monthlyReportService;

    public MonthlyReportJob(TransactionService transactionService,
                            MonthlyReportService monthlyReportService) {
        this.transactionService = transactionService;
        this.monthlyReportService = monthlyReportService;
    }

    /**
     * Fetches all transactions for the previous month, groups them by wallets (their ID value), and
     * delegates creation of actual reports to the {@link MonthlyReportService}.
     *
     * @see QuartzJobBean#executeInternal(JobExecutionContext)
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("Starting monthly report job");

        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        long generatedFilesCount = transactionService.getAllByYearMonth(previousMonth)
                .parallelStream()
                .collect(Collectors.collectingAndThen(Collectors.groupingByConcurrent(Transaction::getWalletId, Collectors.toList()), Map::entrySet))
                .parallelStream()
                .map(entry -> monthlyReportService.generate(entry.getKey(), entry.getValue()))
                .filter(b -> b)
                .count();
        LOGGER.info("Generated {} monthly reports for month {}", generatedFilesCount, previousMonth);

        LOGGER.info("Monthly report job successfully finished");
    }
}
