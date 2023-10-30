#!/bin/bash

# Atualize a lista de pacotes
sudo apt-get update

# Instale os pacotes necessários para permitir o apt usar repositórios HTTPS
sudo apt-get install -y ca-certificates curl gnupg

# Crie o diretório para o keyring do apt
sudo install -m 0755 -d /etc/apt/keyrings

# Adicione a chave GPG oficial do Docker
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

# Adicione o repositório do Docker ao apt
echo \
  "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  "$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Atualize a lista de pacotes novamente
sudo apt-get update

# Instale o Docker Engine
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# Adicione seu usuário ao grupo docker para executar comandos docker sem sudo
sudo usermod -aG docker $USER
