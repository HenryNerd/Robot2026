package frc.robot.subsystems.indexer;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import frc.robot.Constants;

public class IndexerIOReal implements IndexerIO {
  private final StatusSignal<AngularVelocity> velocity;

  private final StatusSignal<Temperature> temp;

  private final StatusSignal<Current> supplyCurrent;

  private final TalonFX indexerMotor;

  public IndexerIOReal() {
    indexerMotor = new TalonFX(Constants.CanIds.INDEXER_LEFT_MOTOR_ID);

    indexerMotor.getConfigurator().apply(IndexerConstants.CW_INDEXER_MOTOR_CONFIGS);

    velocity = indexerMotor.getVelocity();

    temp = indexerMotor.getDeviceTemp();

    supplyCurrent = indexerMotor.getSupplyCurrent();
  }

  @Override
  public void updateInputs(IndexerIOInputs inputs) {
    StatusCode leftStatus = BaseStatusSignal.refreshAll(velocity, temp, supplyCurrent);

    inputs.isMotorConnected = leftStatus.isOK();

    inputs.velocity = velocity.getValue();

    inputs.isMotorConnected = indexerMotor.isConnected();

    inputs.temp = temp.getValue();

    inputs.supplyCurrent = supplyCurrent.getValue();
  }

  @Override
  public void setDutyCycle(double dutyCycle) {
    DutyCycleOut request = new DutyCycleOut(dutyCycle).withEnableFOC(true);
    indexerMotor.setControl(request);
  }
}
