apiVersion: v1
kind: ConfigMap
metadata:
  name: zcp-portal-ui-config
  namespace: ${namespace}
data:
  SPRING_ACTIVE_PROFILE: stage
  CLIENT_ID: account
  REDIRECT_URI: https://${domain_console}/login
  ACCESS_TOKEN_URI: https://${domain_iam}/auth/realms/zcp/protocol/openid-connect/token
  USER_AUTHORIZATION_URI: https://${domain_iam}/auth/realms/zcp/protocol/openid-connect/auth
  IAM_BASE_URI: http://zcp-iam:80
  ALERT_BASE_URI: http://zcp-alertmanager:80
  DASHBOARD_BASE_URI: https://${domain_prefix}dashboard.cloudzcp.io
  ZDB_ENABLED: 'true'
  console.application.product: ${config_product:-''}   # eg. ZCP
  console.application.label: ${config_label:-''}       # eg. pou-dev