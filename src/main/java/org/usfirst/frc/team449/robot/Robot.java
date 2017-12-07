package org.usfirst.frc.team449.robot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.usfirst.frc.team449.robot.drive.unidirectional.DriveTalonCluster;
import org.usfirst.frc.team449.robot.other.Clock;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class Robot extends IterativeRobot {

	/**
	 * The absolute filepath to the resources folder containing the config files.
	 */
	@NotNull
	public static final String RESOURCES_PATH = "/home/lvuser/449_resources/";

	/**
	 * The drive
	 */
	private DriveTalonCluster driveSubsystem;

	/**
	 * The object constructed directly from the yaml map.
	 */
	private RobotBallbasaur robotMap;

	/**
	 * The Notifier running the logging thread.
	 */
	private Notifier loggerNotifier;

	/**
	 * The string version of the alliance we're on ("red" or "blue"). Used for string concatenation to pick which
	 * profile to execute.
	 */
	@Nullable
	private String allianceString;

	/**
	 * Whether or not the robot has been enabled yet.
	 */
	private boolean enabled;

	/**
	 * The method that runs when the robot is turned on. Initializes all subsystems from the map.
	 */
	public void robotInit() {
		//Set up start time
		Clock.setStartTime();
		Clock.updateTime();

		enabled = false;

		//Yes this should be a print statement, it's useful to know that robotInit started.
		System.out.println("Started robotInit.");

		Yaml yaml = new Yaml();
		try {
			//Read the yaml file with SnakeYaml so we can use anchors and merge syntax.
//			Map<?, ?> normalized = (Map<?, ?>) yaml.load(new FileReader(RESOURCES_PATH+"ballbasaur_map.yml"));
//			Map<?, ?> normalized = (Map<?, ?>) yaml.load(new FileReader(RESOURCES_PATH + "naveen_map.yml"));
//			Map<?, ?> normalized = (Map<?, ?>) yaml.load(new FileReader(RESOURCES_PATH + "nate_map.yml"));
//			Map<?, ?> normalized = (Map<?, ?>) yaml.load(new FileReader(RESOURCES_PATH + "calcifer_outreach_map.yml"));
			Map<?, ?> normalized = (Map<?, ?>) yaml.load(new FileReader(RESOURCES_PATH + "ballb.yml"));
			YAMLMapper mapper = new YAMLMapper();
			//Turn the Map read by SnakeYaml into a String so Jackson can read it.
			String fixed = mapper.writeValueAsString(normalized);
			//Use a parameter name module so we don't have to specify name for every field./
			mapper.registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
			//Deserialize the map into an object.
			robotMap = mapper.readValue(fixed, RobotBallbasaur.class);
		} catch (IOException e) {
			//This is either the map file not being in the file system OR it being improperly formatted.
			System.out.println("Config file is bad/nonexistent!");
			e.printStackTrace();
		}
		//Set fields from the map.
		this.loggerNotifier = new Notifier(robotMap.getLogger());
		this.driveSubsystem = robotMap.getDrive();
		//Run the logger to write all the events that happened during initialization to a file.
		robotMap.getLogger().run();
		Clock.updateTime();
	}

	/**
	 * Run when we first enable in teleop.
	 */
	@Override
	public void teleopInit() {
		if (!enabled) {
			if (robotMap.getStartupCommand() != null) {
				robotMap.getStartupCommand().start();
			}
			enabled = true;
		}

		doStartupTasks();

		if (robotMap.getTeleopStartupCommand() != null)
			robotMap.getTeleopStartupCommand().start();

		driveSubsystem.setDefaultCommandManual(robotMap.getDefaultDriveCommand());g
	}

	/**
	 * Run every tick in teleop.
	 */
	@Override
	public void teleopPeriodic() {
		//Refresh the current time.
		Clock.updateTime();
		//Run all commands. This is a WPILib thing you don't really have to worry about.
		Scheduler.getInstance().run();
	}

	/**
	 * Run when we first enable in autonomous
	 */
	@Override
	public void autonomousInit() {
	}

	/**
	 * Runs every tick in autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		//Update the current time
		Clock.updateTime();
		//Run all commands. This is a WPILib thing you don't really have to worry about.
		Scheduler.getInstance().run();
	}

	/**
	 * Run when we disable.
	 */
	@Override
	public void disabledInit() {
		//Fully stop the drive
		driveSubsystem.fullStop();
	}

	/**
	 * Run when we first enable in test mode.
	 */
	@Override
	public void testInit() {
	}

	/**
	 * Run every tic while disabled
	 */
	@Override
	public void disabledPeriodic() {
		Clock.updateTime();
	}

	/**
	 * Do tasks that should be done when we first enable, in both auto and teleop.
	 */
	private void doStartupTasks() {
		//Refresh the current time.
		Clock.updateTime();
		//Start running the logger
		loggerNotifier.startPeriodic(robotMap.getLogger().getLoopTimeSecs());
	}
}