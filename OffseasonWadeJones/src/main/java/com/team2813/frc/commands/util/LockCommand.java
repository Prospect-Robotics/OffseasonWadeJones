package com.team2813.frc.commands.util;

import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.concurrent.Callable;

/**
 * Command that runs a function repeatedly until it returns true
 */
public class LockCommand extends CommandBase {

    private Callable<Boolean> lockFunction;
    private boolean unlocked = false;

    public LockCommand(Callable<Boolean> lockFunction) {
        this.lockFunction = lockFunction;
    }

    @Override
    public void execute() {
        try {
            unlocked = lockFunction.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isFinished() {
        return unlocked;
    }
}
