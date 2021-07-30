
 package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;

public class FrontAutoGripper {
    public Servo frontJoint;
    public Servo frontElbow;

    public Position currentPosition;

    // values gotten from previous code

    public enum Position{
        UP(0.5, 0.35), //when joint and elbow are completely extended upwards
        GRAB(0.1, 0.7), //elbow is holding the block above the ground
        TRAVELING(0, 0.7), //halfway between up and grab
        READY(0.1, 0.35), //when joint is down but elbow is up
        RELEASE(0,0.35); //when joint is in traveling and elbow is up

        public double jointPosition;
        public double elbowPosition;

        Position(double jointPosition, double elbowPosition){
            this.jointPosition = jointPosition;
            this.elbowPosition = elbowPosition;
        }
    }

    public FrontAutoGripper (DeviceManager deviceManager){

        frontJoint = deviceManager.frontJoint;
        frontElbow = deviceManager.backElbow;

        // set default position
        frontElbow.setPosition(0);
        frontJoint.setPosition(0);

        frontElbow.setDirection(Servo.Direction.FORWARD);
        frontJoint.setDirection(Servo.Direction.FORWARD);

    }

    public void run(Position position){
        frontJoint.setPosition(position.jointPosition);
        frontElbow.setPosition(position.elbowPosition);

        // updates position
        currentPosition = position;
    }

    public void up(){
        run(Position.UP);
    }

    public void grab(){
        run(Position.GRAB);
    }

    public void traveling(){
        run(Position.TRAVELING);
    }

    public void ready(){
        run(Position.READY); //keeps the elbow and joint ready to pick up the block
    }

    public void release(){
        run(Position.RELEASE); //releases the block
    }
}

