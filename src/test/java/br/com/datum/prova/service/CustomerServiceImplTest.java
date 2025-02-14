package br.com.datum.prova.service;

import br.com.datum.prova.dto.CustomerDto;
import br.com.datum.prova.entities.Customer;
import br.com.datum.prova.exception.ResourceNotFoundException;
import br.com.datum.prova.repository.CustomerRepository;
import br.com.datum.prova.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;  // Mock do repositório

    @InjectMocks
    private CustomerServiceImpl customerService; // Classe a ser testada

    private Customer customer;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setEmail("teste@exemplo.com");
        customer.setName("Cliente");
        customer.setAddress("Rua dos bobos");

        customerDto = new CustomerDto();
        customerDto.setEmail("new@example.com");
        customerDto.setName("Updated Customer");
        customerDto.setAddress("Updated Address");
    }

    @Test
    void testGetByEmail_CustomerExists() {
        when(customerRepository.findByEmail("teste@exemplo.com")).thenReturn(Optional.of(customer));
        Optional<Customer> result = customerService.getByEmail("teste@exemplo.com");
        assertTrue(result.isPresent(), "O cliente deveria ser encontrado");
        assertEquals("teste@exemplo.com", result.get().getEmail(), "O email do cliente deveria ser 'teste@exemplo.com'");
    }

    @Test
    void testGetByEmail_CustomerNotFound() {
        when(customerRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.getByEmail("nonexistent@example.com"),
                "Deveria lançar uma exceção ResourceNotFoundException"
        );

        assertEquals("Cliente não encontrado", thrown.getMessage(), "A mensagem da exceção deveria ser 'Cliente não encontrado'");
    }

    @Test
    void testCreateCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        customerService.createCustomer(customerDto);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomer_CustomerExists() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        customerService.updateCustomer(1L, customerDto);
        verify(customerRepository, times(1)).save(any(Customer.class));
        assertEquals("new@example.com", customer.getEmail(), "O email deveria ser 'new@example.com'");
        assertEquals("Updated Customer", customer.getName(), "O nome deveria ser 'Updated Customer'");
    }

    @Test
    void testUpdateCustomer_CustomerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.updateCustomer(1L, customerDto),
                "Deveria lançar uma exceção ResourceNotFoundException"
        );
        assertEquals("Cliente não encontrado", thrown.getMessage(), "A mensagem da exceção deveria ser 'Cliente não encontrado'");
    }

    @Test
    void testDeleteCustomerById() {
        customerService.deleteCustomerById(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testListCustomers() {
        Page<CustomerDto> customersPage = mock(Page.class);
        when(customerRepository.listCustomerPaginationByQuery(PageRequest.of(0, 10))).thenReturn(customersPage);
        Page<CustomerDto> result = customerService.listCustomers(PageRequest.of(0, 10));
        assertEquals(customersPage, result, "A página de clientes deve ser a mesma.");
    }
}
