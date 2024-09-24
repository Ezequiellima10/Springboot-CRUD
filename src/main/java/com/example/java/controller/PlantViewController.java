package com.example.java.controller;

import com.example.java.model.Plant;
import com.example.java.service.GardenerService;
import com.example.java.service.PlantService;
import com.example.java.service.ProspectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class PlantViewController {

    private final PlantService plantService;

    private final GardenerService gardenerService;

    private final ProspectService prospectService;

    @GetMapping("/plantas")
    public String getAllPlants(Model model) {
        model.addAttribute("plantas", plantService.getAllPlants());

        return "plantas";
    }

    @GetMapping("/agregarPlantas")
    public String showNewPlantForm(Model model) {
        model.addAttribute("Gardeners", gardenerService.getGardenerList());
        model.addAttribute("prospects", prospectService.getAllProspects());
        model.addAttribute("plant",new Plant());

        return "plantas";
    }

    @PostMapping("/guardarPlanta")
    public String savePlant(@ModelAttribute Plant plant, @RequestParam Long gardenerId, @RequestParam(required = false) List<Long> prospectId) {
        plantService.savePlant(plant, gardenerId, prospectId);
        return "redirect:/plantas";
    }

    @GetMapping("actualizarPlanta/{id]")
    public String showUpdatePlantForm(@PathVariable Long plantId, Model model) {
       model.addAttribute("Plant", plantService.getPlantById(plantId));
       model.addAttribute("prospects", prospectService.getAllProspects());

        return "redirect:/actualizarPlanta";
    }

    @PostMapping("/eliminarPlanta/{id}")
    public String updatePlant(@PathVariable Long plantId, @ModelAttribute Plant updatedPlant, @RequestParam Long gardenerId, List<Long> prospectId) {
        plantService.updatePlanta(plantId, updatedPlant, gardenerId, prospectId);
        return "redirect:/plantas";
    }


    @GetMapping("eliminarPlanta/{id]")
    public String deletePlant(@PathVariable Long id) {
        plantService.deletePlantById(id);
        return "redirect:/plantas";
    }
}
