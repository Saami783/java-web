package com.locar.dao;

import com.locar.entities.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvisRepository extends JpaRepository<Avis, Long>  {

    @Query("SELECT a FROM Avis a WHERE a.reservation.vehicule.id = :vehiculeId")
    List<Avis> findAvisByVehiculeId(@Param("vehiculeId") Long vehiculeId);

}
