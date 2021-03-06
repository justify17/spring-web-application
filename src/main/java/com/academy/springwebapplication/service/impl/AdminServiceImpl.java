package com.academy.springwebapplication.service.impl;

import com.academy.springwebapplication.dto.RoleDto;
import com.academy.springwebapplication.dto.RouteDto;
import com.academy.springwebapplication.dto.TrainDto;
import com.academy.springwebapplication.dto.UserDto;
import com.academy.springwebapplication.mapper.RoleMapper;
import com.academy.springwebapplication.mapper.RouteMapper;
import com.academy.springwebapplication.mapper.TrainMapper;
import com.academy.springwebapplication.mapper.UserMapper;
import com.academy.springwebapplication.model.entity.Role;
import com.academy.springwebapplication.model.entity.Route;
import com.academy.springwebapplication.model.entity.Train;
import com.academy.springwebapplication.model.entity.User;
import com.academy.springwebapplication.model.repository.RoleRepository;
import com.academy.springwebapplication.model.repository.RouteRepository;
import com.academy.springwebapplication.model.repository.TrainRepository;
import com.academy.springwebapplication.model.repository.UserRepository;
import com.academy.springwebapplication.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for the administrator functionality
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RouteRepository routeRepository;
    private final TrainRepository trainRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final RouteMapper routeMapper;
    private final TrainMapper trainMapper;

    /**
     * Returns all users from the database
     *
     * @return all users
     */
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Returns all roles from the database
     *
     * @return all roles
     */
    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();

        return roles.stream()
                .map(roleMapper::roleToRoleDto)
                .collect(Collectors.toList());
    }

    /**
     * Returns all routes from the database
     *
     * @return all routes
     */
    @Override
    public List<RouteDto> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();

        return routes.stream()
                .map(routeMapper::routeToRouteDto)
                .collect(Collectors.toList());
    }

    /**
     * Returns all trains from the database
     *
     * @return all trains
     */
    @Override
    public List<TrainDto> getAllTrains() {
        List<Train> trains = trainRepository.findAll();

        return trains.stream()
                .map(trainMapper::trainToTrainDto)
                .collect(Collectors.toList());
    }

    /**
     * Sets the new role for the user and saves it in the database
     *
     * @param username  username for which the new role is being set
     * @param newRoleId new user role id
     */
    @Override
    public void setNewUserRole(String username, Integer newRoleId) {
        Role newUserRole = roleRepository.getById(newRoleId);

        User user = userRepository.findByUsername(username);
        user.setRole(newUserRole);

        userRepository.save(user);
    }

    /**
     * Sets the new account lockout status for the user and saves it in the database
     *
     * @param username username for which the new account status is being set
     */
    @Override
    public void setNewAccountStatus(String username) {
        User user = userRepository.findByUsername(username);

        boolean newAccountStatus = !user.isAccountNonLocked();

        user.setAccountNonLocked(newAccountStatus);

        userRepository.save(user);
    }

    /**
     * Deletes the user from the database
     *
     * @param userId user id
     */
    @Override
    public void deleteUserById(Integer userId) {

        userRepository.deleteById(userId);
    }
}
