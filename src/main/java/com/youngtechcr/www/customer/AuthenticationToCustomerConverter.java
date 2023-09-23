package com.youngtechcr.www.customer;

import com.youngtechcr.www.security.user.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationToCustomerConverter
        implements Converter<Authentication, Customer> {

    private final CustomerService customerService;
    private final UserService userService;

    public AuthenticationToCustomerConverter(
            CustomerService customerService,
            UserService userService
    ) {
        this.userService = userService;
        this.customerService = customerService;
    }

    @Override
    @Transactional(readOnly = true)
    public Customer convert(Authentication source) {
        var user = userService
                .findById(Integer
                        .parseInt(source.getName())
                );
        return customerService.find(user);
    }
}
