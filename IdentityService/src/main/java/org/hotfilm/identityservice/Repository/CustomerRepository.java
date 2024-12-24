package org.hotfilm.identityservice.Repository;

import org.hotfilm.identityservice.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    boolean existsByCustomerName(String name);
}
