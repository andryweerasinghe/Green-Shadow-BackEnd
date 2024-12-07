package lk.ijse.greenshadow.service;

import lk.ijse.greenshadow.dto.impl.StaffDtoImpl;

import java.util.List;

public interface StaffService {
    void saveStaff(StaffDtoImpl staffDto);
    List<StaffDtoImpl> loadAllStaff();
    void deleteStaff(String staffID);
    StaffDtoImpl getStaffByID(String staffID);
    void updateStaff(String staffID, StaffDtoImpl staffDto);
}
