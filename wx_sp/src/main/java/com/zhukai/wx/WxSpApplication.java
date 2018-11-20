package com.zhukai.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class WxSpApplication {

    private static final Double PI = Math.PI;

    private static final Double PK = 180 / PI;

    public static void main(String[] args) {
        SpringApplication.run(WxSpApplication.class, args);
    }

    private static Double getDistanceFromTwoPoints(Map pointA, Map pointB) {
        Double distance = null;
        if (pointA != null && !pointA.isEmpty() && pointB != null && !pointB.isEmpty()) {
            double lat_a = (double) pointA.get("lat");
            double lng_a = (double) pointA.get("lng");
            double lat_b = (double) pointB.get("lat");
            double lng_b = (double) pointB.get("lng");

            if (lat_a == lat_b && lng_a == lng_b) {
                return 0.0d;
            }

            double t1 = Math.cos(lat_a / PK) * Math.cos(lng_a / PK) * Math.cos(lat_b / PK) * Math.cos(lng_b / PK);
            double t2 = Math.cos(lat_a / PK) * Math.sin(lng_a / PK) * Math.cos(lat_b / PK) * Math.sin(lng_b / PK);
            double t3 = Math.sin(lat_a / PK) * Math.sin(lat_b / PK);

            double tt = Math.acos(t1 + t2 + t3);
            distance = 6366000 * tt;
        }
        return distance;
    }

    /**
     * 地球半径
     */
    private static double EARTH_RADIUS = 6378138.0;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static boolean isInCircle(double radius, double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        System.out.println(s);
        s = Math.round(s * 10000) / 10000;
        if (s > radius) {//不在圆上
            return false;
        } else {
            return true;
        }
    }


}
