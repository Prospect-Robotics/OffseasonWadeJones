# Off-season Wade Jones

Gear Heads FRC 2813 Robot code for the 2022 season (RAPID REACT).

This is rewrite of Robot2022 code to use Command-based programming.

## Building

1. Go to the [WPILib 2022.4.1 Release](https://github.com/wpilibsuite/allwpilib/releases/tag/v2022.4.1)
page, download the installer and run it.

> Note that for each year, WPILib installs libraries
and resources in a different directory, so installing an older version of WPILib should not affect
your ability to build code for the current year.

2. In a terminal, change your current working directory to be the "OffseasonWadeJones" subdirectory
   of the git repository (i.e. the directory that contains `build.gradle`)
3. Run `./gradlew build` (Linux & macOS) or `.\gradlew.bat deploy` (Windows)

## Deploying to the robot

1. In a terminal, change your current working directory to be the "OffseasonWadeJones" subdirectory
   of the git repository (i.e. the directory that contains "build.gradle")
2. Connect your machine to the robot's network (robot wifi, ethernet, or a USB-B cable).
3. Verify that your machine is connecting to the robot by running `ping 10.28.13.2`
4. Run `./gradlew deploy` (Linux & macOS) or `.\gradlew.bat deploy` (Windows)
