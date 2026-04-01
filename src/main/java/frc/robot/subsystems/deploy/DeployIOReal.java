package frc.robot.subsystems.deploy;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import frc.robot.Constants;

public class DeployIOReal implements DeployIO {
  // motors
  private final TalonFX motor;

  // status signals
  private final StatusSignal<Current> deployerSupplyCurrent;

  private final StatusSignal<Temperature> deployerTemperature;

  private final StatusSignal<Angle> deployerPosition;

  // control
  private final PositionTorqueCurrentFOC positionRequest;

  public DeployIOReal() {
    // motors
    motor = new TalonFX(Constants.CanIds.DEPLOYER_MOTOR_ID);

    // configs
    motor.getConfigurator().apply(DeployConstants.DEPLOYER_MOTOR_CONFIGS);

    deployerSupplyCurrent = motor.getSupplyCurrent();
    deployerTemperature = motor.getDeviceTemp();
    deployerPosition = motor.getPosition();

    // control
    positionRequest = new PositionTorqueCurrentFOC(Degrees.of(0));
  }

  @Override
  public void updateInputs(DeployIOInputs inputs) {
    StatusCode motorStatus =
        BaseStatusSignal.refreshAll(deployerSupplyCurrent, deployerTemperature, deployerPosition);

    inputs.isDeployerMotorConnected = motorStatus.isOK();

    inputs.deployerSupplyCurrent = deployerSupplyCurrent.getValue();

    inputs.deployerTemp = deployerTemperature.getValue();

    inputs.deployerPosition = deployerPosition.getValue();
  }

  @Override
  public void setPosition(Angle angle) {
    motor.setControl(positionRequest.withPosition(angle));
  }
}
