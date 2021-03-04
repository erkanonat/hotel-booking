package com.booking.recruitment.hotel.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public  class DistanceUtil {

    public static final double R = 6371.0088; // Earth's radius Km

    public static Double calculateDistance(double lat1, double lon1, double lat2, double lon2){
        Double distance = 0d;
        try {
            Double latDistance = toRad(lat2-lat1);
            Double lonDistance = toRad(lon2-lon1);
            Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                    Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                            Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            distance = R * c;
        }catch (Exception ex){
            throw ex;
        }
        return distance;
    }
    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    public static Map<Long, Double> sortByValue(final Map<Long, Double> wordCounts) {

        return wordCounts.entrySet()
                .stream()
                .sorted((Map.Entry.<Long, Double>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
