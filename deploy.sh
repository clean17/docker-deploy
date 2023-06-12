#!/bin/bash


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

#if ! docker buildx version > /dev/null
#then
#  echo "=============================== Docker Buildx does not exist ====================================="
#  echo "Start installing Docker Buildx"
#  # Docker Buildx 바이너리 다운로드
#  BUILDX_VERSION="v0.6.3"
#  # wget - Docker Buildx 바이너리 파일을 다운로드
#  wget https://github.com/docker/buildx/releases/download/$BUILDX_VERSION/buildx-$BUILDX_VERSION.linux-amd64 -O docker-buildx
#  chmod +x docker-buildx
#  # Docker CLI 플러그인 디렉토리 생성
#  mkdir -p ~/.docker/cli-plugins
#  mv docker-buildx ~/.docker/cli-plugins/docker-buildx
#fi

if ! type docker-compose > /dev/null
then
  echo "============================ docker-compose does not exist ======================================"
  echo "Start installing docker-compose"
  sudo curl -L "https://github.com/docker/compose/releases/download/v2.18.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
  sudo chmod +x /usr/local/bin/docker-compose
fi

echo "============================== start docker-compose up: ubuntu ================================="
sudo COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f /home/ubuntu/srv/ubuntu/docker-compose-prod.yml up --build -d
# .env 파일로 환경변수를 ec2에 전달하려면 실행하는 스크립트가 docker-compose와 같은 디렉토리에 있어야 한다.
