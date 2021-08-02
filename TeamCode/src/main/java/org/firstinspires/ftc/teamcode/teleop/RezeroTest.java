package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public class RezeroTest extends OpMode {
    Robot robot;

    @Override
    public void init() {
        robot.frontAutoGripper.frontJoint.setPosition(0);
        robot.frontAutoGripper.frontElbow.setPosition(0);
        robot.backAutoGripper.backElbow.setPosition(0);
        robot.backAutoGripper.backJoint.setPosition(0);
        robot.blockGripper.blockGripper.setPosition(0);
    }

    @Override
    public void loop() {
        robot.frontAutoGripper.frontJoint.setPosition(0);
        robot.frontAutoGripper.frontElbow.setPosition(0);
        robot.backAutoGripper.backElbow.setPosition(0);
        robot.backAutoGripper.backJoint.setPosition(0);
        robot.blockGripper.blockGripper.setPosition(0);
    }
}
