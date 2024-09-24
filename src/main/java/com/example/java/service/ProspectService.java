package com.example.java.service;

import com.example.java.model.Plant;
import com.example.java.model.Prospect;
import com.example.java.repository.ProspectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProspectService {

    ProspectRepository prospectRepository;

    public Prospect getProspectById(Long id) {
        return prospectRepository.findById(id).orElseThrow(
                () -> new RuntimeException("no se encontro el prospecto " + id )
        );
    }

    public Prospect saveProspectById(Prospect prospect) {
        return prospectRepository.save(prospect);
    }

    public void deleteProspectById(Long id) {
         prospectRepository.deleteById(id);
    }

    public List<Prospect> getAllProspects(){
        return prospectRepository.findAll();
    }
}
