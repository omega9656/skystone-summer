package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    public DeviceManager deviceManager;

    public Arm arm;
    public BlockGripper blockGripper;
    public Drivetrain drivetrain;
    public Intake intake;
    public BlockDetector blockDetector;

    // auto only
    public FrontAutoGripper frontAutoGripper;
    public BackAutoGripper backAutoGripper;

    public Robot(HardwareMap hardwareMap) {
        deviceManager = new DeviceManager(hardwareMap);
    }

    public void init(boolean autoIsRunning){
        deviceManager.init(autoIsRunning);

        // drivetrain is only used for TeleOp
        if (!autoIsRunning) {
            drivetrain = new Drivetrain(deviceManager);
        }

        arm = new Arm(deviceManager, !autoIsRunning);
        blockGripper = new BlockGripper(deviceManager);
        intake = new Intake(deviceManager);

        frontAutoGripper = new FrontAutoGripper(deviceManager);
        backAutoGripper = new BackAutoGripper(deviceManager);
    }

}
