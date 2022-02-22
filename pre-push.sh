#!/bin/sh

PWD=$(pwd)
ROOT="file://$(git rev-parse --show-toplevel)"
TMP=$(mktemp -d)

cd "$TMP"
git clone "$ROOT" test --depth 1
cd test
mvn -B clean -Dtest=**IntegrationTest test -DfailIfNoTests=false && mvn -B package -DskipTests
SUCCESS=$?

sed -i 's/api/queries\/api/g' "application/core-rest-query/target/generated-docs/index.html"
sed -i 's/api/executor\/api/g' "application/core-rest-command/target/generated-docs/index.html"

echo "Publish documentation..."

gsutil -m mv application/core-rest-query/target/generated-docs/index.html gs://docs-api/queries/index.html
gsutil -m mv application/core-rest-command/target/generated-docs/index.html gs://docs-api/command/index.html

rm -rf "$TMP"

exit $SUCCESS