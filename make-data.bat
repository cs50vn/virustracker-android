
@echo off

set ARCH_TYPE=all
set BUILD_TYPE=all

python scripts/make-data.py %CD% linux %ARCH_TYPE% %BUILD_TYPE%
