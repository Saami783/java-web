package com.locar.dao;

import com.locar.entities.Avis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisRepository extends JpaRepository<Avis, Long>  {
}
