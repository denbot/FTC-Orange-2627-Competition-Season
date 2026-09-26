package bot.den.orange.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface BaseSubsystem {
    public void init(HardwareMap hardwareMap);
    public void showTelemetry();
}
