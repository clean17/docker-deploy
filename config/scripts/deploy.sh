#!/bin/bash

# github의 workflow가 아래 스크립트를 실행

# Installing docker engine if not exists
if ! type docker > /dev/null
then
  echo "docker does not exist"
  echo "Start installing docker"
  sudo DEBIAN_FRONTEND=noninteractive apt-get -y update
  sudo apt install -y apt-transport-https ca-certificates curl software-properties-common gnupg2
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o docker.gpg
  gpg --no-default-keyring --keyring ./docker.gpg --import < docker.gpg
  sudo mv docker.gpg /etc/apt/trusted.gpg.d/
  sudo chmod 644 /etc/apt/trusted.gpg.d/docker.gpg
  sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable"
  sudo apt update
  apt-cache policy docker-ce
  sudo apt install -y docker-ce
fi

echo "docker version"
sudo docker --version

# Installing git if not exists
if ! type git > /dev/null
then
  echo "git does not exist"
  echo "Start installing git"
  sudo apt-get update
  sudo apt-get install -y git
fi

# Installing Docker Buildx if not exists
# 설치 후에는 Docker Buildx 바이너리만 남는다.
if ! type docker buildx > /dev/null
then
  echo "Docker Buildx does not exist"
  echo "Start Clone Buildx repository"
  # Clone buildx repository
  sudo rm -rf buildx
  sudo git clone https://github.com/docker/buildx.git
  # Build buildx
  cd buildx
  sudo apt install -y make # Makefile 프로젝트 빌드
  cd ..
  # Create directory for CLI plugins
  sudo mkdir -p ~/.docker/cli-plugins
  # Move buildx binary to CLI plugins directory
  sudo mv ./buildx/bin/build/buildx ~/.docker/cli-plugins/docker-buildx # move
  sudo chmod +x ~/.docker/cli-plugins/docker-buildx
  sudo docker buildx create --use
fi

# Installing docker-compose if not exists
if ! type docker-compose > /dev/null
then
  echo "docker-compose does not exist"
  echo "Start installing docker-compose"
  sudo curl -L "https://github.com/docker/compose/releases/download/1.27.3/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
  sudo chmod +x /usr/local/bin/docker-compose
fi

echo "start docker-compose up: ubuntu"
sudo COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f /home/ubuntu/srv/ubuntu/docker-compose-prod.yml up --build -d
