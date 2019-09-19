package com.medsec.dao;

import com.medsec.entity.Doctor;

import java.util.List;

public interface DoctorMapper {
    List<Doctor> selectAllDoctors();
    Doctor selectOneDoctor(String doctorID);
    void deleteDoctor(String doctorID);
    void updateDoctor(Doctor doctor);
    void addDoctor(Doctor doctor);
}
