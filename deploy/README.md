# RideCare Deploy_Backend_Frontend

## Pre-Requisites

- Pip

	Installation(On ubuntu):

		sudo apt update

		sudo apt install python3-pip

- Google-auth

	Installation:

		pip install requests google-auth

- Ansible

	Installation:

		sudo apt update

		sudo apt install software-properties-common

		sudo apt-add-repository --yes --update ppa:ansible/ansible

		sudo apt install ansible

## Run Application

- Download credentials.json
		
		IAM & Admin > Service Accounts > Select or Create SA > Create Key (JSON)

- On the deploy directory:
		
		export ANSIBLE_HOST_KEY_CHECKING=False

		ansible-playbook playbook.yml

## Swagger UI

[http://<VM_IP>:8080/swagger-ui.html#/](http://<VM_IP>:8080/swagger-ui.html#/)
