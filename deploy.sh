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
  sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
  sudo apt update
  apt-cache policy docker-ce
  sudo apt install -y docker-ce
fi

# Installing git if not exists
#if ! type git > /dev/null
#then
#  echo "git does not exist"
#  echo "Start installing git"
#  sudo apt-get update
#  sudo apt-get install -y git
#fi

# Installing Docker Buildx if not exists
# 설치 후에는 Docker Buildx 바이너리만 남는다.
#if ! type docker buildx > /dev/null
#then
#  echo "Docker Buildx does not exist"
#  echo "Start Clone Buildx repository"
#  # Clone buildx repository
#  sudo rm -rf buildx
#  sudo git clone https://github.com/docker/buildx.git
#  # Build buildx
#  cd buildx
#  sudo apt install -y make # Makefile 프로젝트 빌드
#  cd ..
#  # Create directory for CLI plugins
#  sudo mkdir -p ~/.docker/cli-plugins
#  # Move buildx binary to CLI plugins directory
#  sudo mv ./buildx/bin/build/buildx ~/.docker/cli-plugins/docker-buildx # move
#  sudo chmod +x ~/.docker/cli-plugins/docker-buildx
#  sudo docker buildx create --use
#fi

# Docker Buildx 설치 확인
if ! docker buildx version > /dev/null
then
  echo "Docker Buildx does not exist"
  echo "Start installing Docker Buildx"
  # Docker Buildx 바이너리 다운로드
  BUILDX_VERSION="v0.6.3"
  # wget - Docker Buildx 바이너리 파일을 다운로드
  wget https://github.com/docker/buildx/releases/download/$BUILDX_VERSION/buildx-$BUILDX_VERSION.linux-amd64 -O docker-buildx
  chmod +x docker-buildx
  # Docker CLI 플러그인 디렉토리 생성
  mkdir -p ~/.docker/cli-plugins
  mv docker-buildx ~/.docker/cli-plugins/docker-buildx
fi

# Docker Buildx - docker-compse 사용 가능
# Installing docker-compose if not exists
#if ! type docker-compose > /dev/null
#then
#  echo "docker-compose does not exist"
#  echo "Start installing docker-compose"
#  sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
#  sudo chmod +x /usr/local/bin/docker-compose
#fi

echo "start docker-compose up: ubuntu"
sudo COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f /home/ubuntu/srv/ubuntu/docker-compose-prod.yml up --build -d
# .env 파일로 환경변수를 ec2에 전달하려면 실행하는 스크립트가 docker-compose와 같은 디렉토리에 있어야 한다.
