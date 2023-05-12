package io.steveoh.mvwinstaller;

import com.erigir.mslinks.ShellLink;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class InstallerForm extends javax.swing.JFrame {

    private JButton chooseInstallationDirectoryButton;
    private JLabel copyrightLabel;
    private JButton installButton;
    private JTextField installationDirectoryTextField;
    private JLabel installationPathLabel;
    private JButton launchButton;
    private JCheckBox licenseAcceptanceCheckbox;
    private JScrollPane licenseScrollPane;
    private JTextArea licenseTextArea;
    private JButton nextButton;
    private JCheckBox offerDesktopShortcutCheckbox;
    private JCheckBox offerStartMenuShortcutCheckbox;
    private JProgressBar progressBar;
    private JLabel urlLabel;
    private JLabel versionLabel;

    private int pageIndex = 0;

    public InstallerForm() {
        initComponents();

        ImageIcon icon = null;
        try {
            icon = new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir") + File.separator + "installData" + File.separator + "icon.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setIconImage(icon.getImage());

        offerDesktopShortcutCheckbox.setVisible(false);
        offerStartMenuShortcutCheckbox.setVisible(false);
        launchButton.setVisible(false);
        progressBar.setVisible(false);
        setTitle(Settings.programName + " Installer");
        urlLabel.setText("<HTML><U>" + Settings.url + "</U></HTML>");
        versionLabel.setText("Version: " + Settings.version);
        copyrightLabel.setText(Settings.copyright);

        if (!Settings.displayCopyright)
            this.remove(copyrightLabel);
        if (!Settings.displayUrl)
            this.remove(urlLabel);
        if (!Settings.displayVersion)
            this.remove(versionLabel);
        if (!Settings.requireLicenseAcceptance)
            this.remove(licenseAcceptanceCheckbox);
        if (!Settings.displayLicense) {
            this.remove(licenseScrollPane);
            this.remove(licenseAcceptanceCheckbox);
            if (!Settings.offerDesktopShortcut && !Settings.offerStartMenuShortcut) {
                this.remove(nextButton);
            }
        } else {
            licenseTextArea.setText(Settings.licenses.get(0).text);
            licenseAcceptanceCheckbox.setText(Settings.licenses.get(0).acceptanceText);
        }

        setSize(getSize().width, 340);

        if (!Settings.displayCopyright && (!Settings.displayUrl && !Settings.displayVersion)) {
            setSize(getSize().width, 310);
        }
        if (Settings.displayCopyright && (!Settings.displayUrl && !Settings.displayVersion)){
            setSize(getSize().width, 310);
        }
        if (Settings.displayCopyright && !Settings.requireLicenseAcceptance && (!Settings.displayUrl && !Settings.displayVersion)) {
            setSize(getSize().width, 310);
        }
        if (Settings.displayCopyright && !Settings.requireLicenseAcceptance && (Settings.displayUrl || Settings.displayVersion)) {
            setSize(getSize().width, 310);
        }
        if (!Settings.displayCopyright && Settings.requireLicenseAcceptance && (Settings.displayUrl || Settings.displayVersion)) {
            setSize(getSize().width, 310);
        }

        if (!Settings.displayLicense && (Settings.displayUrl || Settings.displayVersion)) {
            setSize(getSize().width, 140);
        }
        if (!Settings.displayLicense && (!Settings.displayUrl && !Settings.displayVersion)) {
            setSize(getSize().width, 120);
        }
    }

    private void initComponents() {

        launchButton = new javax.swing.JButton();
        installButton = new javax.swing.JButton();
        installationDirectoryTextField = new javax.swing.JTextField();
        chooseInstallationDirectoryButton = new javax.swing.JButton();
        installationPathLabel = new javax.swing.JLabel();
        licenseScrollPane = new javax.swing.JScrollPane();
        licenseTextArea = new javax.swing.JTextArea();
        licenseAcceptanceCheckbox = new javax.swing.JCheckBox();
        copyrightLabel = new javax.swing.JLabel();
        urlLabel = new javax.swing.JLabel();
        nextButton = new javax.swing.JButton();
        versionLabel = new javax.swing.JLabel();
        offerDesktopShortcutCheckbox = new javax.swing.JCheckBox();
        offerStartMenuShortcutCheckbox = new javax.swing.JCheckBox();
        progressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Installer");
        setResizable(false);

        launchButton.setText("Launch");
        launchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                launchButtonActionPerformed(evt);
            }
        });

        installButton.setText("Install");
        installButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                installButtonActionPerformed(evt);
            }
        });

        installationDirectoryTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                installationDirectoryTextFieldActionPerformed(evt);
            }
        });

        chooseInstallationDirectoryButton.setText("Browse");
        chooseInstallationDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseInstallationDirectoryButtonActionPerformed(evt);
            }
        });

        installationPathLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        installationPathLabel.setText("Installation Path:");
        installationPathLabel.setToolTipText("");

        licenseTextArea.setColumns(20);
        licenseTextArea.setRows(5);
        licenseTextArea.setEditable(false);
        licenseScrollPane.setViewportView(licenseTextArea);

        licenseAcceptanceCheckbox.setText("I accept");

        copyrightLabel.setText("Copyright 2023 Steve Oh");

        urlLabel.setForeground(Color.WHITE);
        urlLabel.setText("<HTML><U>https://www.google.com</U></HTML>");
        urlLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        urlLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                urlLabelMouseClicked(evt);
            }
        });

        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        versionLabel.setText("Version: 0.1.1");

        offerDesktopShortcutCheckbox.setText("Create Desktop shortcut");

        offerStartMenuShortcutCheckbox.setText("Create Start Menu shortcut");
        offerStartMenuShortcutCheckbox.setSelected(true);

        progressBar.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(installationDirectoryTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chooseInstallationDirectoryButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(copyrightLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nextButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(installButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(offerDesktopShortcutCheckbox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(licenseScrollPane))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(versionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(urlLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(licenseAcceptanceCheckbox)
                            .addComponent(installationPathLabel)
                            .addComponent(offerStartMenuShortcutCheckbox)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(335, 335, 335)
                                .addComponent(launchButton)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(urlLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(versionLabel))
                .addGap(1, 1, 1)
                .addComponent(installationPathLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(installationDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chooseInstallationDirectoryButton))
                .addGap(4, 4, 4)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(licenseScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(offerDesktopShortcutCheckbox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(offerStartMenuShortcutCheckbox)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(licenseAcceptanceCheckbox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(copyrightLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(installButton)
                            .addComponent(nextButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(launchButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void installationDirectoryTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int maxLicenseIndex = Settings.licenses.size() - 1;

        if (Settings.requireLicenseAcceptance && Settings.displayLicense){
            // Check if the license has been accepted
            if (!licenseAcceptanceCheckbox.isSelected()) {
                // The license hasn't been accepted
                JOptionPane.showMessageDialog(this, "You must accept the license to continue.", "License not accepted", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        pageIndex++;

        // When we've shown all the licenses, we need to either go to the final selections page or disable the next button
        if (pageIndex > maxLicenseIndex || !Settings.displayLicense) {
            if (!Settings.offerDesktopShortcut && !Settings.offerStartMenuShortcut) {
                // We don't need to show the final selections page, so we can just install
                installButtonActionPerformed(null);
                return;
            }
            // We need to show the final selections page
            pageIndex = maxLicenseIndex;
            nextButton.setEnabled(false);
            installButton.setEnabled(true);
            if (Settings.offerDesktopShortcut)
                offerDesktopShortcutCheckbox.setVisible(true);
            if (Settings.offerStartMenuShortcut)
                offerStartMenuShortcutCheckbox.setVisible(true);
            licenseAcceptanceCheckbox.setVisible(false);
            licenseScrollPane.setVisible(false);
            if (Settings.displayLicense)
                setSize(getSize().width, getSize().height - 140);
            else
                setSize(getSize().width, getSize().height + 50);
        } else {
            // We haven't yet shown all the licenses, so we need to show the next license
            licenseTextArea.setText(Settings.licenses.get(pageIndex).text);
            licenseAcceptanceCheckbox.setSelected(false);
            licenseAcceptanceCheckbox.setText(Settings.licenses.get(pageIndex).acceptanceText);
        }
    }

    private void installButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (!Settings.overwriteWithoutWarning) {
            // Check if the installation directory exists
            File installationDirectory = new File(installationDirectoryTextField.getText());
            try {
                if (installationDirectory.exists() && !FileUtils.isEmptyDirectory(installationDirectory)) {
                    // The directory exists, so we need to warn the user
                    int result = JOptionPane.showConfirmDialog(this, "The installation directory already exists. Do you want to overwrite it?", "Overwrite?", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        // The user wants to overwrite the directory, delete it recursively
                        try {
                            FileUtils.deleteDirectory(installationDirectory);
                        } catch (IOException ex) {
                            // An error occurred while deleting the directory
                            JOptionPane.showMessageDialog(this, "An error occurred while deleting the installation directory.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        // The user doesn't want to overwrite the directory
                        return;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Disable the buttons
        installButton.setEnabled(false);
        nextButton.setEnabled(false);
        installationDirectoryTextField.setVisible(false);
        chooseInstallationDirectoryButton.setVisible(false);
        offerDesktopShortcutCheckbox.setVisible(false);
        offerStartMenuShortcutCheckbox.setVisible(false);
        if (Settings.displayLicense)
            setSize(getSize().width, getSize().height - 60);
        installationPathLabel.setText("Installing...");
        progressBar.setVisible(true);

        progressBar.setValue(0);

        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                // If we are installing an updater, we need the updater config file
                if (Settings.installMVWUpdater) {
                    installationPathLabel.setText("Installing Updater...");
                    String jarFilePath = new File(System.getProperty("user.dir")).getPath();
                    String installDataPath = jarFilePath + File.separator + "installData";
                    String configFilePath = installDataPath + File.separator + "updater" + File.separator + "config.toml";

                    // if the file already exists, delete it
                    File configFile = new File(configFilePath);
                    if (configFile.exists()) {
                        configFile.delete();
                    }

                    // Start the file String
                    try {
                        String sb = "currentVersion = \"" +
                                Settings.version +
                                "\"\n" +
                                "latestVersionDownloadURL = \"" +
                                Settings.latestVersionDownloadURL +
                                "\"\n" +
                                "changeLogDownloadURL = \"" +
                                Settings.changeLogDownloadURL +
                                "\"\n" +
                                "latestPackageDownloadURL = \"" +
                                Settings.latestPackageDownloadURL +
                                "\"\n" +
                                "programFilesPath = \"" +
                                installationDirectoryTextField.getText() +
                                "\"\n" +
                                "programName = \"" +
                                Settings.programName +
                                "\"\n";

                        // Write the file
                        FileUtils.writeStringToFile(new File(configFilePath), sb, "UTF-8");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    // Get the appdata path
                    String appDataPath = System.getenv("APPDATA");

                    // Create a MVWUpdater folder if it doesn't exist
                    File mvwUpdaterFolder = new File(appDataPath + File.separator + "MVWUpdater");
                    if (!mvwUpdaterFolder.exists()) {
                        mvwUpdaterFolder.mkdir();
                    }

                    // If there is already a subfolder with the program name, delete it
                    File programFolder = new File(mvwUpdaterFolder.getPath() + File.separator + Settings.programName);
                    if (programFolder.exists()) {
                        try {
                            FileUtils.deleteDirectory(programFolder);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    // Create a subfolder with the program name
                    programFolder.mkdir();

                    // Move everything from the installData/updater folder to the program folder
                    File installDataUpdaterFolder = new File(installDataPath + File.separator + "updater");
                    File[] updaterFiles = installDataUpdaterFolder.listFiles();
                    int updaterFilesLength = updaterFiles.length;
                    int progressBarIncrement = 100 / updaterFilesLength;
                    for (File file : updaterFiles) {
                        try {
                            FileUtils.copyFileToDirectory(file, programFolder, false);
                            int progress = (int) Math.round(progressBar.getValue() + progressBarIncrement);
                            publish(progress);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    progressBar.setValue(100);
                }

                // Shortcuts
                if (offerDesktopShortcutCheckbox.isSelected() && Settings.offerDesktopShortcut) {
                    String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
                    String shortcutPath = desktopPath + File.separator + Settings.programName + ".lnk";
                    ShellLink.createLink(installationDirectoryTextField.getText() + File.separator + Settings.programExecutable, shortcutPath);
                }

                if (offerStartMenuShortcutCheckbox.isSelected() && Settings.offerStartMenuShortcut) {
                    String startMenuPath = System.getenv("APPDATA") + File.separator + "Microsoft" + File.separator + "Windows" + File.separator + "Start Menu" + File.separator + "Programs";
                    String shortcutPath = startMenuPath + File.separator + Settings.programName + ".lnk";
                    ShellLink.createLink(installationDirectoryTextField.getText() + File.separator + Settings.programExecutable, shortcutPath);
                }

                // Begin the installation

                progressBar.setValue(0);
                installationPathLabel.setText("Setting environment variables...");
                int numUserEnvironmentVariables = Settings.userEnvironmentVariables.size();
                int numSystemEnvironmentVariables = Settings.systemEnvironmentVariables.size();
                int progressBarIncrement = 100 / (numUserEnvironmentVariables + numSystemEnvironmentVariables);

                // Set user environment variables
                for (UserEnvironmentVariable variable : Settings.userEnvironmentVariables) {
                    try {
                        setEnvironmentVariable(variable.name, variable.value, false);
                        int progress = (int) Math.round(progressBar.getValue() + progressBarIncrement);
                        publish(progress);
                    } catch (Exception ex) {
                        // An error occurred while setting the environment variable
                        JOptionPane.showMessageDialog(null, "An error occurred while setting the environment variable " + variable.name + ".", "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                        return null;
                    }
                }

                // Set system environment variables
                for (SystemEnvironmentVariable variable : Settings.systemEnvironmentVariables) {
                    try {
                        setEnvironmentVariable(variable.name, variable.value, true);
                        int progress = (int) Math.round(progressBar.getValue() + progressBarIncrement);
                        publish(progress);
                    } catch (Exception ex) {
                        // An error occurred while setting the environment variable
                        JOptionPane.showMessageDialog(null, "An error occurred while setting the environment variable " + variable.name + ".", "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                        return null;
                    }
                }

                // Set up the autostart programs
                for (AutoStartProgram program : Settings.autoStartPrograms) {
                    try {
                        String programName = program.programName;
                        String programPath = program.path;
                        programPath = programPath.replace("/", "\\");
                        programPath = "\\\"" + programPath + "\\\"";
                        String programArguments = program.arguments;

                        String registryKey = "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run";
                        String command = String.format("reg add \"%s\" /v \"%s\" /d \"%s\" /f", registryKey, programName, programPath);

                        if (!programArguments.isEmpty()) {
                            command += String.format(" /a \"%s\"", programArguments);
                        }

                        ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command);
                        processBuilder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                progressBar.setValue(0);
                installationPathLabel.setText("Installing...");
                double totalFiles = Settings.moveFileSteps.size() + Settings.moveDirectorySteps.size();
                double filesMoved = 0;

                String jarFilePath = new File(System.getProperty("user.dir")).getPath();
                String installDataPath = jarFilePath + File.separator + "installData";
                String data = installDataPath + File.separator + "data";

                for (MoveFileStep step : Settings.moveFileSteps) {
                    String source = step.source;
                    String destination = step.destination;

                    source = source.replace("/", "\\");
                    destination = destination.replace("/", "\\");

                    source = data + File.separator + source;
                    destination = installationDirectoryTextField.getText() + File.separator + destination;

                    File sourceFile = new File(source);
                    File destinationFile = new File(destination);

                    try {
                        FileUtils.copyFile(sourceFile, destinationFile);
                        filesMoved++;
                        int progress = (int) ((filesMoved / totalFiles) * 100);
                        publish(progress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                for (MoveDirectoryStep step : Settings.moveDirectorySteps) {
                    String source = step.source;
                    String destination = step.destination;

                    source = source.replace("/", "\\");
                    destination = destination.replace("/", "\\");

                    source = data + File.separator + source;
                    destination = installationDirectoryTextField.getText() + File.separator + destination;

                    File sourceFile = new File(source);
                    File destinationFile = new File(destination);

                    try {
                        FileUtils.copyDirectory(sourceFile, destinationFile);
                        filesMoved++;
                        int progress = (int) ((filesMoved / totalFiles) * 100);
                        publish(progress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // Done!
                progressBar.setValue(100);
                installationPathLabel.setText("Done!");
                if (Settings.offerLaunchAfterInstall) {
                    launchButton.setVisible(true);
                    installButton.setVisible(false);
                    nextButton.setVisible(false);
                }

                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                // Update the progress bar with the latest progress value
                progressBar.setValue(chunks.get(chunks.size() - 1));
            }

            @Override
            protected void done() {
            }
        };

        worker.execute();

    }

    public static void setEnvironmentVariable(String variableName, String variableValue, boolean isSystemVariable) throws IOException {
        ProcessBuilder processBuilder;
        if (isSystemVariable) {
            processBuilder = new ProcessBuilder("cmd", "/c", "setx", variableName, "\"" + variableValue + "\"", "/M");
        } else {
            processBuilder = new ProcessBuilder("cmd", "/c", "setx", variableName, "\"" + variableValue + "\"");
        }
        processBuilder.start();
    }

    private void chooseInstallationDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // We need to open a native folder chooser dialog

        // Create a new file chooser
        JFileChooser chooser = new JFileChooser();

        // Set the chooser to select folders
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Show the chooser
        int result = chooser.showOpenDialog(this);

        // If the user clicked the "Open" button
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected path
            File selectedPath = chooser.getSelectedFile();

            // Set the text field to the path
            installationDirectoryTextField.setText(selectedPath.getAbsolutePath());
        }
    }

    private void urlLabelMouseClicked(java.awt.event.MouseEvent evt) {
        // urlLabel.getText() will return <HTML><U>urlText</U></HTML>
        // so we need to remove the HTML and U tags
        String url = urlLabel.getText();
        url = url.substring(9, url.length() - 11);
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void launchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String exe = installationDirectoryTextField.getText() + File.separator + Settings.programExecutable;
        try {
            Runtime.getRuntime().exec(exe);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
