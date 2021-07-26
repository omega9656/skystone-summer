package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Cubed Drive")
public class CubedDrive extends OmegaTeleOp {
    public DriveMode getCurrentMode() {
        return DriveMode.CUBED;
    }
}
