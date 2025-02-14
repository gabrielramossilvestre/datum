package br.com.datum.prova.repository;

import br.com.datum.prova.dto.CustomerDto;
import br.com.datum.prova.entities.Customer;
import br.com.datum.prova.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    //Somente para exemplo
    @Query("SELECT new br.com.datum.prova.dto.CustomerDto(c.name, c.address, c.email) FROM Customer c")
    Page<CustomerDto> listCustomerPaginationByQuery(Pageable page);
}
