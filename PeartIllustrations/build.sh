set -e

TAG=$(git rev-parse --short HEAD)

for dockerfile in $(find . -maxdepth 2 -name Dockerfile); do
  MODULE_DIR=$(dirname "$dockerfile")
  MODULE_NAME=$(basename "$MODULE_DIR" | tr '[:upper:]' '[:lower:]')
  IMAGE="hman1148/peartillustrations-$MODULE_NAME:$TAG"
  echo "Building $IMAGE"
  docker build -t $IMAGE "$MODULE_DIR"
  docker push $IMAGE
done

echo "All images built and pushed with tag: $TAG"

helm upgrade app ./charts/app --namespace dev