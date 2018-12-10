apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  annotations:
    ${private_alb_enable}ingress.bluemix.net/ALB-ID: ${private_alb}
    ingress.bluemix.net/redirect-to-https: 'True'
  name: zcp-portal-ui-ingress
  namespace: ${namespace}
spec:
  tls:
  - hosts:
    - ${domain_console}
    secretName: cloudzcp-io-cert
  rules:
  - host: ${domain_console}
    http:
      paths:
      - backend:
          serviceName: zcp-portal-ui
          servicePort: 80

