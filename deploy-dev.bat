@echo off

echo "Deploy apk"
echo "======================="

set ARCH_TYPE=all
set BUILD_TYPE=%1%
set B2_APP_ID=%2%
set B2_APP_KEY=%3%

python scripts/deploy-apk.py %CD% windows %ARCH_TYPE% %BUILD_TYPE% %B2_APP_ID% %B2_APP_KEY%
