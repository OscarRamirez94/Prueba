package com.example.prueba.service.impl;




import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.prueba.entity.Identification;
import com.example.prueba.repository.IdentificationRepository;
import com.example.prueba.service.IdentificationService;


@Service
public class IdentificationServiceImpl implements IdentificationService {

	@Autowired
	private IdentificationRepository identificationRepository;
	
	
	@Override
	@Transactional(readOnly = true)
	public Identification findById(Long id) {
		return identificationRepository.findById(id).orElse(null);
	}

	@Override
	public List<Identification> findAll() {
		return identificationRepository.findAll();
	}
	
	@Override
	public Identification delete(Long id) {
		Identification identification = findById(id);
	    identification.setActive(false);
	    return identificationRepository.save(identification);		
	}
	
	@Override
	public Identification save(Identification identification) {
		return identificationRepository.save(identification);
	}

	
	@Override
	public Identification update(Long id, Identification identification) {
		Identification identificationSearch = findById(id);
		if(identificationSearch!=null) {
			identificationSearch.setIdentificationName(identification.getIdentificationName());
			identificationSearch.setDescription(identification.getDescription());
			return identificationRepository.save(identificationSearch);
		}
		return identification;
	}

}
