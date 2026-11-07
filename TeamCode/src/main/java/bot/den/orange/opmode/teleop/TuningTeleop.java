package bot.den.orange.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bot.den.orange.Constants;
import bot.den.orange.subsystems.Drive;
import bot.den.orange.subsystems.Intake;
import bot.den.orange.subsystems.Shooter;

@TeleOp(name = "TuningTeleop", group = "util")
public class TuningTeleop extends OpMode {
    private final Drive drive = new Drive(telemetry);
    private final Shooter shooter = new Shooter(telemetry);
    private final Intake intake = new Intake(telemetry);
    private boolean tuningShooter = true;

    @Override
    public void init() {
        drive.init(hardwareMap);
        shooter.init(hardwareMap);
        intake.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
    }
    @Override
    public void loop() {
        drive.arcadeDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x);

        if (gamepad1.triangle) {
            shooter.startLauncher();
        } else if (gamepad1.square) {
            shooter.stopLauncher();
        }

        if(gamepad1.right_bumper){
            shooter.shoot();
        }
        else if(gamepad1.rightBumperWasReleased()){
            shooter.stop();
        }

        if (gamepad1.left_bumper){
            intake.intake();
        }
        else if(gamepad1.left_trigger > 0.5){
            intake.outtake();
        }
        else{
            intake.stopIntake();
        }

        if(gamepad1.circleWasPressed()){
            tuningShooter = !tuningShooter;
        }

        if(tuningShooter){
            if(gamepad1.dpadUpWasPressed()){
                shooter.changeSpeed(Constants.Shooter.launcherVelocityBumpRpm);
            }
            else if(gamepad1.dpadDownWasPressed()){
                shooter.changeSpeed(-Constants.Shooter.launcherVelocityBumpRpm);
            }
        }
        else{
            if(gamepad1.dpadUpWasPressed()){
                intake.changeIntakePower(Constants.Intake.intakeMotorPowerBump);
            }
            else if(gamepad1.dpadDownWasPressed()){
                intake.changeIntakePower(-Constants.Intake.intakeMotorPowerBump);
            }
            if(gamepad1.dpadRightWasPressed()){
                intake.changeIntakeServoPower(Constants.Intake.intakeServoPowerBump);
            }
            else if(gamepad1.dpadLeftWasPressed()){
                intake.changeIntakeServoPower(-Constants.Intake.intakeServoPowerBump);
            }
        }

        drive.showTelemetry();
        shooter.showTelemetry();
        intake.showTelemetry();
    }
}

