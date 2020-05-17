FROM ubuntu:18.04

ARG PACKER_VERSION=1.5.6

RUN apt-get update -q && apt-get install --no-install-recommends -y software-properties-common wget openssh-client unzip python3 python3-dev libssl-dev libffi-dev sshpass
RUN update-alternatives --install /usr/bin/python python /usr/bin/python3 1
RUN wget https://bootstrap.pypa.io/get-pip.py && python get-pip.py
RUN pip install ansible[azure]==2.8.0 pywinrm

# Install Packer
RUN wget https://releases.hashicorp.com/packer/${PACKER_VERSION}/packer_${PACKER_VERSION}_linux_amd64.zip \
    && unzip packer_${PACKER_VERSION}_linux_amd64.zip -d /usr/local/bin/ \
    && rm -rf packer_${PACKER_VERSION}_linux_amd64.zip