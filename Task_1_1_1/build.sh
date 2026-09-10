#!/bin/bash

set -e

SRC_DIR="src"
OUT_DIR="out"
ARTIFACT="app.jar"
MAIN_CLASS="ru.nsu.kruzhaev.Main"

rm -rf "$OUT_DIR" "$ARTIFACT"
mkdir "$OUT_DIR"

javac -d "$OUT_DIR" "$(find "$SRC_DIR" -name "Main.java")"

jar --create --file "$ARTIFACT" --main-class "$MAIN_CLASS" -C "$OUT_DIR" .

java -jar "$ARTIFACT"