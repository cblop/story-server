#!/bin/bash
export LEIN_ROOT=true
nohup sudo -E /home/mthom/bin/lein ring server-headless 80 &
