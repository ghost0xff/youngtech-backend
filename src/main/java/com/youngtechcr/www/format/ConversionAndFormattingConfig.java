package com.youngtechcr.www.format;


import com.youngtechcr.www.security.user.StringToUserTypeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

@Configuration
public class ConversionAndFormattingConfig {
    private final StringToUserTypeConverter stringToUserTypeConverter;

    public ConversionAndFormattingConfig(StringToUserTypeConverter stringToUserTypeConverter) {
        this.stringToUserTypeConverter = stringToUserTypeConverter;
    }

    @Bean
    public FormattingConversionService conversionService() {

        // Use the DefaultFormattingConversionService AND DO register defaults
        DefaultFormattingConversionService conversionService =
                new DefaultFormattingConversionService(true);

//         Ensure @NumberFormat is still supported
//        conversionService.addFormatterForFieldAnnotation(
//                new NumberFormatAnnotationFormatterFactory());

        // Register JSR-310 date conversion with a specific global format
//        DateTimeFormatterRegistrar dateTimeRegistrar = new DateTimeFormatterRegistrar();
//        dateTimeRegistrar.setUseIsoFormat(true);
//        dateTimeRegistrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        dateTimeRegistrar.registerFormatters(conversionService);

        // Register date conversion with a specific global format
//        DateFormatterRegistrar dateRegistrar = new DateFormatterRegistrar();
//        dateRegistrar.setFormatter(new DateFormatter("yyyy-MM-dd"));
//        dateRegistrar.registerFormatters(conversionService);
        conversionService.addConverter(stringToUserTypeConverter);
        return conversionService;
    }
}
