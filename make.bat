@echo off

rem Current dir
rem	Host type
rem Arch type: all, armeabi-v7a, arm64-v8a, x86
rem Build type: release, debug

rem make.bat all release
rem make.bat all release
rem make.bat armeabi-v7a,arm64-v8a,x86 release

set ARCH_TYPE=all
set BUILD_TYPE=%1%

python scripts/build.py %CD% windows %ARCH_TYPE% %BUILD_TYPE%