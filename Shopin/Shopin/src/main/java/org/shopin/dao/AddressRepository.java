package org.shopin.dao;

import org.shopin.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT * FROM address WHERE email=?1 ORDER BY createdon DESC LIMIT 1", nativeQuery = true)
    Address fetchLatestAddress(String email);

    @Transactional(readOnly = true)
    @Query(value = "SELECT address FROM address WHERE email=?1 ORDER BY createdon DESC LIMIT 1", nativeQuery = true)
    String fetchLatestAddressOnly(String email);
}
