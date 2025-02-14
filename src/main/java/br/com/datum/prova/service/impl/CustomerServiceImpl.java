package br.com.datum.prova.service.impl;

import br.com.datum.prova.dto.CustomerDto;
import br.com.datum.prova.entities.Customer;
import br.com.datum.prova.exception.ResourceNotFoundException;
import br.com.datum.prova.repository.CustomerRepository;
import br.com.datum.prova.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> getByEmail(String email) {
        return Optional.ofNullable(customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado")));
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void createCustomer(CustomerDto req) {
        var cus = new Customer();
        cus.setAddress(req.getAddress());
        cus.setEmail(req.getEmail());
        cus.setName(req.getName());
        customerRepository.save(cus);
    }

    @Override
    @Transactional
    public void patchCustomerEmail(Long id, String email) {
        customerRepository.findById(id).ifPresentOrElse(
                cus -> {
                    cus.setEmail(email);
                    customerRepository.save(cus);
                },
                () -> {
                    throw new ResourceNotFoundException("Cliente não encontrado");
                }
        );
    }

    @Override
    public void updateCustomer(Long id, CustomerDto dto) {
        customerRepository.findById(id).ifPresentOrElse(
                cus -> {
                    cus.setEmail(dto.getEmail());
                    cus.setName(dto.getName());
                    cus.setAddress(dto.getAddress());
                    customerRepository.save(cus);
                },
                () -> {
                    throw new ResourceNotFoundException("Cliente não encontrado");
                }
        );
    }

    @Override
    public Page<CustomerDto> listCustomers(Pageable pageable) {
        return customerRepository.listCustomerPaginationByQuery(pageable);
    }


}
