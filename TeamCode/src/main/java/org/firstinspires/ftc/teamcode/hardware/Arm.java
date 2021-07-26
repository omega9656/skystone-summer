package org.firstinspires.ftc.teamcode.hardware;

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
    */

    public boolean usingTeleop = false;

    public enum Position {
//        INIT(degreesToTicks(-63)),
//        UP(degreesToTicks(-354)), // block is lifted up, almost at deposit position
//        DOWN(degreesToTicks(-25)),
//        TRAVELING(degreesToTicks(-53)),
//        DEPOSIT(degreesToTicks(-429)), // arm flipped around completely
//        INTAKING(degreesToTicks(-88)); // arm is slightly above down position

        INIT(degreesToTicks(-30)),
        UP(degreesToTicks(-175)), // block is lifted up, almost at deposit position
//        DOWN(degreesToTicks(-10)),
        TRAVELING(degreesToTicks(-25)),
        DEPOSIT(degreesToTicks(-215)), // arm flipped around completely
        INTAKING(degreesToTicks(-50)); // arm is slightly above down position

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

    // this method is called every frame
/*    void process() {
        // ignore direction motor may have previously been set to
        arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        arm.setTargetPosition(currentLocation.ticks);
        arm.setPower(currentPower);
    }*/

    public void setTicks(int ticks) {
        currentLocation.ticks = ticks;
    }

    public int getTicks() {
        return currentLocation.ticks;
    }

    public void setPosition(Position targetPosition) {

        // checks if arm is already at UP and if button for UP is pressed again
        // then sets position to DEPOSIT

        /*if (usingTeleop && currentLocation == Position.UP && targetPosition == Position.UP) {

            deposit();

        } else {
        }*/

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

//    public void down(){
//        setPosition(Position.DOWN);
//    }

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