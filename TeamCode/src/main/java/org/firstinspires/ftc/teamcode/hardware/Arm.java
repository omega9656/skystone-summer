package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Arm {
    public DcMotorEx arm;

    // "PPR" on goBILDA datasheet
    // https://www.gobilda.com/5202-series-yellow-jacket-planetary-gear-motor-50-9-1-ratio-117-rpm-3-3-5v-encoder
    public static final double TICKS_PER_REVOLUTION = 1425.1;

    public boolean usingTeleop = false;

    public enum Position {
        INIT(degreesToTicks(-30)),
        UP(degreesToTicks(-175)), // block is lifted up, almost at deposit position
        TRAVELING(degreesToTicks(-25)),
        DEPOSIT(degreesToTicks(-215)), // arm flipped around completely
        INTAKING(degreesToTicks(-40)); // arm is slightly above down position

        public int ticks;

        Position(int ticks) {
            this.ticks = ticks;
        }
    }

    public Position currentLocation = Position.TRAVELING;

    public static final double DEFAULT_POWER = 0.5;
    public double currentPower = DEFAULT_POWER;

    public static int degreesToTicks(int degrees) {
        return 2 * ((int) ((TICKS_PER_REVOLUTION / 360) * degrees));
    }

    public Arm(DeviceManager deviceManager) {
        arm = deviceManager.arm;

        // reset the encoder to zero
        arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        arm.setPower(DEFAULT_POWER);

        //init position
        init();
    }

    public Arm(DeviceManager deviceManager, boolean usingTeleop) {
        this(deviceManager);
        this.usingTeleop = usingTeleop;
    }

    public void setPosition(Position targetPosition) {
        arm.setTargetPosition(targetPosition.ticks);
        arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        currentLocation = targetPosition;
    }

    public void init(){
        setPosition(Position.INIT);
    }

    public void up(){
        setPosition(Position.UP);
    }

    public void traveling(){
        setPosition(Position.TRAVELING);
    }

    public void intaking(){
        setPosition(Position.INTAKING);
    }

    public void deposit(){
        setPosition(Position.DEPOSIT);
    }

    // TODO: add fineAdjustUp and fineAdjustDown methods for TeleOp
}