package com.example.prueba.service;

import java.util.List;

import com.example.prueba.entity.Identification;

public interface IdentificationService {
    public Identification findById(Long id);
    
    public List<Identification> findAll();
    
    public Identification delete(Long id);
    
    public Identification save(Identification identification);
    
    public Identification update(Long id,Identification identification);

}
