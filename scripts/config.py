import sys, os, shutil, subprocess, time


#Build tool
gradleExecWin       = "gradlew.bat"
gradleExecLinux     = "gradlew"
hostType            = ""
archType            = ""
buildType           = ""

#Project config
rootDir         = ""
javaDir         = ""
dataDir         = ""
scriptDir       = ""
rootGenDir      = ""
javaGenDir      = ""
nativeGenDir    = ""
dataGenDir      = ""

#App config
outputFile  = "virustracker"
versionCode = "1"
internalversionCode = "1.0.0"
versionName = "v" + internalversionCode

def parseArchType():
    #Coming soon
    print("#Coming soon")


def parseBuildType():
    #Coming soon
    print("#Coming soon")


def buildProjectPath(rootPath, host, arch, build):
    global rootDir
    rootDir = rootPath
    global hostType
    hostType = host
    global archType
    archType = arch
    global buildType
    buildType = build
    
    global javaDir
    javaDir = rootDir + os.sep + "java"
    global dataDir
    dataDir = rootDir + os.sep + "data"
    global scriptDir
    scriptDir = rootDir + os.sep + "scripts"
    global rootGenDir
    rootGenDir = rootDir + os.sep + "_generated"
    global javaGenDir
    javaGenDir = rootGenDir + os.sep + "java"
    global dataGenDir
    dataGenDir = rootGenDir + os.sep + "data"

    print("\033[1;34;40mLoad build config\033[0;37;40m")
    print("Host: \033[1;32;40m%s\033[0;37;40m" % hostType)
    print("Arch: \033[1;32;40m%s\033[0;37;40m" % archType)
    print("Build Type: \033[1;32;40m%s\033[0;37;40m\n" % buildType)
    
    print("\033[1;34;40mLoad project config\033[0;37;40m")
    print("Root dir:        \033[1;34;40m%s\033[0;37;40m" % rootDir)
    print("Java dir:        \033[1;34;40m%s\033[0;37;40m" % javaDir)
    print("Data dir:        \033[1;34;40m%s\033[0;37;40m" % dataDir)
    print("Script dir:      \033[1;34;40m%s\033[0;37;40m" % scriptDir)
    print("Root gen dir:    \033[1;34;40m%s\033[0;37;40m" % rootGenDir)
    print("\n")
    
    
def getOutputFile():
    if buildType == "release":
        return outputFile + "-" + versionCode + "-" + versionName + "-release.apk"
    else: 
        return outputFile + "-" + versionCode + "-" + versionName + "-debug.apk"