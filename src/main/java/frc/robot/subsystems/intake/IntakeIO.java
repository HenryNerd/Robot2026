package frc.robot.subsystems.intake;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
  @AutoLog
  public static class IntakeIOInputs {
    public boolean isLeftMotorConnected = false;
    public boolean isRightMotorConnected = false;
    public AngularVelocity leftVelocity = RPM.of(0);
    public AngularVelocity rightVelocity = RPM.of(0);
    public Temperature leftTemp = Celsius.of(0);
    public Temperature rightTemp = Celsius.of(0);
    public Current leftCurrent = Amps.of(0);
    public Current rightCurrent = Amps.of(0);
  }

  public default void updateInputs(IntakeIOInputs inputs) {}

  public default void set(double power) {}
}
