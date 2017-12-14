package org.usfirst.frc.team449.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.jetbrains.annotations.NotNull;
import org.usfirst.frc.team449.robot.jacksonWrappers.YamlSubsystem;
import org.usfirst.frc.team449.robot.subsystem.interfaces.intake.SubsystemIntake;
import org.usfirst.frc.team449.robot.subsystem.interfaces.solenoid.SubsystemSolenoid;

public class PneumaticsAndIntake extends YamlSubsystem implements SubsystemSolenoid,SubsystemIntake{

	@Override
	protected void initDefaultCommand() {

	}

	@NotNull
	@Override
	public IntakeMode getMode() {
		return null;
	}

	@Override
	public void setMode(@NotNull IntakeMode mode) {

	}

	@Override
	public void setSolenoid(@NotNull DoubleSolenoid.Value value) {

	}

	@NotNull
	@Override
	public DoubleSolenoid.Value getSolenoidPosition() {
		return null;
	}
}
