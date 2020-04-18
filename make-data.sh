#!/usr/bin/env bash
#@echo off

export ARCH_TYPE=all
export BUILD_TYPE=all

python3 scripts/make-data.py $PWD linux $ARCH_TYPE $BUILD_TYPE
