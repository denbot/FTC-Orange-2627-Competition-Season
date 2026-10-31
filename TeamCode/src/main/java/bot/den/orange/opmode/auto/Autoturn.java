package bot.den.orange.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import bot.den.orange.Alliance;
import bot.den.orange.subsystems.Drive;

    @Autonomous(name = "Autoturn", group = "DenBot", preselectTeleOp = "BaseTeleop")
    public class Autoturn extends OpMode {
        private enum AutoState{
            GO_FORWARD,
            TURN,
            END
        }

        private Alliance alliance = Alliance.RED;

        private final Drive drive = ne Drive(telemetry);
        private AutoState state = AutoState.GO_FORWARD;

        @Override
        public void init(){
            drive.init(hardwareMap);
            drive.resetEncoders();
        }

        @Override
        public void loop(){
            switch (state){
                case GO_FORWARD:
                    if (drive.drive(12, DistanceUnit.INCH)){
                        state = bot.den.orange.opmode.auto.Autoturn.AutoState.END;
                    }
                    break;
                case TURN:
                    int degreesToRotate = 45;
                    if (alliance == Alliance.RED) {
                        degreesToRotate *= -1;
                    }
                    if (drive.rotate(degreesToRotate, AngleUnit.DEGREES)) {
                        drive.resetEncoders();
                    }
                    break;
            }

            drive.showTelemetry();
        }
    }