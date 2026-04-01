package frc.robot.subsystems.deploy;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Deploy extends SubsystemBase {
  private final DeployIOInputsAutoLogged inputs = new DeployIOInputsAutoLogged();
  private final DeployIO deployIO;

  public Deploy(DeployIO deployIO) {
    this.deployIO = deployIO;
  }

  @Override
  public void periodic() {
    deployIO.updateInputs(inputs);
    Logger.processInputs("Intake", inputs);
  }

  public void setDeployerPosition(DeployerPosition position) {
    deployIO.setPosition(position.getAngle());
  }

  public Command deployCommand() {
    return Commands.runOnce(() -> setDeployerPosition(DeployerPosition.EXTENDED), this);
  }

  public Command retractCommand() {
    return Commands.runOnce(() -> setDeployerPosition(DeployerPosition.RETRACTED), this);
  }

  public Command crunchCommand() {
    return Commands.startEnd(
        () -> setDeployerPosition(DeployerPosition.RETRACTED),
        () -> setDeployerPosition(DeployerPosition.EXTENDED),
        this);
  }
}
