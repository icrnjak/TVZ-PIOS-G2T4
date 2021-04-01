package hr.tvz.keepthechange.service;

import hr.tvz.keepthechange.entity.Transaction;
import hr.tvz.keepthechange.util.JxlsUtil;
import org.jxls.common.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Stream;

/**
 * Contains methods for working with monthly reports.
 */
@Service
public class MonthlyReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonthlyReportService.class);
    public static final String MONTHLY_REPORT_FOLDER = "monthlyReport";
    public static final String MONTHLY_REPORT_FILENAME_PATTERN = "monthly-report-{walletId}-{yearMonth}.xlsx";
    private final JxlsService jxlsService;
    private final FileManagerService fileManagerService;

    public MonthlyReportService(JxlsService jxlsService, FileManagerService fileManagerService) {
        this.jxlsService = jxlsService;
        this.fileManagerService = fileManagerService;
    }

    /**
     * Generates a monthly report as XLSX file.
     *
     * @param yearMonth    month of the year
     * @param walletId     id of a wallet for which we are generating a report
     * @param transactions all transactions of a wallet in a given month
     * @return if the was successfully created and saved
     */
    public boolean generate(YearMonth yearMonth, Long walletId, List<Transaction> transactions) {
        LOGGER.debug("Generating monthly report for wallet {} and yearMonth {}", walletId, yearMonth);
        Context context = new Context();
        context.putVar("yearMonth", yearMonth);
        context.putVar("transactions", transactions);
        context.putVar("locale", LocaleContextHolder.getLocale());

        try (InputStream is = new ClassPathResource(JxlsUtil.MONTHLY_REPORT_TEMPLATE).getInputStream()) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                jxlsService.transform(is, baos, context);
                boolean saved = fileManagerService.saveFile(generateFilePath(yearMonth, walletId), baos.toByteArray());
                if (saved) {
                    LOGGER.debug("Monthly report for wallet {} and yearMonth {} generated", walletId, yearMonth);
                }
                return saved;
            }
        } catch (IOException ioException) {
            throw new UncheckedIOException(ioException);
        }
    }

    /**
     * Same as {@link #generate(YearMonth, Long, List)} but yearMonth is set to the previous month of the year.
     *
     * @see #generate(YearMonth, Long, List)
     */
    public boolean generate(Long walletId, List<Transaction> transactions) {
        return generate(getPreviousMonth(), walletId, transactions);
    }

    /**
     * Checks if a monthly report for a given month of the year and wallet id exists.
     *
     * @param yearMonth month of the year
     * @param walletId  id of a wallet
     * @return {@code true} if it exists, {@code false} otherwise
     */
    public boolean exists(YearMonth yearMonth, Long walletId) {
        return fileManagerService.exists(generateFilePath(yearMonth, walletId));
    }

    /**
     * Same as {@link #exists(YearMonth, Long)} but yearMonth is set to the previous month of the year.
     *
     * @see #exists(YearMonth, Long)
     */
    public boolean exists(Long walletId) {
        return exists(getPreviousMonth(), walletId);
    }

    /**
     * Fetches monthly report for a given month of a year and wallet.
     *
     * @param yearMonth month of the year
     * @param walletId  id of a wallet
     * @return monthly report as a {@link Resource}
     */
    public Resource get(YearMonth yearMonth, Long walletId) {
        return fileManagerService.get(generateFilePath(yearMonth, walletId));
    }

    /**
     * Same as {@link #get(YearMonth, Long)} but yearMonth is set to the previous month of the year.
     *
     * @see #get(YearMonth, Long)
     */
    public Resource get(Long walletId) {
        return get(getPreviousMonth(), walletId);
    }

    /**
     * Deletes a monthly report at a given relative path.
     * <p>
     * It is made to be easily used with {@link #walkFiles()}.
     * </p>
     *
     * @param reportPath relative path of a monthly report
     * @return {@code true} if it was deleted successfully, {@code false} otherwise
     * @throws IllegalArgumentException if a given path to a report file is not valid
     */
    public boolean delete(String reportPath) {
        if (!isValidFilePath(reportPath)) {
            throw new IllegalArgumentException("Invalid monthly report file path: " + reportPath);
        }
        return fileManagerService.delete(reportPath);
    }

    /**
     * Returns a stream containing all monthly report files.
     *
     * @return {@link Stream} containing monthly reports
     */
    public Stream<String> walkFiles() {
        return fileManagerService.walkFiles(MONTHLY_REPORT_FOLDER);
    }

    private boolean isValidFilePath(String path) {
        if (!StringUtils.hasText(path)) {
            return false;
        }
        try {
            String[] filenameSplit = Path.of(path).getFileName().toString().split("-");
            Long walletId = Long.valueOf(filenameSplit[2]);
            YearMonth yearMonth = YearMonth.parse(filenameSplit[3] + "-" + filenameSplit[4].substring(0, 2));
            return path.equals(generateFilePath(yearMonth, walletId));
        } catch (Exception e) {
            return false;
        }
    }

    private String generateFilePath(YearMonth yearMonth, Long walletId) {
        return MONTHLY_REPORT_FOLDER +
                "/" +
                walletId +
                "/" +
                generateFilename(yearMonth, walletId);
    }

    private String generateFilename(YearMonth yearMonth, Long walletId) {
        return MONTHLY_REPORT_FILENAME_PATTERN.replace("{walletId}", walletId.toString())
                .replace("{yearMonth}", yearMonth.toString());
    }

    private YearMonth getPreviousMonth() {
        return YearMonth.now().minusMonths(1);
    }
}
