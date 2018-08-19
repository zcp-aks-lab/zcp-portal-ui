# The zcp-portal-ui Installation Guide

zcp-portal-ui는 Cloud Z CP 의 Console front-end server 로, zcp-iam, zcp-alertmanager 등의 back-end API server 와 연동을 한다.

zcp-portal-ui 을 설치하기 이전에 k8s cluster 가 설치되어 있어야 하고, cluster role 권한으로 `kubectl` 을 수행 할 수 있는 환경을 갖추어야 한다.

zcp-iam, zcp-alertmanager, zcp-alertmanager-store 가 미리 설치되어 있어야 한다.

## Clone this project into the desktop
```
$ git clone https://github.com/cnpst/zcp-portal-ui.git
```

## Deploy the application
프로젝트 별로 수정해야 하는 파일은 **secret, configmap, ingress** 세 가지이다.

k8s configuration 파일 디렉토리로 이동한다.

```
$ cd zcp-iam/k8s/site
```

### :one: Secret을 수정한 후 생성 한다.

#### 프로젝트에 적용 할 add-on service meta data용 Secret을 생성한다.
v1.0 에서 제공하는 add-on service 의 모든 목록은 `k8s/zcp-portal-service-meta-config.yaml` 에 있으므로 참고하고, 프로젝트에 제공할 add-on service 를 확인한 후 `k8s/site/zcp-portal-service-meta-config.yaml` 파일을 수정한다.

:bangbang: 이때 각 add-on service의 링크정보에 도메인 정보도 반드시 수정해 주어야 한다.

```
$ kubectl create -f zcp-portal-service-meta-config.yaml
```

#### KeyCloak 의 client secret 값을 변경한 후 Secret을 생성한다.
```
apiVersion: v1
kind: Secret
metadata:
  name: zcp-portal-ui-secret
  namespace: zcp-system
type: Opaque
data:
  CLIENT_SECRET: NzFhZDFlNDItZDIzOS00YTdkLTk5ZjktZTFlNTBhZDdjYTkz
```

KeyCloak > zcp realm > clients > account > credentials 탭으로 이동하여 secret 정보를 복사 한 후 base64로 incoding 한다.

([base64 encoding web site](https://www.base64encode.org/) 참고. :bangbang: Mac 에서 base64 로 하는 경우 % 가 붙어서 값이 틀림 주의 요망)

`CLIENT_SECRET` 의 value 를 base64 incoding 된 값으로 변경한다.

(:white_check_mark: KeyCloak 설치 후 zcp realm의 account client의 secret을 변경하지 않은 경우 그대로 사용하면 됨)

```
$ kubectl create -f zcp-portal-secret.yaml
```

다음 명령어로 생성된 secret 을 확인한다.
```
$ kubectl get secret -n zcp-system
```

### :two: ConfigMap을 수정한 후 생성 한다.
#### 프로젝트의 KeyCloak, zcp-iam, zcp-alertmanager, k8s-dashboard 등의 URI 접속정보를 변경해야 한다.
도메인 정보만 업데이트 해주면 됨.

```
apiVersion: v1
kind: ConfigMap
metadata:
  name: zcp-portal-ui-config
  namespace: zcp-system
data:
  SPRING_ACTIVE_PROFILE: stage
  CLIENT_ID: account
  REDIRECT_URI: https://console.cloudzcp.io/login
  ACCESS_TOKEN_URI: https://iam.cloudzcp.io/auth/realms/zcp/protocol/openid-connect/token
  USER_AUTHORIZATION_URI: https://iam.cloudzcp.io/auth/realms/zcp/protocol/openid-connect/auth
  IAM_BASE_URI: http://zcp-iam:80
  ALERT_BASE_URI: http://zcp-alertmanager:80
  DASHBOARD_BASE_URI: https://dashboard.cloudzcp.io
```

#### ConfigMap 생성
```
$ kubectl create -f zcp-portal-config.yaml
```

### :three: Deployment와 Service를 생성 한다.
zcp-portal-ui 의 container image tag 정보를 확인 한 후, 생성 한다.
현재는 bluemix container registry `image: registry.au-syd.bluemix.net/cloudzcp/zcp-portal-ui:0.9.3` 를 사용한다.
```
$ cd ../
$ kubectl create -f zcp-portal-deployment-ibm.yaml
```

다음 명령어로 zcp-portal-ui가 정상적으로 배포되었는지 확인한다.
```
$ kubectl get pod -n zcp-system
```

### :four: Ingress를 수정한 후 생성한다.

#### ingress 설정 내, ALB-ID 및 도메인/인증서(TLS) 설정

Private Only로 클러스터가 구성된 경우 (Private ALB만 있는 경우), 아래 주석처리 되어있는 `ingress.bluemix.net/ALB-ID: xxxxxx` 이 부분을 반드시 enable 해주어야 한다.

TLS 적용을 위한 Secret 이 생성되어 있어야 한다. (KeyCloak 설치 시 이미 생성이 되어 있을 것이다)

  1. `rules.host` 에 도메인 설정을 변경한다.
  2. `tls.hosts` 이하에 도메인 설정을 변경한다.
  3. `tls.secretName` 을 변경한다.

```
$ cd site/

$ vi zcp-portal-ingress.yaml

apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  annotations:
    #ingress.bluemix.net/ALB-ID: private-cr7a9b181c82674f478e461c648c3000da-alb1
    ingress.bluemix.net/redirect-to-https: "True"
  name: zcp-portal-ui-ingress
  namespace: zcp-system
spec:
  tls:
  - hosts:
    - iam.hklaw.co.kr
    secretName: hklaw-co-kr-cert
  rules:
  - host: iam.hklaw.co.kr
    http:
      paths:
      - backend:
          serviceName: zcp-portal-ui
          servicePort: 80
```

#### Ingress 생성
```
$ kubectl create -f zcp-portal-ingress.yaml
```

다음 명령어로 Ingress가 정상적으로 배포되었는지 확인한다.
```
$ kubectl get ingress -n zcp-system
```

DNS 라우팅이 설정되어 있으면 브라우져에서 ingress 에 설정한 도메인으로 접속을 한다.
ex) https://console.cloudzcp.io

      - backend:ㅏ
      - backend:
