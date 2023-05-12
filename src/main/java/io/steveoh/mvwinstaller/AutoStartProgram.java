package io.steveoh.mvwinstaller;

public class AutoStartProgram {
    public String programName;
    public String path;
    public String arguments;

    public AutoStartProgram(String programName, String path, String arguments) {
        this.programName = programName;
        this.path = path;
        this.arguments = arguments;
    }
}
