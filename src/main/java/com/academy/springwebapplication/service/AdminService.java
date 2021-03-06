package com.academy.springwebapplication.service;

import com.academy.springwebapplication.dto.RoleDto;
import com.academy.springwebapplication.dto.RouteDto;
import com.academy.springwebapplication.dto.TrainDto;
import com.academy.springwebapplication.dto.UserDto;

import java.util.List;

public interface AdminService {
    List<UserDto> getAllUsers();

    List<RoleDto> getAllRoles();

    List<RouteDto> getAllRoutes();

    List<TrainDto> getAllTrains();

    void setNewUserRole(String username, Integer newRoleId);

    void setNewAccountStatus(String username);

    void deleteUserById(Integer userId);
}
