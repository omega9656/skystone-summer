package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class BlockDetector {
    public DistanceSensor distanceSensor;

    public BlockDetector(DeviceManager deviceManager){

        distanceSensor = deviceManager.sensorDistance;

    }

    public boolean isBlockIntaked(){
        return distanceSensor.getDistance(DistanceUnit.CM) < 7;
    }
}
