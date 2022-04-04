package com.example.prueba.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.prueba.entity.Identification;


@Repository
public interface IdentificationRepository  extends JpaRepository<Identification, Long>{

}
