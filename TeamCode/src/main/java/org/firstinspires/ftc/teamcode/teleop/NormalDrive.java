package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Normal Drive")
public class NormalDrive extends OmegaTeleOp {
    @Override
    public void loop() {
        drive(DriveMode.NORMAL, DEFAULT_STRAFE);

        intake(false);
        moveBackAutoGripper();
        moveArm();
        moveBlockGripper();

        showTelemetry();
    }
}
