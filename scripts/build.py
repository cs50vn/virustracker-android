import sys, os, shutil, subprocess, time, config

def buildWorkingDir():
    # Remove java gen folder
    if os.path.exists(config.javaGenDir):
        shutil.rmtree(config.javaGenDir)

    #Copy project template to build dir
    src = config.javaDir
    des = config.javaGenDir + os.sep + "_asproject"
    if os.path.exists(des):
        shutil.rmtree(des)
    
    print("\033[1;34;40mFrom\n\033[0;37;40m" + src)
    print("\033[1;34;40mTo\n\033[0;37;40m" + des)

    shutil.copytree(src, des)

    #Copy packed_data to _asproject
    src = config.dataGenDir + os.sep + "packed_data.zip"
    des = des + os.sep + "app" + os.sep + "src" + os.sep + "main" + os.sep + "assets"
    if os.path.exists(src):
        shutil.copy(src, des)

    
    print("\n")
    
def buildJava():
    print("===========================================================")
    print("                      \033[1;32;40mBUILD JAVA\033[0;37;40m")
    print("===========================================================")

    if config.hostType == "windows":
        gradleExec = config.gradleExecWin
    elif config.hostType == "linux":
        gradleExec = config.gradleExecLinux
        
    if config.buildType == "release":
        cmd = config.javaDir + os.sep + gradleExec + " assembleRelease -p " + config.javaGenDir + os.sep + "_asproject" + " --profile"
        subprocess.call(cmd, shell=True)
        #Enable in next version
        #cmd = config.javaDir + os.sep + gradleExec + " testReleaseUnitTest -p " + config.javaGenDir + os.sep + "_asproject"
        #subprocess.call(cmd, shell=True)
    else:
        cmd = config.javaDir + os.sep + gradleExec + " assembleDebug -p " + config.javaGenDir + os.sep + "_asproject" + " --profile"
        subprocess.call(cmd, shell=True)
        #Enable in next version
        #cmd = config.javaDir + os.sep + gradleExec + " testDebugUnitTest -p " + config.javaGenDir + os.sep + "_asproject"
        #subprocess.call(cmd, shell=True)
    #print(cmd)
    #subprocess.call(cmd, shell=True)
    
    print("\n")

def buildPackage():
    print("===========================================================")
    print("                      \033[1;32;40mBUILD PACKAGE\033[0;37;40m")
    print("===========================================================")
        
    if config.buildType == "release":
        src = config.javaGenDir + os.sep + "_asproject" + os.sep + "app" + os.sep + "build" + os.sep + "outputs" + os.sep + "apk" + os.sep + "release" + os.sep + "app-release.apk"
        des = config.rootGenDir + os.sep + "apks"
        
        print("\033[1;34;40mFrom:\n\033[0;37;40m" + src)
        print("\033[1;34;40mTo\n\033[0;37;40m" + des)
        
        if not os.path.exists(des):
            os.mkdir(des)
        des += os.sep + config.outputFile + "-" + config.versionCode + "-" + config.versionName + "-release.apk"
        shutil.copyfile(src, des)
    else:
        src = config.javaGenDir + os.sep + "_asproject" + os.sep + "app" + os.sep + "build" + os.sep + "outputs" + os.sep + "apk" + os.sep + "debug" + os.sep + "app-debug.apk"
        des = config.rootGenDir + os.sep + "apks"
        
        print("\033[1;34;40mFrom:\n\033[0;37;40m" + src)
        print("\033[1;34;40mTo\n\033[0;37;40m" + des)
        
        if not os.path.exists(des):
            os.mkdir(des)
        des += os.sep + config.outputFile + "-" + config.versionCode + "-" + config.versionName + "-debug.apk"
        shutil.copyfile(src, des)

    
    print("\n")

def main(argv):
    start = time.time()
    print("===========================================================")
    print("                      \033[1;32;40mBUILD APPLICATION\033[0;37;40m")
    print("===========================================================")
    
    print(str(argv))
    config.buildProjectPath(argv[0], argv[1], argv[2], argv[3])
    
    buildWorkingDir()
    
    buildJava()
    
    buildPackage()

    elapsedTime = time.time() - start
    print("Running time: %s s" % str(elapsedTime))

if __name__ == '__main__':
    main(sys.argv[1:])