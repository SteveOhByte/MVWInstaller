# MVWInstaller

This software is designed to be an easy to use and highly customizable installer application for software.

To use it, get started by downloading the latest release from the [releases](https://github.com/SteveOhIo/MVWInstaller/releases) tab.

To configure the installer, simply open up the included **config.toml** file.

MVWInstaller comes with support for installing the [MVWUpdater](https://github.com/SteveOhIo/MVWUpdater). If you wish to enable it, you can find the configuration instructions on the updater's GitHub page.

When configuring your files for installation, keep everything inside the "data" folder. The example case provides a single move file step, of moving "#indat#/files/test.txt" to "#indir#/new/test1.txt".
This means that in the installData/data/files folder there must be a test.txt file. It will move it to the user selected installation directory/new/test1.txt location.

The provided move directory step says to move the source "#indat#/folder" to the destination "#indir#". This is telling the software to move all contents of installData/data/folder to the user selected installation directory.
