docker stop BashSandbox

docker rm BashSandbox
                                
docker build -t nixx/bash-sandbox .

docker run -d --name BashSandbox nixx/bash-sandbox tail -f /dev/null
