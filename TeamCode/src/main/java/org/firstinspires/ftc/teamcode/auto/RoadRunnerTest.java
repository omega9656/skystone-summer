package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Robot;

public class RoadRunnerTest extends LinearOpMode {
    Robot robot;
    SampleMecanumDrive drive;

    Pose2d startPose = new Pose2d(0, 0, 0);

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap);
        robot.init(true);

        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startPose);

        if (opModeIsActive() && !isStopRequested()) {
            executeAutoPath();
        }
    }

    public void executeAutoPath() {
        Trajectory tr = drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(10, 0), 0)
                .splineTo(new Vector2d(10, 15), 0)
                .splineTo(new Vector2d(10, 5), 0)
                .splineTo(new Vector2d(0, 5), 0)
                .splineTo(new Vector2d(0, 0), 0)
                .addTemporalMarker(2, () -> {
//                    drive.followTrajectory(drive.trajectoryBuilder(startPose).build());

                    robot.drivetrain.backLeft.setPower(0);
                    robot.drivetrain.backRight.setPower(0);
                    robot.drivetrain.frontLeft.setPower(0);
                    robot.drivetrain.frontRight.setPower(0);
                })
                .build();

        drive.followTrajectory(tr);
    }
}