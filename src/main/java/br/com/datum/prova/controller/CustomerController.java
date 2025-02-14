package br.com.datum.prova.controller;

import br.com.datum.prova.dto.CustomerDto;
import br.com.datum.prova.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<CustomerDto> getCustomerByEmail(@PathVariable String email) {
        var customer = customerService.getByEmail(email);
        return ResponseEntity.ok(new CustomerDto(customer.get()));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerDto dto) {
        customerService.createCustomer(dto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/{id}/email/{email}")
    public ResponseEntity<Void> patchCustomerEmail(@PathVariable Long id, @PathVariable String email) {
        customerService.patchCustomerEmail(id, email);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> patchCustomerEmail(@PathVariable Long id, @RequestBody CustomerDto dto) {
        customerService.updateCustomer(id, dto);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<Page<CustomerDto>> listCustomerWithPagination(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerDto> lst = customerService.listCustomers(pageable);

        return ResponseEntity.ok(lst);
    }

}
