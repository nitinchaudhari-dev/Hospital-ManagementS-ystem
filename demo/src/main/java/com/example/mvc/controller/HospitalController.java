package com.example.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.mvc.entity.Hospital;
import com.example.mvc.service.HospitalService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Controller
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    // ✅ Home Page
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("hospitals", hospitalService.getAllPatients());
        return "index";
    }

    // ✅ Contact Page
    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    // ✅ Add new patient form
    @GetMapping("/products")
    public String addPatientForm(Model model) {
        model.addAttribute("product", new Hospital());
        return "new-petient";
    }

    // ✅ Save or update patient
    @PostMapping("/save-patient")
    public String savePatient(@ModelAttribute("product") Hospital hospital) {

        // Preserve old createdAt and createdBy if updating existing record
        if (hospital.getId() != null) {
            Hospital existing = hospitalService.getPatientById(hospital.getId());
            if (existing != null) {
                hospital.setCreatedAt(existing.getCreatedAt());
                hospital.setCreatedBy(existing.getCreatedBy());
            }
        }

        hospitalService.savePatient(hospital);
        return "redirect:/getall";
    }

    // ✅ Display all patients
    @GetMapping("/getall")
    public String getAllPatients(Model model) {
        model.addAttribute("hospital", hospitalService.getAllPatients());
        return "petient-list";
    }

    // ✅ Edit patient
    @GetMapping("/edit-patient/{id}")
    public String editPatient(@PathVariable("id") Long id, Model model) {
        Hospital existingPatient = hospitalService.getPatientById(id);
        model.addAttribute("product", existingPatient);
        return "new-petient";
    }

    // ✅ Find patients by month page
    @GetMapping("/find-patient")
    public String findPatientPage() {
        return "find-In-Month";
    }

    // ✅ List patients by selected month
    @GetMapping("/list-patient-by-month")
    public String listPatientsByMonth(@RequestParam("monthYear") String monthYear, Model model) {
        try {
            YearMonth ym = YearMonth.parse(monthYear);
            LocalDate startDate = ym.atDay(1);
            LocalDate endDate = ym.atEndOfMonth();

            List<Hospital> patients = hospitalService.getPatientsByMonth(startDate, endDate);
            model.addAttribute("patients", patients);
            model.addAttribute("selectedMonth", ym.getMonth().toString() + " " + ym.getYear());
            return "list-patient-by-month";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Invalid month or format! Please select again.");
            return "find-In-Month";
        }
    }
}
