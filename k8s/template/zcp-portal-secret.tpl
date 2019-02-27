apiVersion: v1
kind: Secret
metadata:
  name: zcp-portal-ui-secret
  namespace: ${namespace}
  labels:
    component: zcp-portal-ui
type: Opaque
data:
  CLIENT_SECRET: NzFhZDFlNDItZDIzOS00YTdkLTk5ZjktZTFlNTBhZDdjYTkz