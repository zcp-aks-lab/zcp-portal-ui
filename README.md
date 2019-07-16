# Installation Guide

zcp-portal-ui는 Cloud Z CP 의 Console front-end server 로, zcp-iam, zcp-alertmanager 등의 back-end API server 와 연동을 한다.

zcp-portal-ui 을 설치하기 이전에 k8s cluster 가 설치되어 있어야 하고, cluster-admin role 권한으로 `kubectl` 을 수행 할 수 있는 환경을 갖추어야 한다.

zcp-iam, zcp-alertmanager, zcp-alertmanager-store 가 미리 설치되어 있어야 한다.

## Clone Project

Clone this project into the desktop

```
$ git clone https://github.com/cnpst/zcp-portal-ui.git
```

## Generate YAML (Kubernetes Resources)

설치 환경에 맞게 `setenv.sh` 파일을 수정한 후, `template.sh` 파일을 실행한다.

각 정보를 확인하는 자세한 방법은 Appendix 를 참고한다.

```
$ cd zcp-portal-ui/k8s/template

$ vi setenv.sh
out_dir=.tmp

domain_prefix=pog-dev-   #CHANGE
domain_iam=${domain_prefix}iam.cloudzcp.io
domain_console=${domain_prefix}console.cloudzcp.io

namespace=zcp-system
image=registry.au-syd.bluemix.net/cloudzcp/zcp-iam:1.1.0
replicas=1

config_product=ZCP    #eg. 'ZCP :)'
config_label=pou-dev  #eg. 'pou-dev'  #CHANGE

private_alb=$(kubectl get deploy -n kube-system | grep private | cut -f1 -d' ' | head -n 1)
private_alb_enable='#'  # $([ -z "$private_alb" ] && echo '#' || echo '' )  #CHANGE
...
```

`template.sh` 파일은 템플릿 파일(`.tpl`/`.tpl2`)을 변환하여 YAML 파일을 생성한다.

`.tmp` 는 `setenv.sh` 파일의 `out_dir` 값과 동일하다.

아래 내용을 참고하여 template.sh를 실행하여 YAML파일을 생성하고, 올바르게 생성되었는지 확인한다.

```
$ bash template.sh

$ ls -l .tmp
-rw-r--r--  1 hoon  staff    458  3 15 12:06 setenv.sh
-rw-r--r--  1 hoon  staff    313  3 15 12:06 varlables.log
-rw-r--r--  1 hoon  staff    751  3 15 12:39 zcp-portal-config.yaml
-rw-r--r--  1 hoon  staff   2126  3 15 12:06 zcp-portal-deployment.yaml
-rw-r--r--  1 hoon  staff    606  3 15 12:06 zcp-portal-ingress.yaml
-rw-r--r--  1 hoon  staff    212  3 15 12:06 zcp-portal-secret.yaml
-rw-r--r--  1 hoon  staff  10401  3 15 12:06 zcp-portal-service-meta-config.yaml
```

## Create Kubernetes Resource

템플릿을 통해 생성된 YAML 파일을 아래의 명령으로 실행한다.

```
$ kubectl create -f .tmp
configmap/zcp-portal-ui-config created
deployment.apps/zcp-portal-ui created
service/zcp-portal-ui created
ingress.extensions/zcp-portal-ui-ingress created
secret/zcp-portal-ui-secret created
configmap/zcp-portal-service-meta-config created

## check to create
$ kubectl get deploy,po,cm,secret,svc,ing -n zcp-system -l component=zcp-portal-ui
```

DNS 라우팅이 설정되어 있으면 브라우져에서 ingress 에 설정한 도메인으로 접속을 한다.

ex) https://console.cloudzcp.io

## Appendix

### ~~KeyCloak 의 client secret 값 확인방법~~
> setenv.sh 에서 자동으로 설정되도록 변경됨

KeyCloak > zcp realm > clients > account > credentials 탭으로 이동하여 secret 정보를 복사 한 후 base64로 encoding 한다.

```
$ echo -n "secret of account client" | base64
```

(KeyCloak 설치 후 zcp realm의 account client의 secret을 변경하지 않은 경우 그대로 사용하면 됨)

### ingress 설정 내, ALB-ID 및 도메인/인증서(TLS) 설정

Private Only로 클러스터가 구성된 경우 (Private ALB만 있는 경우), `setenv.sh`를 `private_alb_enable=''` 와 같이 설정한다.
