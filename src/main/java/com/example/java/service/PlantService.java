package com.example.java.service;

import com.example.java.model.Gardener;
import com.example.java.model.Plant;
import com.example.java.repository.GardenerRepository;
import com.example.java.repository.PlantRepository;
import com.example.java.repository.ProspectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlantService {

    private PlantRepository plantRepository;
    private GardenerRepository gardenerRepository;
    private ProspectRepository prospectRepository;

    public Plant getPlantById(Long id){
        return plantRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No se encontro la Planta: "+ id)
        );
    }

    public List<Plant> getAllPlants(){
        return plantRepository.findAllByOrderBySpeciesIgnoreCaseAsc();
    }

    public Plant savePlant(Plant plant, Long gardenerId, List<Long> prospectIds){
        Gardener gardener = gardenerRepository.findById(gardenerId).orElseThrow(
                () -> new RuntimeException("No existe un jardinero con ese id "
                        + gardenerId+ "en savePlant"));

        plant.setGardener(gardener);

        if(prospectIds != null){
            plant.setAssociatedProspectus(prospectRepository.findAllById(prospectIds));
        }

        return plantRepository.save(plant);
    }

    public void deletePlantById(Long id){
        plantRepository.deleteById(id);
    }

    public void actualizarPlanta(Long idPlanta, Plant plantaActualizada, Long idJardinero, List<Long> idProspectos) {
        Optional<Plant> plantaOptional = plantRepository.findById(idPlanta);

        Gardener jardinero = gardenerRepository.findById(idJardinero)
                .orElseThrow(() -> new RuntimeException("No se encontró el jardinero con el id:"
                        +idJardinero+" al momento de actualizar la planta"));

        if (idProspectos !=null) {
            plantaActualizada.setAssociatedProspectus(prospectRepository.findAllById(idProspectos));
        }

        plantaActualizada.setGardener(jardinero);

        Plant plantaExistente = construirPlanta(plantaActualizada, plantaOptional);
        plantRepository.save(plantaExistente);
    }

    private Plant construirPlanta(Plant plantaActualizada, Optional<Plant> plantaOptional) {
        Plant.PlantBuilder plantaBuilder = Plant.builder();

        plantaOptional.ifPresent(plantaExistente -> {
            plantaBuilder
                    .id(plantaActualizada.getId())
                    .leafColor(plantaActualizada.getLeafColor())
                    .species(plantaActualizada.getSpecies())
                    .plantingDate(plantaActualizada.getPlantingDate())
                    .gardener(plantaActualizada.getGardener())
                    .associatedProspectus(plantaActualizada.getAssociatedProspectus());
        });

        return plantaBuilder.build();
    }

}
