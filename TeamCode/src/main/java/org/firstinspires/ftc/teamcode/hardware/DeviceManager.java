package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DeviceManager {
    public HardwareMap hardwareMap;

    // Arm on robot that holds claw
    public DcMotorEx arm;

    // Intake Servos
    public DcMotorEx blockRotator; // Rotates the block
    public DcMotorEx blockGripper; // Grabs the block

    // Intake Motors
    public DcMotorEx omniIntake; // Omni-Wheel Intake Motor
    public DcMotorEx compliantIntake; // Compliant wheel Intake Motor

    // Front Gripper Servos
    public DcMotorEx frontJoint; // Rotates Gripper
    public DcMotorEx frontElbow; // Rotates Gripper

    // Rear Gripper Servos
    public DcMotorEx backJoint; // Rotates Gripper
    public DcMotorEx backElbow; // Rotates Gripper

    // Drive Motors
    public DcMotorEx frontRight, frontLeft, backRight, backLeft;



    // Device Initialization
    void init(boolean autoIsRunning) {
        // If auto is running, hardware is already set up
        if (!autoIsRunning) {
            // Drive Motor Initialization
            frontRight = hardwareMap.get(DcMotorEx.class, "front_right");
            frontLeft = hardwareMap.get(DcMotorEx.class, "front_left");
            backRight = hardwareMap.get(DcMotorEx.class, "back_right");
            backLeft = hardwareMap.get(DcMotorEx.class, "back_left");

            omniIntake = hardwareMap.get(DcMotorEx.class, "omni_intake");
            compliantIntake = hardwareMap.get(DcMotorEx.class, "compliant_intake");

            frontJoint = hardwareMap.get(DcMotorEx.class, "front_joint");
            frontElbow = hardwareMap.get(DcMotorEx.class, "front_elbow");

            backJoint = hardwareMap.get(DcMotorEx.class, "back_joint");
            backElbow = hardwareMap.get(DcMotorEx.class, "back_elbow");

            arm = hardwareMap.get(DcMotorEx.class, "arm");

            blockRotator = hardwareMap.get(DcMotorEx.class, "block_rotator");
            blockGripper = hardwareMap.get(DcMotorEx.class, "block_gripper");
        }
    }

    // Used by TeleOp
    public DeviceManager(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

}
