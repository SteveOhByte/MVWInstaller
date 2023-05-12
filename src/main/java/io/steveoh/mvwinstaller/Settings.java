package io.steveoh.mvwinstaller;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    public static boolean overwriteWithoutWarning;
    public static boolean offerDesktopShortcut;
    public static boolean offerStartMenuShortcut;
    public static boolean offerLaunchAfterInstall;
    public static boolean displayLicense;
    public static boolean requireLicenseAcceptance;
    public static boolean displayUrl;
    public static boolean displayCopyright;
    public static boolean displayVersion;
    public static boolean installMVWUpdater;

    public static String latestVersionDownloadURL;
    public static String changeLogDownloadURL;
    public static String latestPackageDownloadURL;

    public static String url;
    public static String copyright;
    public static String version;
    public static String programExecutable;
    public static String programName;
    public static List<UserEnvironmentVariable> userEnvironmentVariables = new ArrayList<>();
    public static List<SystemEnvironmentVariable> systemEnvironmentVariables = new ArrayList<>();
    public static List<AutoStartProgram> autoStartPrograms = new ArrayList<>();
    public static List<MoveFileStep> moveFileSteps = new ArrayList<>();
    public static List<MoveDirectoryStep> moveDirectorySteps = new ArrayList<>();
    public static List<License> licenses = new ArrayList<>();
}
