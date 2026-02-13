package frc.robot.controls;

import badgerutils.commands.CommandUtils;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.commands.AutoAimCommand;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.ShootOnTheMoveCommand;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.shooter.Shooter;

public class CompetitionControllerMapping extends ControllerMapping {

  private final Drive drive;
  private final Shooter shooter;
  private final Intake intake;

  public CompetitionControllerMapping(
      CommandXboxController driverController,
      CommandXboxController operatorController,
      Drive drive,
      Intake intake,
      Shooter shooter) {
    super(driverController, operatorController);
    this.drive = drive;
    this.intake = intake;
    this.shooter = shooter;
  }

  @Override
  public void bind() {
    drive.setDefaultCommand(
        DriveCommands.joystickDriveCommand(
            drive,
            () -> -driverController.getLeftY(),
            () -> -driverController.getLeftX(),
            () -> -driverController.getRightX()));

    driverController
        .start()
        .onTrue(
            Commands.runOnce(
                    () ->
                        drive.setPose(
                            new Pose2d(drive.getPose().getTranslation(), Rotation2d.kZero)),
                    drive)
                .ignoringDisable(true));

    driverController
        .a()
        .whileTrue(
            new AutoAimCommand(
                drive,
                shooter,
                () -> -driverController.getLeftY(),
                () -> -driverController.getLeftX()));
    driverController
        .leftTrigger(0.5)
        .whileTrue(
            new ShootOnTheMoveCommand(
                drive,
                shooter,
                () -> -driverController.getLeftY(),
                () -> -driverController.getLeftX(),
                Constants.Locations.blueHub.toTranslation2d()));
  }

  @Override
  public void clear() {
    super.clear();
    CommandUtils.removeAndCancelDefaultCommand(drive);
  }
}
