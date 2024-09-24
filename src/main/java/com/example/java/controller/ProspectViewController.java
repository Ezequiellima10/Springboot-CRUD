package com.example.java.controller;


import com.example.java.model.Prospect;
import com.example.java.service.ProspectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
@AllArgsConstructor
public class ProspectViewController {

    private final ProspectService prospectService;

    @GetMapping("/prospectos")
    public String getAllProspects(Model model) {
        model.addAttribute("prospectos", prospectService.getAllProspects());

        return "prospectos";
    }

    @GetMapping("/agregarProspecto")
    public String showNewProspectForm(Model model) {
        model.addAttribute("prospecto", new Prospect());

        return "prospectos/newProspectForm";
    }

    @PostMapping("/guardarProspecto")
    public String saveProspecto(@ModelAttribute Prospect prospecto) {
        prospectService.saveProspectById(prospecto);

        return "redirect:/prospectos";
    }
}
