package com.youngtechcr.www.school;

import com.youngtechcr.www.product.ProductAttribute;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;
@Component
public class StringToSchoolMemberTypeConverter implements Converter<String, SchoolMemberType>{
    @Override
    public SchoolMemberType convert(String source) {
        return SchoolMemberType.valueOf(source.toUpperCase());
    }
}
