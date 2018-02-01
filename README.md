```bash
oc new-project hello-istio
oc adm policy add-scc-to-user privileged -z default -n hello-istio
./mvnw clean package fabric8:deploy
oc expose svc istio-ingress -n istio-system
oc get route/istio-ingress -n istio-system
http istio-ingress-istio-system.192.168.64.13.nip.io/hello
```