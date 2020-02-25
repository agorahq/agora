#!/usr/bin/env bash
set -euo pipefail
CLUSTER="agora-cluster"
SERVICE="agora-delivery"
CONTEXT="do-lon1-${CLUSTER}"
REGION="lon1"
doctl k8s cluster create ${CLUSTER} --region ${REGION} --size s-1vcpu-2gb --count 1
kubectl create secret generic agora-host --from-literal=AGORA_HOST='127.0.0.1'
kubectl create secret generic agora-port --from-literal=AGORA_PORT='80'
kubectl create secret generic agora-oauth --from-literal=AGORA_OAUTH_CLIENT_ID="${AGORA_OAUTH_CLIENT_ID}" --from-literal=AGORA_OAUTH_CLIENT_SECRET="$AGORA_OAUTH_CLIENT_SECRET"
kubectl --context ${CONTEXT} apply -f agora.delivery/manifest.yaml
script/do-wait-for-service.sh ${CONTEXT} ${SERVICE}
open http://"$(kubectl --context ${CONTEXT} get service ${SERVICE} --template="{{range .status.loadBalancer.ingress}}{{.ip}}{{end}}")"