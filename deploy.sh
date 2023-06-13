#!/bin/bash


# Docker 제거
#sudo apt-get remove docker docker-engine docker.io containerd runc
sudo apt-get purge docker-ce docker-ce-cli containerd.io

# Docker Compose 제거
sudo rm /usr/local/bin/docker-compose

if ! type docker > /dev/null
then
  echo "================================ docker does not exist ==============================="
  echo "Start installing docker"
  sudo DEBIAN_FRONTEND=noninteractive apt-get -y update
  sudo apt-get install -y apt-transport-https ca-certificates curl gnupg-agent software-properties-common # gnupg-agent - 개인키를 캐싱해줌

#  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
#  sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

  # Docker의 공식 GPG 키 추가 - 공식 GPG 키를 사용하여 Docker 이미지 및 소프트웨어의 무결성을 검증
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
  # Docker 저장소 설정
  echo \
  "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

  sudo apt-get update
  sudo apt-get install -y docker-ce docker-ce-cli containerd.io
fi

if ! docker buildx version > /dev/null
then
  echo "=============================== Docker Buildx does not exist ====================================="
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

if ! type docker-compose > /dev/null
then
  echo "============================ docker-compose does not exist ======================================"
  echo "Start installing docker-compose"
  sudo curl -L "https://github.com/docker/compose/releases/download/v2.18.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
  sudo chmod +x /usr/local/bin/docker-compose
fi

echo "============================== start docker-compose up: ubuntu ================================="
sudo COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f /home/ubuntu/srv/ubuntu/docker-compose-prod.yml up --build
# COMPOSE_DOCKER_CLI_BUILD=1 이거 있어야 /usr/local/bin/docker-compose를 찾을 수 있음
# sudo COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f docker-compose-actions.yml up --build # workflow test
# .env 파일로 환경변수를 ec2에 전달하려면 실행하는 스크립트가 docker-compose와 같은 디렉토리에 있어야 한다.
