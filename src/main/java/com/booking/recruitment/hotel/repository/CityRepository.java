package com.booking.recruitment.hotel.repository;

import com.booking.recruitment.hotel.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends JpaRepository<City, Long>, CrudRepository<City, Long> {}
