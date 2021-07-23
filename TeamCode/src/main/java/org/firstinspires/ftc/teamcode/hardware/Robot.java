package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    public DeviceManager deviceManager;

    public Arm arm;
    public BackAutoGripper backAutoGripper;
    public BlockGripper blockGripper;
    public Drivetrain drivetrain;
    public Intake intake;

    public Robot(HardwareMap hardwareMap) {
        deviceManager = new DeviceManager(hardwareMap);
    }

    public void init(boolean autoIsRunning){
        deviceManager.init(autoIsRunning);
        arm = new Arm(deviceManager, !autoIsRunning);
        backAutoGripper = new BackAutoGripper(deviceManager);
        blockGripper = new BlockGripper(deviceManager);
        // drivetrain is only used for TeleOp
        if (!autoIsRunning) {
            drivetrain = new Drivetrain(deviceManager);
        }

        intake = new Intake(deviceManager);
        backAutoGripper = new BackAutoGripper(deviceManager);
    }

}
