
echo "Deploy apk"
echo "======================="

export ARCH_TYPE=all
export BUILD_TYPE=$1
export B2_APP_ID=$2
export B2_APP_KEY=$3

python3 scripts/deploy-apk.py $PWD linux $ARCH_TYPE $BUILD_TYPE $B2_APP_ID $B2_APP_KEY
