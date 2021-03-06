package com.academy.springwebapplication.controller;

import com.academy.springwebapplication.dto.DepartureDto;
import com.academy.springwebapplication.dto.TicketDto;
import com.academy.springwebapplication.dto.UserRouteDto;
import com.academy.springwebapplication.mapper.DepartureMapper;
import com.academy.springwebapplication.model.entity.Departure;
import com.academy.springwebapplication.service.DepartureService;
import com.academy.springwebapplication.service.TicketGenerationService;
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
public class DepartureController {
    private final DepartureService departureService;
    private final TicketGenerationService ticketGenerationService;
    private final DepartureMapper departureMapper;

    @GetMapping("/departures")
    public String departures(Model model) {
        model.addAttribute("userRoute", new UserRouteDto());

        return "departures";
    }

    @PostMapping("/departures")
    public String findingDeparturesForRoute(@Valid @ModelAttribute("userRoute") UserRouteDto route,
                                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {

            return "departures";
        }

        List<Departure> departures = departureService.getDeparturesForRoute(route);

        List<TicketDto> tickets = ticketGenerationService.generateTicketsSuitableForUserRoute(departures, route);

        model.addAttribute("tickets", tickets);

        return "departures";
    }

    @GetMapping("/departures/route")
    public String departureRoute(@RequestParam(name = "departureId") Integer departureId,
                                 Model model) {
        departureService.checkIfDepartureIdIsValid(departureId);

        Departure departure = departureService.getDepartureById(departureId);

        DepartureDto departureDto = departureMapper.departureToDepartureDto(departure);

        model.addAttribute("departure", departureDto);

        return "departureRoute";
    }
}
