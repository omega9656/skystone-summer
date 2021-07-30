package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.Servo;

public class BackAutoGripper {
    public Servo backJoint;
    public Servo backElbow;

    public Position currentMode;

    //all values taken from skystone-worlds repo
    public enum Position{
        UP(.41,.31), //when joint and elbow are completely extended up
        GRAB(0,.02),//joint is parallel to ground and elbow is perpendicular
        TRAVELING(.63,.02),//elbow holding block above ground or 'travelling'
        READY(.45,    .15), //when joint is down but elbow is up
        RELEASE(.63,.31); //when joint is in travelling and elbow is up

        public double jointPosition;
        public double elbowPosition;

        Position(double jointPosition, double elbowPosition){
            this.jointPosition =jointPosition;
            this.elbowPosition = elbowPosition;
        }
    }

    public BackAutoGripper(DeviceManager deviceManager){
        backJoint = deviceManager.backJoint;
        backElbow = deviceManager.backElbow;

        // set default position
        backElbow.setPosition(0);
        backJoint.setPosition(0);

        backJoint.setDirection(Servo.Direction.REVERSE);
        backElbow.setDirection(Servo.Direction.REVERSE);
    }

    public void run(Position position){

        backJoint.setPosition(position.jointPosition);
        backElbow.setPosition(position.elbowPosition);

        // updates the position the joint and elbow are at
        currentMode = position;
    }

    //sets back auto gripper to up position
    public void up(){
        run(Position.UP);
    }

    //grabs the block
    public void grab(){
        run(Position.GRAB);
    }

    //sets back auto gripper to travelling position
    public void traveling(){
        run(Position.TRAVELING);
    }

    //ready to grab the block (elbow isn't set)
    public void ready(){
        run(Position.READY);
    }

    //releases the block
    public void release(){
        run(Position.RELEASE);
    }

}
