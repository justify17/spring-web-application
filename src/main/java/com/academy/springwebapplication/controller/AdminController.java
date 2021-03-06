package com.academy.springwebapplication.controller;

import com.academy.springwebapplication.dto.*;
import com.academy.springwebapplication.service.AdminService;
import com.academy.springwebapplication.service.DepartureService;
import com.academy.springwebapplication.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final TicketService ticketService;
    private final DepartureService departureService;

    @GetMapping("/admin")
    public String admin(Model model) {
        setModelData(model);

        model.addAttribute("openDefault", true);

        return "admin";
    }

    @PostMapping(value = "/admin", params = {"hiddenAction=changeUserRole"})
    public String changeUserRole(@RequestParam("username") String username,
                                 @RequestParam("newRole") int newRoleId,
                                 Model model) {
        adminService.setNewUserRole(username, newRoleId);

        setModelData(model);

        model.addAttribute("openDefault", true);

        return "admin";
    }

    @PostMapping(value = "/admin", params = {"hiddenAction=changeAccountStatus"})
    public String changeAccountStatus(@RequestParam("username") String username,
                                      Model model) {
        adminService.setNewAccountStatus(username);

        setModelData(model);

        model.addAttribute("openDefault", true);

        return "admin";
    }

    @PostMapping(value = "/admin", params = {"hiddenAction=deleteUser"})
    public String deleteUser(@RequestParam("userId") int userId,
                             Model model) {
        adminService.deleteUserById(userId);

        setModelData(model);

        model.addAttribute("openDefault", true);

        return "admin";
    }

    @PostMapping(value = "/admin", params = {"hiddenAction=cancelDeparture"})
    public String cancelDeparture(@RequestParam("departureId") int departureId,
                                  Model model) {
        departureService.deleteDepartureById(departureId);

        setModelData(model);

        model.addAttribute("openDepartures", true);

        return "admin";
    }

    @PostMapping(value = "/admin", params = {"hiddenAction=createNewDeparture"})
    public String addNewDeparture(@Valid @ModelAttribute("newDeparture") DepartureDto departureDto,
                                  BindingResult bindingResult, Model model) {
        model.addAttribute("openDepartures", true);

        if (bindingResult.hasErrors()) {
            setModelData(model);

            return "admin";
        }

        departureService.saveNewDeparture(departureDto);

        setModelData(model);

        return "admin";
    }

    @GetMapping("/admin/userOrders")
    public String userOrders(@RequestParam("username") String username,
                             Model model) {
        List<TicketDto> userTickets = ticketService.getUserTickets(username);

        model.addAttribute("tickets", userTickets);

        return "userOrders";
    }

    @PostMapping("/admin/userOrders")
    public String deleteUserOrder(@RequestParam("ticketId") int ticketId,
                                  @RequestParam("username") String username,
                                  Model model) {
        ticketService.deleteTicketById(ticketId);

        List<TicketDto> userTickets = ticketService.getUserTickets(username);

        model.addAttribute("tickets", userTickets);

        return "userOrders";
    }

    @GetMapping("/admin/departureDetails")
    public String departureDetails(@RequestParam("departureId") int departureId,
                                   Model model) {
        List<TicketDto> departureTickets = ticketService.getAllTicketsByDeparture(departureId);

        model.addAttribute("tickets", departureTickets);

        return "departureDetails";
    }

    @PostMapping("/admin/departureDetails")
    public String deleteTicket(@RequestParam("ticketId") int ticketId,
                               @RequestParam("departureId") int departureId,
                               Model model) {
        ticketService.deleteTicketById(ticketId);

        List<TicketDto> departureTickets = ticketService.getAllTicketsByDeparture(departureId);

        model.addAttribute("tickets", departureTickets);

        return "departureDetails";
    }

    private void setModelData(Model model) {
        List<UserDto> users = adminService.getAllUsers();
        model.addAttribute("users", users);

        List<RoleDto> roles = adminService.getAllRoles();
        model.addAttribute("roles", roles);

        List<DepartureDto> departures = departureService.getAllDepartures();
        model.addAttribute("departures", departures);

        List<RouteDto> routes = adminService.getAllRoutes();
        model.addAttribute("routes", routes);

        List<TrainDto> trains = adminService.getAllTrains();
        model.addAttribute("trains", trains);

        if (!model.containsAttribute("newDeparture")) {

            model.addAttribute("newDeparture", new DepartureDto());
        }
    }
}
