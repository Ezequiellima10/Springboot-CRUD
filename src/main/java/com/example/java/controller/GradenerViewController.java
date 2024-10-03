package com.example.java.controller;

import org.springframework.ui.Model;

//import ch.qos.logback.core.model.Model;
import com.example.java.service.GardenerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class GradenerViewController {

    private final GardenerService gardenerService;

    @GetMapping
    public String GetAllGardeners(Model model) {
        model.addAttribute("gardeners", gardenerService.getGardenerList());

        return "getAllGardeners";
    }


}
