#!/usr/bin/env bash
#@echo off

# Current dir
# Host type
# Arch type: all, armeabi-v7a, arm64-v8a, x86
# Build type: all, release, debug
# Skip native: 1: skip, 0: no skip
# Custom gen dir: 1: use, 0: non use

# make.bat all release 0 0
# make.bat all release 0 1:<custompath>
# make.bat armeabi-v7a,arm64-v8a,x86 release 0 1,<custompath>

export ARCH_TYPE=all
export BUILD_TYPE=$1

python3 scripts/build.py $PWD linux $ARCH_TYPE $BUILD_TYPE

# Predefined Environment Variables
# JAVA_HOME, ANDROID_NDK_HOME, ANDROID_HOME, FASTBUILD_HOME, PYTHON3