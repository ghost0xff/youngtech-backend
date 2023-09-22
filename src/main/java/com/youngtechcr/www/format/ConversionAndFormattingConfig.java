package com.youngtechcr.www.format;


import com.youngtechcr.www.product.StringToProductAttributeConverter;
import com.youngtechcr.www.security.user.StringToUserTypeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

@Configuration
public class ConversionAndFormattingConfig {

    private final StringToUserTypeConverter stringToUserTypeConverter;
    private final StringToProductAttributeConverter stringToProductAttributeConverter;

    public ConversionAndFormattingConfig(
            StringToUserTypeConverter stringToUserTypeConverter,
            StringToProductAttributeConverter stringToProductAttributeConverter) {
        this.stringToUserTypeConverter = stringToUserTypeConverter;
        this.stringToProductAttributeConverter = stringToProductAttributeConverter;
    }

    @Bean
    public FormattingConversionService conversionService() {
        // Use the DefaultFormattingConversionService AND DO register defaults
        DefaultFormattingConversionService conversionService =
                new DefaultFormattingConversionService(true);
        conversionService.addConverter(stringToUserTypeConverter);
        conversionService.addConverter(stringToProductAttributeConverter);
        return conversionService;
    }
}
