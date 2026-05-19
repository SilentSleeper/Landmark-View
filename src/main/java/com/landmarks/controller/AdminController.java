package com.landmarks.controller;

import com.landmarks.domain.Landmark;
import com.landmarks.repository.LandmarkRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/landmarks")
public class AdminController {

    private final LandmarkRepository landmarkRepository;

    public AdminController(LandmarkRepository landmarkRepository) {
        this.landmarkRepository = landmarkRepository;
    }

    @GetMapping
    public String listLandmarks(Model model) {
        model.addAttribute("landmarks", landmarkRepository.findAll());
        return "admin/landmarks";
    }

    @GetMapping("/new")
    public String newLandmarkForm(Model model) {
        model.addAttribute("landmark", new Landmark());
        return "admin/landmark-form";
    }

    @PostMapping
    public String saveLandmark(@ModelAttribute("landmark") Landmark landmark) {
        landmarkRepository.save(landmark);
        return "redirect:/admin/landmarks";
    }

    @GetMapping("/edit/{id}")
    public String editLandmarkForm(@PathVariable Long id, Model model) {
        Landmark landmark = landmarkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid landmark Id:" + id));
        model.addAttribute("landmark", landmark);
        return "admin/landmark-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteLandmark(@PathVariable Long id) {
        Landmark landmark = landmarkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid landmark Id:" + id));
        landmarkRepository.delete(landmark);
        return "redirect:/admin/landmarks";
    }
}
