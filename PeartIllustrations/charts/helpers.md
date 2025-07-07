# Common commands to fix helm charts
- helm upgrade --install app ./charts/app  --values ./charts/app/values.yaml 
  - This is to help upgrade any charts that have changes
- helm install identity-service ./app --values ./app/values.yaml
-  This is to install any new chrats add to the kubernetes cluster