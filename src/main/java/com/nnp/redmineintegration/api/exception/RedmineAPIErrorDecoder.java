package com.nnp.redmineintegration.api.exception;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nnp.redmineintegration.utils.LogUtils;
import org.apache.logging.log4j.core.util.IOUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedmineAPIErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        Reader reader = null;
        Map<String, Object> errorMessageMap = new HashMap<>();

        try {
            if (response.body() != null) {
                reader = response.body().asReader(StandardCharsets.UTF_8);
                String result = IOUtils.toString(reader);

                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

                errorMessageMap = mapper.readValue(
                        result,
                        new TypeReference<Map<String, Object>>() {
                        }
                );
            }
        } catch (IOException e) {
            log.error(
                    "IO Exception on reading exception message feign client : {}",
                    LogUtils.sanitizeForLog(e.getLocalizedMessage())
            );
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(
                            "IO Exception on closing exception message reader : {}",
                            LogUtils.sanitizeForLog(e.getLocalizedMessage())
                    );
                }
            }
        }

        RedmineAPIException redmineAPIException = new RedmineAPIException();

        // WMI_WRONG_MAP_ITERATOR fixed by using entrySet()
        for (Map.Entry<String, Object> entry : errorMessageMap.entrySet()) {

            String key = entry.getKey();
            Object value = entry.getValue();

            if ("status".equalsIgnoreCase(key)) {
                if (value instanceof Number) {
                    redmineAPIException.setStatus(((Number) value).intValue());
                }
            }

            if ("errors".equalsIgnoreCase(key) || "error".equalsIgnoreCase(key)) {

                if (value instanceof String) {
                    redmineAPIException.setMessage((String) value);

                } else if (value instanceof List<?> list && !list.isEmpty()) {
                    Object firstValue = list.get(0);

                    if (firstValue != null) {
                        redmineAPIException.setMessage(firstValue.toString());
                    }
                }
            }
        }

        if (redmineAPIException.getStatus() == 0) {
            redmineAPIException.setStatus(500);
        }

        return redmineAPIException;
    }
}

