package org.usfirst.frc.team449.robot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.wpi.first.wpilibj.command.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.usfirst.frc.team449.robot.drive.unidirectional.DriveTalonCluster;
import org.usfirst.frc.team449.robot.jacksonWrappers.MappedDigitalInput;
import org.usfirst.frc.team449.robot.jacksonWrappers.MappedRunnable;
import org.usfirst.frc.team449.robot.jacksonWrappers.YamlCommand;
import org.usfirst.frc.team449.robot.oi.OI;
import org.usfirst.frc.team449.robot.oi.buttons.CommandButton;
import org.usfirst.frc.team449.robot.other.Logger;
import org.usfirst.frc.team449.robot.other.Updater;
import org.usfirst.frc.team449.robot.subsystem.interfaces.solenoid.SolenoidSimple;
import org.usfirst.frc.team449.robot.subsystem.singleImplementation.pneumatics.Pneumatics;

import java.util.ArrayList;
import java.util.List;

/**
 * The Jackson-compatible object representing the entire robot.
 */
public class RobotBallbasaur {

	/**
	 * The OI for controlling this robot's drive.
	 */
	@NotNull
	private final OI oi;

	/**
	 * The logger for recording events and telemetry data.
	 */
	@NotNull
	private final Logger logger;

	/**
	 * The drive.
	 */
	@NotNull
	private final DriveTalonCluster drive;

	/**
	 * The command for the drive to run during the teleoperated period.
	 */
	@NotNull
	private final Command defaultDriveCommand;

	/**
	 * A runnable that updates cached variables.
	 */
	@NotNull
	private final Runnable updater;


	/**
	 * The pneumatics on this robot. Can be null.
	 */
	@Nullable
	private final Pneumatics pneumatics;


	/**
	 * The command to be run when first enabled.
	 */
	@Nullable
	private final Command startupCommand;


	@JsonCreator
	public RobotBallbasaur(@NotNull @JsonProperty(required = true) OI oi,
	                       @NotNull @JsonProperty(required = true) Logger logger,
	                       @NotNull @JsonProperty(required = true) DriveTalonCluster drive,
	                       @NotNull @JsonProperty(required = true) YamlCommand defaultDriveCommand,
	                       @NotNull @JsonProperty(required = true) MappedRunnable updater,
	                       @Nullable Pneumatics pneumatics,
	                       @Nullable YamlCommand startupCommand) {
		this.oi = oi;
		this.drive = drive;
		this.pneumatics = pneumatics;
		this.logger = logger;
		this.updater = updater;
		this.defaultDriveCommand = defaultDriveCommand.getCommand();
		this.startupCommand = startupCommand != null ? startupCommand.getCommand() : null;
	}

	/**
	 * @return The OI for controlling this robot's drive.
	 */
	@NotNull
	public OI getOI() {
		return oi;
	}

	/**
	 * @return The logger for recording events and telemetry data.
	 */
	@NotNull
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @return The drive.
	 */
	@NotNull
	public DriveTalonCluster getDrive() {
		return drive;
	}

	/**
	 * @return The command for the drive to run during the teleoperated period.
	 */
	@NotNull
	public Command getDefaultDriveCommand() {
		return defaultDriveCommand;
	}


	/**
	 * @return The command to be run when first enabled.
	 */
	@Nullable
	public Command getStartupCommand() {
		return startupCommand;
	}

	/**
	 * @return A runnable that updates cached variables.
	 */
	@NotNull
	public Runnable getUpdater() {
		return updater;
	}
}