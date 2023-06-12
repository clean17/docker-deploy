#!/bin/bash

#if ! type docker > /dev/null
#then
#  echo "docker does not exist"
#  echo "Start installing docker"
#  sudo DEBIAN_FRONTEND=noninteractive apt-get -y update
#  sudo apt install -y apt-transport-https ca-certificates curl software-properties-common gnupg2
#  curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o docker.gpg
#  gpg --no-default-keyring --keyring ./docker.gpg --import < docker.gpg
#  sudo mv docker.gpg /etc/apt/trusted.gpg.d/
#  sudo chmod 644 /etc/apt/trusted.gpg.d/docker.gpg
#  sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
#  sudo apt update
#  apt-cache policy docker-ce
#  sudo apt install -y docker-ce docker-ce-cli containerd.io
#fi

if ! type docker > /dev/null
then
  echo "================================ docker does not exist ==============================="
  echo "Start installing docker"
  sudo DEBIAN_FRONTEND=noninteractive apt-get -y update
  sudo apt-get install -y apt-transport-https ca-certificates curl gnupg-agent software-properties-common
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
  sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
  sudo apt-get update
  sudo apt-get install -y docker-ce docker-ce-cli containerd.io
fi

if ! type docker-compose > /dev/null
then
  echo "============================ docker-compose does not exist ======================================"
  echo "Start installing docker-compose"
  sudo curl -L "https://github.com/docker/compose/releases/download/2.17.3/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
  sudo chmod +x /usr/local/bin/docker-compose
fi

echo "============================== start docker-compose up: ubuntu ================================="
sudo DOCKER_BUILDKIT=1 docker-compose -f /home/ubuntu/srv/ubuntu/docker-compose-prod.yml up --build -d
#sudo DOCKER_BUILDKIT=1 docker-compose -f docker-compose-actions.yml up --build # workflow test
# .env 파일로 환경변수를 ec2에 전달하려면 실행하는 스크립트가 docker-compose와 같은 디렉토리에 있어야 한다.
