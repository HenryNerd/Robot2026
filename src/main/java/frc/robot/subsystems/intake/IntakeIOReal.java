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

  private final TalonFX latchMotor;
  private final StatusSignal<Current> latchCurrentSignal;

  private final StatusSignal<Current> leftCurrentSignal;
  private final StatusSignal<Current> rightCurrentSignal;

  public IntakeIOReal() {
    leftMotor = new TalonFX(IntakeConstants.intakeLeftMotorId);
    rightMotor = new TalonFX(IntakeConstants.intakeRightMotorId);

    leftCurrentSignal = leftMotor.getSupplyCurrent();
    rightCurrentSignal = rightMotor.getSupplyCurrent();

    latchMotor = new TalonFX(IntakeConstants.latchMotorId);
    latchCurrentSignal = latchMotor.getSupplyCurrent();

    dutyCycle = new DutyCycleOut(0).withEnableFOC(true);
  }

  @Override
  public void updateInputs(IntakeIOInputs inputs) {
    inputs.isLeftMotorConnected = leftMotor.isConnected();
    inputs.isRightMotorConnected = rightMotor.isConnected();
    inputs.isLatchMotorConnected = latchMotor.isConnected();
    inputs.leftVelocity = leftMotor.getVelocity().getValue();
    inputs.rightVelocity = rightMotor.getVelocity().getValue();
    inputs.latchPosition = latchMotor.getPosition().getValue();
    inputs.leftTemp = leftMotor.getDeviceTemp().getValue();
    inputs.rightTemp = rightMotor.getDeviceTemp().getValue();
    inputs.latchTemp = latchMotor.getDeviceTemp().getValue();

    BaseStatusSignal.refreshAll(leftCurrentSignal, rightCurrentSignal, latchCurrentSignal);
    inputs.leftCurrent = leftCurrentSignal.getValue();
    inputs.rightCurrent = rightCurrentSignal.getValue();
    inputs.latchCurrent = latchCurrentSignal.getValue();
  }

  @Override
  public void set(double power) {
    leftMotor.setControl(dutyCycle.withOutput(power));
  }
  @Override
  public void extend() {
    latchMotor.setPosition(IntakeConstants.latchExtensionAngle);
  }
}
