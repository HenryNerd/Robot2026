package frc.robot.subsystems.indexer;

import badgerutils.motor.MotorConfigUtils;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class IndexerConstants {
  public static final int INDEXER_LEFT_MOTOR_ID = -1;
  public static final int INDEXER_RIGHT_MOTOR_ID = -1;

  public static final MotorOutputConfigs LEFT_MOTOR_OUTPUT_CONFIGS =
      MotorConfigUtils.createMotorOutputConfig(
          InvertedValue.Clockwise_Positive, NeutralModeValue.Brake);
  public static final MotorOutputConfigs RIGHT_MOTOR_OUTPUT_CONFIGS =
      MotorConfigUtils.createMotorOutputConfig(
          InvertedValue.CounterClockwise_Positive, NeutralModeValue.Brake);
  public static final CurrentLimitsConfigs CURRENT_LIMITS_CONFIGS =
      new CurrentLimitsConfigs()
          .withStatorCurrentLimitEnable(false)
          .withSupplyCurrentLimitEnable(false);
}
