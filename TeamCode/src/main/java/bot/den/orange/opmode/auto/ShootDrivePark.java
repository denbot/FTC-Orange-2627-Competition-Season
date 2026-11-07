package bot.den.orange.opmode.auto;

import static bot.den.orange.opmode.auto.ShootDrivePark.AutoState.PARK;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import bot.den.orange.subsystems.Drive;
import bot.den.orange.subsystems.Intake;
import bot.den.orange.subsystems.Shooter;

@Autonomous(name = "ShootDrivePark", group = "Orange", preselectTeleOp = "BaseTeleop" )
public class ShootDrivePark extends OpMode {
    enum AutoState {
        SHOOT,
        DRIVE,
        TURN,
        DRIVE_B,
        TURN_B,
        DRIVE_C,
        TURN_C,
        PARK,
        END
    }
    private final Drive drive = new Drive(telemetry);
    private final Shooter shooter = new Shooter(telemetry);
    private final Intake intake = new Intake(telemetry);
    private AutoState state = AutoState.SHOOT;
    private final ElapsedTime shotTimer = new ElapsedTime();

    @Override
    public void init(){
        drive.init(hardwareMap);
        drive.resetEncoders();
        shooter.init(hardwareMap);
        intake.init(hardwareMap);
    }

    public void start(){
        shotTimer.reset();
    }

    @Override
    public void loop() {
        double degreesToRotate = 0;
        switch (state) {
            case SHOOT:
                shooter.startLauncher();
                if (shotTimer.seconds() > 5) {
                    shooter.stopLauncher();
                    drive.resetEncoders();
                }
                state = AutoState.DRIVE;
                break;
            case DRIVE:
                shooter.stopLauncher();
                shooter.stop();
                if (drive.drive(24, DistanceUnit.INCH)) {
                    drive.resetEncoders();
                    state = AutoState.TURN;
                }
                break;
            case TURN:
                degreesToRotate = -90;
                if (drive.rotate(degreesToRotate, AngleUnit.DEGREES)) {
                    drive.resetEncoders();
                    state = AutoState.DRIVE_B;
                }
                break;
            case DRIVE_B:
                if (drive.drive(28, DistanceUnit.INCH)) {
                    drive.resetEncoders();
                    state = AutoState.TURN_B;
                }
                break;
            case TURN_B:
                degreesToRotate = 90;
                if (drive.rotate(degreesToRotate, AngleUnit.DEGREES)) {
                    drive.resetEncoders();
                    state = AutoState.DRIVE_C;
                }
                break;
            case DRIVE_C:
                degreesToRotate = -90;
                if (drive.rotate(degreesToRotate, AngleUnit.DEGREES)) {
                    drive.resetEncoders();
                    state = AutoState.TURN_C;
                }
                break;
            case TURN_C:
                if (drive.drive(24, DistanceUnit.INCH)) {
                    drive.resetEncoders();
                    state = PARK;
                }
                break;
            case PARK:
                if (drive.drive(70, DistanceUnit.INCH)) {
                    drive.resetEncoders();
                    state = AutoState.END;
        }
    }
}
