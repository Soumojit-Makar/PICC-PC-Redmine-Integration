package com.nnp.redmineintegration.utils;
public final class LogUtils {

    private LogUtils() {
        // Utility class
    }

    /**
     * Sanitizes untrusted data before writing it to application logs.
     * Removes CR, LF and other line-separator characters that could
     * allow log-entry injection.
     */
    public static String sanitizeForLog(String value) {
        if (value == null) {
            return null;
        }

        return value
                .replace("\r", "")
                .replace("\n", "")
                .replace("\u2028", "")
                .replace("\u2029", "");
    }
}

