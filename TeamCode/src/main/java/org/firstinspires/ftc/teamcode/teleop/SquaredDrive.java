package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Squared Drive")
public class SquaredDrive extends OmegaTeleOp {
    @Override
    public void loop() {
        drive(DriveMode.SQUARED, DEFAULT_STRAFE);

        intake(false);
        moveBackAutoGripper();
        moveArm();
        moveBlockGripper();

        showTelemetry();

    }
}
