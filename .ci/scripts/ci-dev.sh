
echo "Build a dev environment"
echo "======================="

export ROOT_DIR=$1
echo "RootDir ${ROOT_DIR}"

#Install prerequisites
echo "Install prerequisites"
echo "======================="

apt-get update
apt-get install -y keychain
pip install --upgrade b2

#Make data
echo -e "\n*****  1  *****"
$ROOT_DIR/make-data.sh

#Compile app
echo -e "\n*****  2  *****"
$ROOT_DIR/make.sh debug

#Deploy apk to hosting
echo -e "\n*****  3  *****"
#$ROOT_DIR/deploy-dev.sh debug $B2_APP_ID $B2_APP_KEY

#Email a download link
#echo -e "\n*****  4  *****"
#$ROOT_DIR/sendmail-dev.sh debug