package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.exception.BadRequestException;
import com.booking.recruitment.hotel.exception.ElementNotFoundException;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.repository.HotelRepository;
import com.booking.recruitment.hotel.service.HotelService;
import com.booking.recruitment.hotel.utils.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
class DefaultHotelService implements HotelService {
  private final HotelRepository hotelRepository;

  @Autowired
  DefaultHotelService(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  @Override
  public List<Hotel> getAllHotels() {
    return hotelRepository.findAll();
  }

  @Override
  public List<Hotel> getHotelsByCity(Long cityId) {
    return hotelRepository.findAll().stream()
        .filter((hotel) -> cityId.equals(hotel.getCity().getId()))
        .collect(Collectors.toList());
  }

  @Override
  public Hotel createNewHotel(Hotel hotel) {
    if (hotel.getId() != null) {
      throw new BadRequestException("The ID must not be provided when creating a new Hotel");
    }

    return hotelRepository.save(hotel);
  }

  @Override
  public Hotel findById(Long hotelId) {
    Optional<Hotel> hotel = hotelRepository.findById(hotelId);
    if (hotel.isPresent())
      return hotel.get();
    else
      throw new ElementNotFoundException("Hotel not found with id : " + hotelId);
  }

  @Override
  public void deleteHotel(Long hotelId) {

       Optional<Hotel> hotel = hotelRepository.findById(hotelId);
       if(!hotel.isPresent())
         throw new ElementNotFoundException("Hotel not found with id : " + hotelId);
       else
         hotel.get().setDeleted(true);
       hotelRepository.save(hotel.get());
  }

  @Override
  public List<Hotel> findClosestHotels(Double lat, Double lon) {

      List<Hotel> result = new ArrayList<>();

      Map<Long,Double> distanceMap = new HashMap<>();
      List<Hotel> hotels = hotelRepository.findAll();
      for(Hotel hotel:hotels) {
        Double distance = DistanceUtil.calculateDistance(lat,lon,hotel.getLatitude(),hotel.getLongitude());
        distanceMap.put(hotel.getId(),distance);
      }

      Map<Long,Double> sortedHotels = DistanceUtil.sortByValue(distanceMap);
      int i=1;
      for(Map.Entry<Long,Double> item: sortedHotels.entrySet()) {
            result.add(hotelRepository.findById(item.getKey()).get()) ;
            if(i==3)
              break;
      }
      return result;
  }
}
