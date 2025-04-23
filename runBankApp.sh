#!/bin/bash

# Define the path to the source code directory
PROJECT_DIR="./src"
# Define the package and class name
PACKAGE="bankapp"
MAIN_CLASS="Main"

# Navigate to the source code directory
cd $PROJECT_DIR

# Compile the Java files
javac $PACKAGE/*.java

# Run the Menu class
java $PACKAGE.$MAIN_CLASS
