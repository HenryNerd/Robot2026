package frc.robot.controls;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

// test class, not meant to stay around for competition.
public class RemoveMeControllerMapping extends ControllerMapping {

  public RemoveMeControllerMapping(
      CommandXboxController driverController, CommandXboxController operatorController) {
    super(driverController, operatorController);
  }

  @Override
  public void bind() {}
}
