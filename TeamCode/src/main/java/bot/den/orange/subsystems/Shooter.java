package bot.den.orange.subsystems;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import bot.den.orange.Constants;

public class Shooter implements BaseSubsystem {

    private final Telemetry telemetry;
    private DcMotorEx launcher = null;
    private CRServo launcherServo = null;

    public Shooter(Telemetry telemetry){
        this.telemetry=telemetry;
    }

    public void init (HardwareMap hardwareMap){
        launcher = hardwareMap.get(DcMotorEx.class, Constants.Robot.ConfigNames.launcher);
        launcherServo = hardwareMap.get(CRServo.class, Constants.Robot.ConfigNames.launcherServo);

        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        launcher.setZeroPowerBehavior(BRAKE);

        launcherServo.setPower(Constants.Shooter.feederStopPower);

        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0, 10));

        launcherServo.setDirection(DcMotorSimple.Direction.REVERSE);
        launcher.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void startLauncher(){
        launcher.setVelocity(Constants.Shooter.launcherTargetVelocityRpm);
    }

    public void stopLauncher(){
        launcher.setVelocity(Constants.Shooter.launcherStopVelocityRpm);
    }
    public void runFeederReverse(){
        launcherServo.setPower(Constants.Shooter.feederReversePower);
    };
    public void showTelemetry(){
        telemetry.addData("shooterMotorSpeed", launcher.getVelocity());
        telemetry.addData("shooterServoPower", launcherServo.getPower());
    }

    public void shoot(){
        if (launcher.getVelocity() > Constants.Shooter.launcherMinVelocityRpm){
            launcherServo.setPower(Constants.Shooter.feederServoPower);
        }
        else{
            startLauncher();
        }
    }
    public void stop(){
        stopLauncher();
        launcherServo.setPower(Constants.Shooter.feederStopPower);
    }
}
