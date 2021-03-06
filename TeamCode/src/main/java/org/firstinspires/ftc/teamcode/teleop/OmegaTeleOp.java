package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.hardware.Arm;
import org.firstinspires.ftc.teamcode.hardware.Robot;


public abstract class OmegaTeleOp extends OpMode {
    Robot robot;
    ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    boolean stalled = false;
    boolean pickedUp = false;

    // Different drive modes
    enum DriveMode {
        NORMAL,
        SQUARED,
        CUBED
    }

    public static final double DEFAULT_STRAFE = 2;

    abstract public DriveMode getCurrentMode();

    @Override
    public void init() {
        robot = new Robot(hardwareMap);
        robot.init(false);
        time.reset();
    }

    @Override
    public void loop() {
        drive(DEFAULT_STRAFE);

        intake(true);
        moveBackAutoGripper();
        moveBlockGripper();
        sensorPickup();

        showTelemetry();
    }

    public void sensorPickup() {
        final int RUN_MILLISECONDS = 150;

        if (robot.blockDetector.isBlockIntaked() && !pickedUp) {
            pickedUp = true;

            robot.blockGripper.release();
            // Create a timer for running certain tasks for a certain amount of time
            ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

            timer.startTime();
            while (timer.milliseconds() < RUN_MILLISECONDS) {
                drive(DEFAULT_STRAFE);
            }

            robot.arm.intaking();
            timer.reset();
            timer.startTime();
            while (timer.milliseconds() < RUN_MILLISECONDS) {
                drive(DEFAULT_STRAFE);
            }

            robot.blockGripper.grab();
            timer.reset();
            timer.startTime();
            while (timer.milliseconds() < RUN_MILLISECONDS) {
                drive(DEFAULT_STRAFE);
            }

            robot.arm.traveling();
        }
    }

    public void drive(double strafe) {
        // https://gm0.copperforge.cc/en/stable/docs/software/mecanum-drive.html
        // https://www.chiefdelphi.com/t/paper-mecanum-and-omni-kinematic-and-force-analysis/106153/5 (3rd paper)


        // moving left joystick up means robot moves forward
        double vertical = -gamepad1.left_stick_y;  // flip sign because y axis is reversed on joystick

        // moving left joystick to the right means robot moves right
        double horizontal = gamepad1.left_stick_x * strafe;  // counteract imperfect strafing by multiplying by constant

        // moving right joystick to the right means clockwise rotation of robot
        double rotate = gamepad1.right_stick_x;

        // calculate initial power from gamepad inputs
        // to understand this, draw force vector diagrams (break into components)
        // and observe the goBILDA diagram on the GM0 page (linked above)
        double frontLeftPower = vertical + horizontal + rotate;
        double backLeftPower = vertical - horizontal + rotate;
        double frontRightPower = vertical - horizontal - rotate;
        double backRightPower = vertical + horizontal - rotate;

        // if there is a power level that is out of range
        if (
                Math.abs(frontLeftPower) > 1 ||
                        Math.abs(backLeftPower) > 1 ||
                        Math.abs(frontRightPower) > 1 ||
                        Math.abs(backRightPower) > 1
        ) {
            // scale the power within [-1, 1] to keep the power levels proportional
            // (if the power is over 1 the FTC SDK will just make it 1)

            // find the largest power
            double max = Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower));
            max = Math.max(Math.abs(frontRightPower), max);
            max = Math.max(Math.abs(backRightPower), max);

