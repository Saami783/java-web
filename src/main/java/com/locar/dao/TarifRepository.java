package com.locar.dao;

import com.locar.entities.Tarif;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarifRepository extends JpaRepository<Tarif, Integer>  {
}
