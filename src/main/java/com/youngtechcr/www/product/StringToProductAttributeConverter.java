package com.youngtechcr.www.product;

import com.youngtechcr.www.security.user.UserType;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToProductAttributeConverter implements Converter<String, ProductAttribute> {
    @Override
    public ProductAttribute convert(String source) {
        return ProductAttribute.valueOf(source.toUpperCase());
    }
}
