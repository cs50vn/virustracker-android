import sys, os, shutil, subprocess, time, config

b2Id = ""
b2Key = ""
bucketName = "projecta-build"
filePath = "virustracker-android/v1/"

def buildPath(rootPath, host, arch, build, _b2Id, _b2Key):
    config.buildProjectPath(rootPath, host, arch, build)

    global b2Id
    b2Id = _b2Id
    global b2Key
    b2Key = _b2Key
    

def deployApk():
    print("===========================================================")
    print("                      \033[1;32;40mDEPLOY\033[0;37;40m")
    print("===========================================================")
    
    cmd = "b2 authorize-account %s %s" % (b2Id, b2Key)
    print(cmd)
    subprocess.call(cmd, shell=True)
    
    des = config.rootGenDir + os.sep + "apks" + os.sep + config.getOutputFile()
    cmd = "b2 upload-file %s %s %s" % (bucketName, des, filePath + config.getOutputFile())
    print(cmd)
    subprocess.call(cmd, shell=True)
    
    cmd = "b2 clear-account"
    print(cmd)
    subprocess.call(cmd, shell=True)

    print("\n")

def main(argv):
    start = time.time()
    print("===========================================================")
    print("                      \033[1;32;40mDEPLOY APK TO HOSTING\033[0;37;40m")
    print("===========================================================")
    
    print(str(argv))
    buildPath(argv[0], argv[1], argv[2], argv[3], argv[4], argv[5])

    deployApk()
    
    elapsedTime = time.time() - start
    print("Running time: %s s" % str(elapsedTime))

if __name__ == '__main__':
    main(sys.argv[1:])