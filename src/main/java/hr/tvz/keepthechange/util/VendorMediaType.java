package hr.tvz.keepthechange.util;

import org.springframework.http.MediaType;

/**
 * Contains vendor specific {@link MediaType}s.
 */
public class VendorMediaType {

    public static final String APPLICATION_MS_EXCEL_VALUE = "application/vnd.ms-excel";
    public static final MediaType APPLICATION_MS_EXCEL;
    public static final String APPLICATION_MS_EXCEL_2007_VALUE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final MediaType APPLICATION_MS_EXCEL_2007;

    static {
        APPLICATION_MS_EXCEL = new MediaType("application", "vnd.ms-excel");
        APPLICATION_MS_EXCEL_2007 = new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
}
