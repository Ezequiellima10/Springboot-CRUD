package com.example.java.service;

import com.example.java.model.Gardener;
import com.example.java.repository.GardenerRepository;
import com.example.java.repository.ProspectRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GardenerService {

    private GardenerRepository gardenerRepository;

    public Gardener getGardenerById(Long id) {
        return gardenerRepository.findById(id).orElseThrow(()
                -> new RuntimeException("No se encontro el jardinero: "+ id));
    }

    public List<Gardener> getGardenerList(){
        return gardenerRepository.findAll();
    };

    public Gardener saveGardener(Gardener gardener){
        return gardenerRepository.save(gardener);
    }

}
