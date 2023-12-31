package com.locar.dao;

import com.locar.entities.Tarif;
import com.locar.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarifRepository extends JpaRepository<Tarif, Long>  {

    public List<Tarif> findByVehicule(Vehicule vehicule);
}