            // scale everything with the ratio max:1
            // don't need to worry about signs because max is positive
            frontLeftPower /= max;
            backLeftPower /= max;
            frontRightPower /= max;
            backRightPower /= max;
        }

        // square or cube gamepad inputs
        if (getCurrentMode() == DriveMode.SQUARED) {
            // need to keep the sign, so multiply by absolute value of itself
            frontLeftPower *= Math.abs(frontLeftPower);
            backLeftPower *= Math.abs(backLeftPower);
            frontRightPower *= Math.abs(frontRightPower);
            backRightPower *= Math.abs(backRightPower);
        } else if (getCurrentMode() == DriveMode.CUBED) {
            frontLeftPower = Math.pow(frontLeftPower, 3);
            backLeftPower = Math.pow(backLeftPower, 3);
            frontRightPower = Math.pow(frontRightPower, 3);
            backRightPower = Math.pow(backRightPower, 3);
        } // if drive mode is normal, don't do anything

        // set final power values to motors
        robot.deviceManager.frontLeft.setPower(frontLeftPower);
        robot.deviceManager.backLeft.setPower(backLeftPower);
        robot.deviceManager.frontRight.setPower(frontRightPower);
        robot.deviceManager.backRight.setPower(backRightPower);
    }


    public void intake(boolean stallAutomation) {
        // time to reverse if intake motor current is above stall current
        // TODO tune this as needed
        final double WAIT_TIME = 500; // milliseconds

        // https://www.gobilda.com/5202-series-yellow-jacket-planetary-gear-motor-13-7-1-ratio-435-rpm-3-3-5v-encoder/
        final double STALL_CURRENT = 9.2; // amps

        if (gamepad2.left_bumper) {
            robot.intake.in();

            // TODO: fix stall automation
            if (stallAutomation) {
                // if the intake motor is stalling, run intake outward
                if ((robot.deviceManager.compliantIntake.getCurrent(CurrentUnit.AMPS) >= STALL_CURRENT
                        || robot.deviceManager.omniIntake.getCurrent(CurrentUnit.AMPS) >= STALL_CURRENT)
                        && !stalled) {
                    stalled = true;
                    robot.intake.out();
                    time.reset();
                }

                // if the time we run the intake outward is expired, stop the intake
                if (time.time() > WAIT_TIME && stalled) {
                    robot.intake.stop();
                    stalled = false;
                }
            }
        } else if (gamepad2.right_bumper) {
            robot.intake.out();
        } else {
            robot.intake.stop();
        }
    }

    // temporary method
    public void moveBackAutoGripper(){
        if (gamepad1.a){
            robot.backAutoGripper.up();
        }

        if (gamepad1.b){
            robot.backAutoGripper.grab();
        }
    }

    public void moveArm(){
        if (gamepad2.dpad_up) {
            // if the arm is at UP and the up button is pressed again,
            // the arm goes into deposit mode
            if (robot.arm.currentLocation == Arm.Position.UP) {
                robot.arm.deposit();
            } else {
                robot.arm.up();
            }
        }

        if (gamepad2.dpad_down) {
            robot.arm.deposit();
        }

        if (gamepad2.dpad_left){
            robot.arm.traveling();
        }

        if (gamepad2.dpad_right){
            robot.arm.intaking();
        }
    }

    public void moveBlockGripper(){
        if (gamepad2.a){
            robot.blockGripper.ready();
        }

        if (gamepad2.b){
            robot.blockGripper.release();
        }

        if (gamepad2.x){
            robot.blockGripper.grab();
        }
    }

    public void showTelemetry(){
        telemetry.addLine("Arm")
                .addData("Ticks", robot.arm.arm.getCurrentPosition())
                .addData("Location", robot.arm.currentLocation.ticks)
                .addData("Power", robot.arm.arm.getPower());

        telemetry.addLine("Intake")
                .addData("Compliant Power", robot.intake.compliantIntake.getPower())
                .addData("Omni Power", robot.intake.omniIntake.getPower());

        telemetry.addLine("Block Gripper")
                .addData("Block Gripper Pos", robot.blockGripper.blockGripper.getPosition())
                .addData("block_gripper port", robot.blockGripper.blockGripper.getPortNumber());

        telemetry.addLine("Back Auto Gripper")
                .addData("Elbow Port", robot.backAutoGripper.backElbow.getPortNumber())
                .addData("Joint Port", robot.backAutoGripper.backJoint.getPortNumber())
                .addData("Elbow Pos", robot.backAutoGripper.backElbow.getPosition())
                .addData("Joint Pos", robot.backAutoGripper.backJoint.getPosition());

        telemetry.addLine("Drivetrain")
                .addData("front_left port", robot.drivetrain.frontLeft.getPortNumber())
                .addData("front_right port", robot.drivetrain.frontRight.getPortNumber())
                .addData("back_left port", robot.drivetrain.backLeft.getPortNumber())
                .addData("back_right port", robot.drivetrain.backRight.getPortNumber());
    }

}
