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


    public void updatePlanta(Long plantId, Plant updatedPlant, Long gardenerId, List<Long> prospectsId) {
        Optional<Plant> plantaOptional = plantRepository.findById(plantId);

        Gardener gardener = gardenerRepository.findById(gardenerId)
                .orElseThrow(() -> new RuntimeException("No se encontró el jardinero con el id:"
                        +gardenerId+" al momento de actualizar la planta"));

        if (prospectsId !=null) {
            updatedPlant.setAssociatedProspectus(prospectRepository.findAllById(prospectsId));
        }

        updatedPlant.setGardener(gardener);

        Plant plantaExistente = buildPlant(updatedPlant, plantaOptional);
        plantRepository.save(plantaExistente);
    }


    private Plant buildPlant(Plant updatedPlant, Optional<Plant> optionalPlant) {
        Plant.PlantBuilder plantaBuilder = Plant.builder();

        optionalPlant.ifPresent(plantaExistente -> {
            plantaBuilder
                    .id(plantaExistente.getId())
                    .leafColor(updatedPlant.getLeafColor())
                    .species(updatedPlant.getSpecies())
                    .plantingDate(updatedPlant.getPlantingDate())
                    .gardener(updatedPlant.getGardener())
                    .associatedProspectus(updatedPlant.getAssociatedProspectus());
        });

        return plantaBuilder.build();
    }

}
