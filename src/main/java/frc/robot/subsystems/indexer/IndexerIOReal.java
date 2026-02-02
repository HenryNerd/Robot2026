package frc.robot.subsystems.indexer;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

public class IndexerIOReal implements IndexerIO {

  private final TalonFX leftIndexerMotor;
  private final TalonFX rightIndexerMotor;

  public IndexerIOReal() {
    leftIndexerMotor = new TalonFX(IndexerConstants.INDEXER_LEFT_MOTOR_ID);
    rightIndexerMotor = new TalonFX(IndexerConstants.INDEXER_RIGHT_MOTOR_ID);
  }

  @Override
  public void updateInputs(IndexerIOInputs inputs) {
    inputs.leftVelocity = leftIndexerMotor.getVelocity().getValue();
    inputs.rightVelocity = rightIndexerMotor.getVelocity().getValue();

    inputs.isLeftMotorConnected = leftIndexerMotor.isConnected();
    inputs.isRightMotorConnected = rightIndexerMotor.isConnected();

    inputs.leftTemp = leftIndexerMotor.getDeviceTemp().getValue();
    inputs.rightTemp = rightIndexerMotor.getDeviceTemp().getValue();

    inputs.leftSupplyCurrent = leftIndexerMotor.getSupplyCurrent().getValue();
    inputs.rightSupplyCurrent = rightIndexerMotor.getSupplyCurrent().getValue();
  }

  @Override
  public void setDutyCycle(double dutyCycle) {
    DutyCycleOut request = new DutyCycleOut(dutyCycle).withEnableFOC(true);
    leftIndexerMotor.setControl(request);
    rightIndexerMotor.setControl(request);
  }
}
