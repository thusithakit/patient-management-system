package com.thusithakit.patientservice.service;

import com.thusithakit.patientservice.dto.PatientRequestDTO;
import com.thusithakit.patientservice.dto.PatientResponseDTO;
import com.thusithakit.patientservice.exception.EmailAlreadyExistException;
import com.thusithakit.patientservice.mapper.PatientMapper;
import com.thusithakit.patientservice.model.Patient;
import com.thusithakit.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private PatientRepository patientRepository;
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients () {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(PatientMapper::toDTO)
                .toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistException("A patient with this email already exists" + patientRequestDTO.getEmail());
        }
        Patient newPatient = patientRepository.save(PatientMapper.toPatientModel(patientRequestDTO));
        return PatientMapper.toDTO(newPatient);
    }
}
