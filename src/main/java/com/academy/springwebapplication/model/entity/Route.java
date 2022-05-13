package com.academy.springwebapplication.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = {"id", "type","departureStation","arrivalStation"})
@ToString(of = {"id", "type","departureStation","arrivalStation"})
@NoArgsConstructor
@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String type;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Departure> departures;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private Set<RouteStation> routeStations;

    @Transient
    private Station departureStation;

    @Transient
    private Station arrivalStation;
}