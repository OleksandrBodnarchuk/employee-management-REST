package com.obodnarchuk.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.city = :city AND a.street = :street AND a.houseNr = :houseNr")
    Optional<Address> checkForAddress(@Param("city") String city,
                                      @Param("street") String street,
                                      @Param("houseNr") String houseNr);
}
