#!/usr/bin/env bash
set -euo pipefail
CLUSTER="agora-cluster"
doctl k8s cluster delete ${CLUSTER}