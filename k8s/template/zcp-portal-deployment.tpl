apiVersion: apps/v1beta1
kind: Deployment
metadata:
  name: zcp-portal-ui
  namespace: ${namespace}
spec:
  replicas: ${replicas}
  template:
    metadata:
      labels:
        component: zcp-portal-ui
        app: zcp-portal-ui
      annotations: # https://www.weave.works/docs/cloud/latest/tasks/monitor/configuration-k8s/
        prometheus.io/path: /prometheus
        prometheus.io/port: '9000'
        prometheus.io/scrape: 'true'
    spec:
      tolerations:
      - key: "management"
        operator: "Equal"
        value: 'true'
        effect: "NoSchedule"
      affinity:
        nodeAffinity:
          requiredDuringSchedulingIgnoredDuringExecution:
            nodeSelectorTerms:
            - matchExpressions:
              - key: beta.kubernetes.io/arch
                operator: In
                values:
                - "amd64"
              - key: role
                operator: In
                values:
                - "management"
      containers:
      - name: zcp-portal-ui
        image: ${image}
        imagePullPolicy: Always
        ports:
        - name: cont-port
          containerPort: 8181
        - name: prometheus
          containerPort: 9000
        envFrom:
        - configMapRef:
            name: zcp-portal-ui-config
        env:
          - name: CLIENT_SECRET
            valueFrom:
              secretKeyRef:
                name: zcp-portal-ui-secret
                key: CLIENT_SECRET
#          - name: MARIADB_PASSWORD
#            valueFrom:
#              secretKeyRef:
#                name: zcp-portal-ui-secret
#                key: MARIADB_PASSWORD
        volumeMounts:
        - name: svc-meta-config-volume
          mountPath: /home/zcp/
      volumes:
        - name: svc-meta-config-volume
          configMap:
            name: zcp-portal-service-meta-config
---

apiVersion: v1
kind: Service
metadata:
  name: zcp-portal-ui
  labels:
    name: zcp-portal-ui
  namespace: ${namespace}
spec:
  type: ClusterIP
  ports:
  - port: 80
    targetPort: 8181
    protocol: TCP
    name: http
  selector:
    component: zcp-portal-ui
