#!/bin/bash
set -e

# Use IMAGE_TAG from environment or default to git commit
TAG=${IMAGE_TAG:-$(git rev-parse --short HEAD)}

echo "Building Docker images with tag: $TAG"

# Build all Docker images
for dockerfile in $(find . -maxdepth 2 -name Dockerfile); do
  MODULE_DIR=$(dirname "$dockerfile")
  MODULE_NAME=$(basename "$MODULE_DIR" | tr '[:upper:]' '[:lower:]')
  IMAGE="hman1148/peartillustrations-$MODULE_NAME:$TAG"
  
  echo "Building $IMAGE from $MODULE_DIR"
  docker build -t $IMAGE "$MODULE_DIR"
  
  # Only push if DOCKER_PUSH is set to true
  if [ "$DOCKER_PUSH" = "true" ]; then
    echo "Pushing $IMAGE"
    docker push $IMAGE
  fi
done

echo "Docker build completed with tag: $TAG"