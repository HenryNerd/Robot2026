package frc.robot.subsystems.intake;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Current;

public class IntakeIOReal implements IntakeIO {
  private final TalonFX leftMotor;
  private final TalonFX rightMotor;
  private final DutyCycleOut dutyCycle;

  private final StatusSignal<Current> leftCurrentSignal;
  private final StatusSignal<Current> rightCurrentSignal;

  public IntakeIOReal() {
    leftMotor = new TalonFX(IntakeConstants.intakeLeftMotorId);
    rightMotor = new TalonFX(IntakeConstants.intakeRightMotorId);

    leftCurrentSignal = leftMotor.getSupplyCurrent();
    rightCurrentSignal = rightMotor.getSupplyCurrent();

    dutyCycle = new DutyCycleOut(0).withEnableFOC(true);
  }

  @Override
  public void updateInputs(IntakeIOInputs inputs) {
    inputs.isLeftMotorConnected = leftMotor.isConnected();
    inputs.isRightMotorConnected = rightMotor.isConnected();
    inputs.leftVelocity = leftMotor.getVelocity().getValue();
    inputs.rightVelocity = rightMotor.getVelocity().getValue();
    inputs.leftTemp = leftMotor.getDeviceTemp().getValue();
    inputs.rightTemp = rightMotor.getDeviceTemp().getValue();

    BaseStatusSignal.refreshAll(leftCurrentSignal, rightCurrentSignal);
    inputs.leftCurrent = leftCurrentSignal.getValue();
    inputs.rightCurrent = rightCurrentSignal.getValue();
  }

  @Override
  public void set(double power) {
    leftMotor.setControl(dutyCycle.withOutput(power));
  }
}
