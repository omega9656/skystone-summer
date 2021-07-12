package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;

public class BlockGripper {
    public Servo blockGripper;
    public Servo blockRotator;

    // TODO tune constants
    public enum Position {
        READY(0),
        GRAB(70),
        RELEASE(-20);

        public double gripperPos;

        Position(double gripperPos){
            this.gripperPos = gripperPos;
        }
    }

    public BlockGripper(DeviceManager deviceManager){
        blockGripper = deviceManager.blockGripper;
        blockGripper.setPosition(Position.READY.gripperPos);

        // not going to be using
        blockRotator = deviceManager.blockRotator;
    }

    public void runGripper(Position position){
        blockGripper.setPosition(position.gripperPos);
    }

    public void ready(){
        runGripper(Position.READY);
    }

    public void grab(){
        runGripper(Position.GRAB);
    }

    public void release(){
        runGripper(Position.RELEASE);
    }
}
