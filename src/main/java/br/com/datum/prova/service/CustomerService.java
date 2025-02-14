package br.com.datum.prova.service;

import br.com.datum.prova.dto.CustomerDto;
import br.com.datum.prova.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> getByEmail(String email);

    void deleteCustomerById(Long id);

    void createCustomer(CustomerDto req);

    void patchCustomerEmail(Long id, String email);

    void updateCustomer(Long id, CustomerDto dto);

    Page<CustomerDto> listCustomers(Pageable pageable);
}
