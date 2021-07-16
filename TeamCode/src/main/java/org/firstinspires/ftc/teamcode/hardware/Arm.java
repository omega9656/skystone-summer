package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Arm {
    public DcMotorEx arm;

    // "PPR" on goBILDA datasheet
    // https://www.gobilda.com/5202-series-yellow-jacket-planetary-gear-motor-50-9-1-ratio-117-rpm-3-3-5v-encoder
    public static final double TICKS_PER_REVOLUTION = 1425.1;

    // constants stored as encoder ticks,
    // higher value is counter-clockwise

    /*
    * INIT:      -63  deg
    * DOWN:      -25  deg
    * UP:        -354 deg
    * DEPOSIT:   -429 deg
    * TRAVELING: -53  deg
    * INTAKING:  -88  deg
    *
    * */

    public static final int ARM_INIT = -250;

    public boolean usingTeleop = false;

    public enum Position {


/*        UP(-1400), // block is lifted up, almost at deposit position
        DOWN(-100),
        TRAVELING(-210),
        DEPOSIT(-1700), // arm flipped around completely
        INTAKING(-350); // arm is slightly above down position*/

        UP(degreesToTicks(-354)), // block is lifted up, almost at deposit position
        DOWN(degreesToTicks(-25)),
        TRAVELING(degreesToTicks(-53)),
        DEPOSIT(degreesToTicks(-429)), // arm flipped around completely
        INTAKING(degreesToTicks(-88)); // arm is slightly above down position

        public int ticks;

        Position(int ticks) {
            this.ticks = ticks;
        }
    }

    public Position currentLocation = Position.TRAVELING;

    public static final double DEFAULT_POWER = 0.5;
    public double currentPower = DEFAULT_POWER;

    public static int degreesToTicks(int degrees) {
        return (int) ((TICKS_PER_REVOLUTION / 360) * degrees);
    }

    public Arm(DeviceManager deviceManager) {
        arm = deviceManager.arm;

        // reset the encoder to zero
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(ARM_INIT);
        arm.setPower(DEFAULT_POWER);
    }

    public Arm(DeviceManager deviceManager, boolean usingTeleop) {
        this(deviceManager);
        this.usingTeleop = usingTeleop;
    }

    // this method is called every frame
    void process() {
        // ignore direction motor may have previously been set to
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setTargetPosition(currentLocation.ticks);
        arm.setPower(currentPower);
    }

    public void setTicks(int ticks) {
        currentLocation.ticks = ticks;
    }

    public int getTicks() {
        return currentLocation.ticks;
    }

    public void setPosition(Position targetPosition) {
        if (usingTeleop && targetPosition == Position.UP) {
            currentLocation = Position.DEPOSIT;
        } else {
            currentLocation = targetPosition;
        }
    }

    // TODO: add fineAdjustUp and fineAdjustDown methods for TeleOp
}