@echo off

if not defined ProgramFiles(x86) ( 
  set "RegKey=HKEY_LOCAL_MACHINE\SOFTWARE\JavaSoft\Java Runtime Environment" 
) else ( 
  set "RegKey=HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\JavaSoft\Java Runtime Environment" 
) 

for /f "tokens=2*" %%a in ('reg query "%RegKey%" /s ^| findstr /i /r /c:"^ * InstallDir"') do ( 
  set "JRE-InstallDir=%%bbin\java.exe" 
) 

echo Hier wird die java.exe vermutet
echo %JRE-InstallDir%
echo.

echo Die .jar wird versucht zu starten
@echo on
"%JRE-InstallDir%" -jar %~dp0ClearTrack.jar
@echo off
echo.
echo.
pause