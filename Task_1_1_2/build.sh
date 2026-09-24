#!/bin/bash

set -e

OUT_DIR="out"
DOCS_DIR="docs"
ARTIFACT="app.jar"
MAIN_CLASS="ru.nsu.kruzhaev.Main"
PACKAGE_PATH="src/main/java/ru/nsu/kruzhaev"
OUT_PACKAGE="out/ru/nsu/kruzhaev"

echo "Removing old files..."
rm -rf "$OUT_DIR" "$DOCS_DIR" "$ARTIFACT"
mkdir "$OUT_DIR"

echo "---------"
echo "Compiling files..."
javac -d "$OUT_DIR" "$PACKAGE_PATH"/*.java

echo "---------"
echo "Creating JAR-archive..."
jar cvfe "$ARTIFACT" "$MAIN_CLASS" -C "$OUT_DIR" .

echo "---------"
echo "Making docs..."
javadoc -d "$DOCS_DIR" "$PACKAGE_PATH"/*.java

echo "---------"
echo "Starting..."
java -jar "$ARTIFACT"