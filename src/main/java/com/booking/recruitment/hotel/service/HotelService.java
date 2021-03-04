package com.booking.recruitment.hotel.service;

import com.booking.recruitment.hotel.model.Hotel;

import java.util.List;

public interface HotelService {
  List<Hotel> getAllHotels();

  List<Hotel> getHotelsByCity(Long cityId);

  Hotel createNewHotel(Hotel hotel);

  Hotel findById(Long hotelId) ;

  void deleteHotel(Long hotelId);

  List<Hotel> findClosestHotels(Double lat, Double lon);
}
