#define MyAppName "DogeClock"
#define MyAppVersion "1.0.0"
#define MyAppExeName "DogeClock.exe"
#define MyAppDir "composeApp\build\compose\binaries\main-release\app\DogeClock"

[Setup]
AppId = {{A1B2C3D4 - E5F6 - 7890 - ABCD - EF1234567890}
        AppName={
#MyAppName
        }
        AppVersion={
#MyAppVersion
        }
        PrivilegesRequired=lowest
        DefaultDirName={
            localappdata
        }\{
#MyAppName
        }
        DefaultGroupName={
#MyAppName
        }
        OutputDir=installer-output
        OutputBaseFilename=DogeClock-Setup
        SetupIconFile=composeApp\src\jvmMain\resources\icons\app_icon.ico
        Compression=lzma
        SolidCompression=yes
        WizardStyle=modern

        [Languages]
        Name: "english"; MessagesFile: "compiler:Default.isl"

        [Tasks]
        Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"
        Name: "startmenu"; Description: "Create Start Menu shortcut"
        Name: "startup"; Description: "Start on login"; Flags: unchecked

        [Files]
        Source: "{#MyAppDir}\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

        [Icons]
        Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: startmenu
        Name: "{autodesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon

        [Registry]
        Root: HKCU; Subkey: "Software\Microsoft\Windows\CurrentVersion\Run"; ValueType: string; ValueName: "{#MyAppName}"; ValueData: "{app}\{#MyAppExeName}"; Flags: uninsdeletevalue; Tasks: startup

        [Run]
        Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#MyAppName}}"; Flags: nowait postinstall skipifsilent
