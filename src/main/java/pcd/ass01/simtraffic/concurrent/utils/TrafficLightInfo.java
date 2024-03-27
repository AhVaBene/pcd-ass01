package pcd.ass01.simtraffic.concurrent.utils;

import pcd.ass01.simtraffic.concurrent.engine.TrafficLight;

public  record TrafficLightInfo(TrafficLight sem, Road road, double roadPos) {}
