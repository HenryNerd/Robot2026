package frc.robot.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.indexer.Indexer;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.leds.Leds;
import frc.robot.subsystems.shooter.Shooter;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import static edu.wpi.first.units.Units.Feet;

public class SafeAimAndShootCommand extends ParallelCommandGroup {

  private static final Distance MINIMUM_SHOT_DISTANCE = Feet.of(7.5);
  
  public SafeAimAndShootCommand(
      Drive drive,
      Shooter shooter,
      Indexer indexer,
      Intake intake,
      Leds leds,
      DoubleSupplier xSupplier,
      DoubleSupplier ySupplier,
      Supplier<Translation2d> positionSupplier,
      Rotation2d angleTolerance,
      BooleanSupplier overrideAngleSafeguard,
      BooleanSupplier overrideVelocitySafeguard,
      BooleanSupplier overrideHubActive) {

    Command safeShootCommand =
        new SafeShootCommand(
            drive,
            shooter,
            indexer,
            intake,
            leds,
            positionSupplier,
            angleTolerance,
            overrideAngleSafeguard,
            overrideVelocitySafeguard,
            overrideHubActive);

    Command driveAtAngleCommand =
        DriveCommands.driveAimLockedCommand(drive, xSupplier, ySupplier, positionSupplier, true);

    Command moveIntoRangeCommand =
            new MoveIntoRangeCommand(drive, positionSupplier.get(), MINIMUM_SHOT_DISTANCE);

    BooleanSupplier fartherThanCondition =
            () -> drive.fartherThan(positionSupplier.get(), MINIMUM_SHOT_DISTANCE);

    BooleanSupplier driveAngleCondition = () -> drive.isLocked(drive, positionSupplier.get(), true, angleTolerance);

    Command guardedRangeCommand =
            new GuardedCommand(
                    moveIntoRangeCommand,
                    driveAngleCondition,
                    () -> !fartherThanCondition.getAsBoolean());
//                    () -> !autoRangingOverride.getAsBoolean());
    
    addCommands(safeShootCommand, driveAtAngleCommand, guardedRangeCommand);
  }
}
