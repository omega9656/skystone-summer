package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Normal Drive")
public class NormalDrive extends OmegaTeleOp {
    public DriveMode getCurrentMode() {
        return DriveMode.NORMAL;
    }
}
