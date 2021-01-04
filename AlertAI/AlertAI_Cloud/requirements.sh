gsutil cp gs://bucket_alertai/app.py .

gsutil cp gs://bucket_alertai/alertai.db .

gsutil cp gs://bucket_alertai/Dockerfile .

gsutil cp -r gs://bucket_alertai/saves models/

sudo add-apt-repository universe
sudo apt-get update


sudo apt update
sudo apt install apt-transport-https ca-certificates curl software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable"
sudo apt update
sudo apt install docker.io

gcloud compute firewall-rules create alertaicloud --allow tcp:5000


sudo docker build --force-rm -t alertai-cloud .

sudo docker run -d -p 5000:5000 --name AlertAICloud alertai-cloud