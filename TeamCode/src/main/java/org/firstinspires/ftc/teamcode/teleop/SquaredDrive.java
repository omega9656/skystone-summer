package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Squared Drive")
public class SquaredDrive extends OmegaTeleOp {
    public DriveMode getCurrentMode() {
        return DriveMode.SQUARED;
    }
}
