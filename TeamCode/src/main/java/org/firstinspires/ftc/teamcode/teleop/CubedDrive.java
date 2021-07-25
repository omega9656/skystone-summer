package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Cubed Drive")
public class CubedDrive extends OmegaTeleOp {
    @Override
    public void loop() {
        drive(DriveMode.CUBED, DEFAULT_STRAFE);

        intake(false);
        moveBackAutoGripper();
        moveArm();
        moveBlockGripper();

        showTelemetry();
    }
}
