package com.team2813.frc.commands.util;

import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

import java.util.function.BooleanSupplier;

public class LockFunctionCommand extends WaitUntilCommand {

    private final Runnable function;

    public LockFunctionCommand(BooleanSupplier condition, Runnable function) {
        super(condition);

        this.function = function;
    }

    @Override
    public void initialize() {
        function.run();
    }
}
