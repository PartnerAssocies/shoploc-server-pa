package com.pa.shoploc.repository;

import com.pa.shoploc.bo.Commercant;
import com.pa.shoploc.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface CommercantRepository extends JpaRepository<Commercant, String> {
    List<Commercant> findByRole_(Role role);
}
