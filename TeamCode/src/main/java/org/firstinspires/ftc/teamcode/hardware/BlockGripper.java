package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;

public class BlockGripper {
    public Servo blockGripper;
    public Servo blockRotator;

    public Position pos;
    //updates position every time

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

        pos = Position.READY; // init pos
    }

    public void runGripper(Position position){
        blockGripper.setPosition(position.gripperPos);
    }

    public void ready(){
        runGripper(Position.READY);
        pos = Position.READY;
    }

    public void grab(){
        runGripper(Position.GRAB);
        pos = Position.GRAB;
    }

    public void release(){
        runGripper(Position.RELEASE);
        pos = Position.RELEASE;
    }
}
