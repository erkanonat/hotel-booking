package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.exception.ElementNotFoundException;
import com.booking.recruitment.hotel.model.City;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.CityService;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
  private final HotelService hotelService;
  private final CityService cityService;

  @Autowired
  public HotelController(HotelService hotelService,CityService cityService) {
    this.hotelService = hotelService;
    this.cityService=cityService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Hotel> getAllHotels() {
    return hotelService.getAllHotels();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Hotel createHotel(@RequestBody Hotel hotel) {
    return hotelService.createNewHotel(hotel);
  }


  @RequestMapping(value = "/{hotelId}", method = RequestMethod.GET)
  @ResponseBody
  public Hotel getHotelById(@PathVariable("hotelId") Long hotelId) {
      return hotelService.findById(hotelId);
  }

  @RequestMapping(value = "/{hotelId}", method = RequestMethod.DELETE)
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteHotel(@PathVariable("hotelId") Long hotelId) {
    hotelService.deleteHotel(hotelId);
  }

  @RequestMapping(value = "/seearch/{cityId}", params = {"sortBy"}, method = RequestMethod.GET)
  @ResponseBody
  public List<Hotel> searchHotels(@PathVariable("cityId") Long cityId ,@RequestParam String sortBy) {

     City city = cityService.getCityById(cityId);
     if(city == null){
       throw new ElementNotFoundException("city not found ");
     }
     return  hotelService.findClosestHotels(city.getCityCentreLatitude(), city.getCityCentreLongitude());
  }
}
