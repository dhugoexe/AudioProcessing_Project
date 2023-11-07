#!/bin/bash

PATH_TO_JAVAFX_LIBS="/Users/hugodevaux/Downloads/javafx-sdk-21.0.1/lib"


if [ -z "$PATH_TO_JAVAFX_LIBS" ]; then
    echo "Please set the PATH_TO_JAVAFX_LIBS variable to your JavaFX lib directory."
    exit 1
fi


java --module-path $PATH_TO_JAVAFX_LIBS --add-modules ALL-MODULE-PATH -cp . Main
