```bash
oc new-project hello-istio
oc adm policy add-scc-to-user privileged -z default -n hello-istio
./mvnw clean package fabric8:deploy
oc expose svc istio-ingress -n istio-system
http $(oc get route/istio-ingress -n istio-system  -o 'jsonpath={.spec.host}')/hello
```