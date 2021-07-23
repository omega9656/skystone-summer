package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Intake {
    public MotorPower currentPower; //declaring MotorPower
    public DcMotorEx omniIntake; //declaring omniIntake
    public DcMotorEx compliantIntake; //declaring compliantIntake

    public Intake(DeviceManager deviceManager) {
        compliantIntake = deviceManager.compliantIntake;

        compliantIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // when ran, ran without encoder?
        compliantIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // when power is 0, BRAKE

        omniIntake = deviceManager.omniIntake;
        omniIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        omniIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // for the omniintake, BRAKE when power = 0

        currentPower = MotorPower.STOP; // default power is at STOP(0)
    }

    public enum MotorPower {
        // set the power for the intake at different states
        IN(-1.0, 1.0),
        OUT(0.5, -0.5),
        STOP(0, 0);

        public double omniPower;
        public double compliantPower;

        MotorPower(double omniPower, double compliantPower){
            this.omniPower = omniPower;
            this.compliantPower = compliantPower;
        }
    }

    public void run(MotorPower motorPower){
        omniIntake.setPower(motorPower.omniPower);
        compliantIntake.setPower(motorPower.compliantPower);
        currentPower = motorPower;
    }

    public void in(){
        run(MotorPower.IN); //calling the run method for the IN power
    }
    public void out(){
        run(MotorPower.OUT); //calling the run method for the OUT power
    }
    public void stop(){
        run(MotorPower.STOP); //calling the run method for the STOP power
    }
}