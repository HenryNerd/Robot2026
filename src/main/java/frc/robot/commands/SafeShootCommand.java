package frc.robot.commands;

import static edu.wpi.first.units.Units.Meters;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.indexer.Indexer;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.shooter.Shooter;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import org.littletonrobotics.junction.Logger;

public class SafeShootCommand extends ParallelCommandGroup {
  private static Rotation2d ANGLE_TOLERANCE = Rotation2d.fromDegrees(5);

  private static double INDEXER_SPEED = 1;
  private static double INTAKE_SPEED = 0.5;

  private boolean isActive;
  private boolean hasShotFirstShot = false;

  public SafeShootCommand(
      Drive drive,
      Shooter shooter,
      Indexer indexer,
      Intake intake,
      Supplier<Translation2d> positionSupplier,
      BooleanSupplier overrideAngleSafeguard,
      BooleanSupplier overrideVelocitySafeguard) {

    BooleanSupplier shooterVelocityCondition = shooter.isAtRequestedSpeed();

    BooleanSupplier driveAngleCondition =
        () -> drive.isLocked(drive, positionSupplier.get(), true, ANGLE_TOLERANCE);

    Command guardedIndexerCommand =
        new GuardedCommand(
                new ConditionalCommand(
                        new InstantCommand(), new WaitCommand(0.25), () -> hasShotFirstShot)
                    .andThen(
                        indexer
                            .indexUntilCancelledCommand(INDEXER_SPEED)
                            .alongWith(new InstantCommand(() -> hasShotFirstShot = true))),
                () ->
                    (shooterVelocityCondition.getAsBoolean()
                            || overrideVelocitySafeguard.getAsBoolean())
                        && (driveAngleCondition.getAsBoolean()
                            || overrideAngleSafeguard.getAsBoolean()))
            .finallyDo(() -> hasShotFirstShot = false);

    Command shootAtDistanceCommand =
        ShooterCommands.shootAtDistanceCommand(
                shooter,
                () ->
                    Meters.of(drive.getPose().getTranslation().getDistance(positionSupplier.get())))
            .withInterruptBehavior(InterruptionBehavior.kCancelIncoming);

    Command intakeCommand = intake.shakeIntake();

    Trigger intakeRescheduler =
        new Trigger(
                () ->
                    this.isActive
                        && (intake.getCurrentCommand() == null
                            || intake.getCurrentCommand() == intake.getDefaultCommand()))
            .onTrue(intakeCommand);
    Trigger indexerRescheduler =
        new Trigger(
                () ->
                    this.isActive
                        && (indexer.getCurrentCommand() == null
                            || indexer.getCurrentCommand() == indexer.getDefaultCommand()))
            .onTrue(guardedIndexerCommand);

    Command loggedGuardCommand =
        Commands.run(() -> logConditions(shooterVelocityCondition, driveAngleCondition));

    Command activityTracker = Commands.startEnd(() -> isActive = true, () -> isActive = false);

    addCommands(
        activityTracker,
        shootAtDistanceCommand,
        guardedIndexerCommand.asProxy(),
        loggedGuardCommand,
        intakeCommand.asProxy());
  }

  private void logConditions(
      BooleanSupplier shooterVelocityCondition, BooleanSupplier driveAngleCondition) {
    Logger.recordOutput(
        "Controls/Ready To Shoot",
        shooterVelocityCondition.getAsBoolean() && driveAngleCondition.getAsBoolean());
    Logger.recordOutput(
        "Controls/Shooter Velocity Condition", shooterVelocityCondition.getAsBoolean());
    Logger.recordOutput("Controls/Drive Angle Condition", driveAngleCondition.getAsBoolean());
  }
}
