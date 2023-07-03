package com.youngtechcr.www.regex;

import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.InternalErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class RegexService {

    private static final Logger log = LoggerFactory.getLogger(RegexService.class);

    public boolean matches(String regex, String text) {
        if (regex == null) {
            log.debug(InternalErrorMessages.NULL_REGEX_PATTERN_VALUE);
            throw new RegexMatchingException(HttpErrorMessages.NULL_REGEX_PATTERN_OR_TEXT_VALUE);
        }
        else if (text == null) {
            log.debug(InternalErrorMessages.NULL_REGEX_TEXT_VALUE);
            throw new RegexMatchingException(HttpErrorMessages.NULL_REGEX_PATTERN_OR_TEXT_VALUE);
        }
        return Pattern.matches(regex, text);
    }
}
