package io.steveoh.mvwinstaller;

import com.formdev.flatlaf.FlatDarkLaf;
import com.moandjiezana.toml.Toml;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MVWInstaller {

    public MVWInstaller() {
        // Set the look and feel to FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.out.println("Unsupported look and feel");
        }

        if (!isAdmin()) {
            System.out.println("Not running as an administrator");
            JOptionPane.showMessageDialog(null, "Please run as an administrator", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String jarFilePath = new File(System.getProperty("user.dir")).getPath();
        String installDataPath = jarFilePath + File.separator + "installData";
        String configFilePath = installDataPath + File.separator + "config.toml";

        // Check if the config file exists, if not, return
        File configFile = new File(configFilePath);
        if (!configFile.exists()) {
            System.out.println("Config file does not exist");
            JOptionPane.showMessageDialog(null, "Config file does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String configText = "";

        // Config handling
        try {
            configText = new String(Files.readAllBytes(Paths.get(configFilePath)));
            Toml config = new Toml().read(configText);

            Settings.overwriteWithoutWarning = config.getBoolean("overwriteWithoutWarning");
            Settings.offerDesktopShortcut = config.getBoolean("offerDesktopShortcut");
            Settings.offerStartMenuShortcut = config.getBoolean("offerStartMenuShortcut");
            Settings.offerLaunchAfterInstall = config.getBoolean("offerLaunchAfterInstall");
            Settings.displayLicense = config.getBoolean("displayLicense");
            Settings.requireLicenseAcceptance = config.getBoolean("requireLicenseAcceptance");
            Settings.displayUrl = config.getBoolean("displayUrl");
            Settings.displayCopyright = config.getBoolean("displayCopyright");
            Settings.displayVersion = config.getBoolean("displayVersion");
            Settings.installMVWUpdater = config.getBoolean("installMVWUpdater");

            Settings.latestVersionDownloadURL = config.getString("latestVersionDownloadURL");
            Settings.changeLogDownloadURL = config.getString("changeLogDownloadURL");
            Settings.latestPackageDownloadURL = config.getString("latestPackageDownloadURL");

            Settings.url = config.getString("url");
            Settings.copyright = config.getString("copyRight");
            Settings.version = config.getString("version");
            Settings.programExecutable = config.getString("programExecutable");
            Settings.programName = config.getString("programName");

            List<Toml> userEnvironmentVariableTables = config.getTables("userEnvironmentVariables");
            for (Toml table : userEnvironmentVariableTables) {
                Settings.userEnvironmentVariables.add(new UserEnvironmentVariable(table.getString("var"), table.getString("value")));
            }

            List<Toml> systemEnvironmentVariableTables = config.getTables("systemEnvironmentVariables");
            for (Toml table : systemEnvironmentVariableTables) {
                Settings.systemEnvironmentVariables.add(new SystemEnvironmentVariable(table.getString("var"), table.getString("value")));
            }

            List<Toml> autoStartProgramTables = config.getTables("autoStartPrograms");
            for (Toml table : autoStartProgramTables) {
                Settings.autoStartPrograms.add(new AutoStartProgram(table.getString("name"), table.getString("path"), table.getString("arguments")));
            }

            List<Toml> moveFileStepTables = config.getTables("moveFileSteps");
            for (Toml table : moveFileStepTables) {
                Settings.moveFileSteps.add(new MoveFileStep(table.getString("source"), table.getString("destination")));
            }

            List<Toml> moveDirectoryStepTables = config.getTables("moveDirectorySteps");
            for (Toml table : moveDirectoryStepTables) {
                Settings.moveDirectorySteps.add(new MoveDirectoryStep(table.getString("source"), table.getString("destination")));
            }

            List<Toml> licenseTables = config.getTables("license");
            for (Toml table : licenseTables) {
                Settings.licenses.add(new License(table.getString("text"), table.getString("acceptanceText")));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Settings.installMVWUpdater) {
            // Check if the updater is found
            File updaterFolder = new File(installDataPath + File.separator + "updater");
            if (!updaterFolder.exists()) {
                System.out.println("Updater folder does not exist");
                JOptionPane.showMessageDialog(null, "Updater folder does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        new InstallerForm().setVisible(true);
    }

    public static void main(String[] args) {
        new MVWInstaller();
    }

    private static boolean isAdmin() {
        boolean isAdmin = false;
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            isAdmin = checkWindowsAdmin();
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            isAdmin = checkUnixAdmin();
        }

        return isAdmin;
    }

    private static boolean checkWindowsAdmin() {
        String groups[] = (new com.sun.security.auth.module.NTSystem()).getGroupIDs();
        for (String group : groups) {
            if (group.equals("S-1-5-32-544"))
                return true;
        }
        return false;
    }

    private static boolean checkUnixAdmin() {
        try {
            Process process = Runtime.getRuntime().exec("id -u");
            process.waitFor();

            return process.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
