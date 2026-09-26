package bot.den.orange.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bot.den.orange.Constants;
import bot.den.orange.subsystems.Drive;
import bot.den.orange.subsystems.Intake;
import bot.den.orange.subsystems.Shooter;

@TeleOp(name = "BaseTeleop", group = "Denbot")
public class BaseTeleop extends OpMode {
    private final Drive drive = new Drive(telemetry);
    private final Shooter shooter = new Shooter(telemetry);
    private final Intake intake = new Intake(telemetry);

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

        if (gamepad1.y) {
            shooter.startLauncher();
        } else if (gamepad1.b) {
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


        drive.showTelemetry();
        shooter.showTelemetry();
        intake.showTelemetry();
    }
}

