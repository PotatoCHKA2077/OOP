#!/bin/bash

set -e

SRC_DIR="src"
OUT_DIR="out"
DOCS_DIR="docs"
ARTIFACT="app.jar"
MAIN_CLASS="ru.nsu.kruzhaev.Main"

echo "Removing old files..."
rm -rf "$OUT_DIR" "$DOCS_DIR" "$ARTIFACT"
mkdir "$OUT_DIR"

echo "Compiling files..."
javac -d "$OUT_DIR" "$(find "$SRC_DIR" -name "Main.java")"

echo "Creating JAR-archive..."
jar --create --file "$ARTIFACT" --main-class "$MAIN_CLASS" -C "$OUT_DIR" .

echo "Making docs..."
mkdir "$DOCS_DIR"
javadoc -d "$DOCS_DIR" "$SRC_DIR/main/java/ru/nsu/kruzhaev/Main.java"

echo "Starting..."
java -jar "$ARTIFACT"