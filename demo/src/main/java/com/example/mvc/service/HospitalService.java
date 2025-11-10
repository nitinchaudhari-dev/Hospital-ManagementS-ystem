package com.example.mvc.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mvc.entity.Hospital;
import com.example.mvc.repository.HospitalRepository;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    // ✅ Save or update patient
    public void savePatient(Hospital hospital) {
        if (hospital.getCreatedBy() == null || hospital.getCreatedBy().isEmpty()) {
            hospital.setCreatedBy("Dr. Ankita Chaudhari");
        }
        hospitalRepository.save(hospital);
    }

    // ✅ Get all patients
    public List<Hospital> getAllPatients() {
        return hospitalRepository.findAll();
    }

    // ✅ Get patient by ID
    public Hospital getPatientById(Long id) {
        return hospitalRepository.findById(id).orElse(null);
    }

    // ✅ Get patients by month (based on createdAt)
    public List<Hospital> getPatientsByMonth(LocalDate startDate, LocalDate endDate) {
        return hospitalRepository.findByCreatedAtBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
    }
    
}
