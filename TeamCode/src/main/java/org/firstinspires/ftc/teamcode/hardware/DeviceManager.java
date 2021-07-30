package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class DeviceManager {
    public HardwareMap hardwareMap;

    // Arm on robot that holds claw
    public DcMotorEx arm;

    // Arm Servos
    public Servo blockRotator; // Rotates the block
    public Servo blockGripper; // Grabs the block

    // Intake Motors
    public DcMotorEx omniIntake; // Omni-Wheel Intake Motor
    public DcMotorEx compliantIntake; // Compliant wheel Intake Motor

    // Front Gripper Servos
    public Servo frontJoint; // Rotates Gripper
    public Servo frontElbow; // Rotates Gripper

    // Rear Gripper Servos
    public Servo backJoint; // Rotates Gripper
    public Servo backElbow; // Rotates Gripper

    // Drive Motors
    public DcMotorEx frontRight, frontLeft, backRight, backLeft;

    //Sensors
    public DistanceSensor sensorDistance;

    // Device Initialization
    void init(boolean autoIsRunning) {
        // If auto is running, hardware is already set up
        if (!autoIsRunning) {
            // Drive Motor Initialization
            frontRight = hardwareMap.get(DcMotorEx.class, "front_right");
            frontLeft = hardwareMap.get(DcMotorEx.class, "front_left");
            backRight = hardwareMap.get(DcMotorEx.class, "back_right");
            backLeft = hardwareMap.get(DcMotorEx.class, "back_left");
        }
        omniIntake = hardwareMap.get(DcMotorEx.class, "omni_intake");
        compliantIntake = hardwareMap.get(DcMotorEx.class, "compliant_intake");

        frontJoint = hardwareMap.get(Servo.class, "front_joint");
        frontElbow = hardwareMap.get(Servo.class, "front_elbow");

        backJoint = hardwareMap.get(Servo.class, "back_joint");
        backElbow = hardwareMap.get(Servo.class, "back_elbow");

        arm = hardwareMap.get(DcMotorEx.class, "arm");

        blockRotator = hardwareMap.get(Servo.class, "block_rotator");
        blockGripper = hardwareMap.get(Servo.class, "block_gripper");

        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_distance");
    }

    // Used by TeleOp
    public DeviceManager(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }


}
