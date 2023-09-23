package com.youngtechcr.www.customer;

import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.security.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory
            .getLogger(CustomerService.class);
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public Customer find(Integer id)  {
        return this
                .customerRepository
                .findById(id)
                .orElseThrow( () -> {
                    logger.warn("Could not find requested customer");
                    return new NoDataFoundException(
                        HttpErrorMessages
                        .NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND
                    );
                });
    }
    @Transactional(readOnly = true)
    public Customer find(User user)  {
        return this
                .customerRepository
                .findByUser(user)
                .orElseThrow( () -> {
                    logger.warn("Could not find requested customer");
                    return new NoDataFoundException(
                            HttpErrorMessages
                                    .NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND
                    );
                });
    }

    @Transactional
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

}
