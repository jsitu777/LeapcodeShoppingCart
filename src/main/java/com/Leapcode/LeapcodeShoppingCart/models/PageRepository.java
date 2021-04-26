package com.Leapcode.LeapcodeShoppingCart.models;

import com.Leapcode.LeapcodeShoppingCart.models.data.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Integer> {
    Page findByCasing(String casing);

    @Query("SELECT p FROM Page p WHERE p.id != :id and p.casing = :casing")
    Page findByCasing(int id, String casing);

    List<Page> findAllByOrderBySortingAsc();
}
