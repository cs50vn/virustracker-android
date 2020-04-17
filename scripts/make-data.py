import sys, os, shutil, subprocess, time, config
from distutils.dir_util import copy_tree

def buildData():
    print("")
    #Build data
    genDirname = "packed_data"
    genDir = config.dataGenDir + os.sep + genDirname
    if os.path.exists(genDir):
        shutil.rmtree(genDir)    
    os.makedirs(genDir)

    # Copy app db
    src = config.dataDir + os.sep + "raw" + os.sep + "app_template.db"
    des = genDir
    shutil.copyfile(src, des + os.sep + "app.db")

    #Compress data
    cmd = "7z a %s.zip %s" % (des, des)
    subprocess.call(cmd, shell=True)

def main(argv):
    start = time.time()
    print("===========================================================")
    print("                      \033[1;32;40mBUILD DATA\033[0;37;40m")
    print("===========================================================")
    
    print(str(argv))
    config.buildProjectPath(argv[0], argv[1], argv[2], argv[3])
    
    buildData()

    elapsedTime = time.time() - start
    print("Running time: %s s" % str(elapsedTime))

if __name__ == '__main__':
    main(sys.argv[1:])