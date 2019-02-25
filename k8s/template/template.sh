#!/bin/bash

# Usage: bash template.sh [FILE]
# 
# *.tpl
# - Create .yaml files through variable substitutions (bash style).
#   eg. $var or ${var}
#
# *.tpl2
# - Create .yaml files through the '##var##' style.
# - If you must keep double-qoute("keep") of content, use this. (like JSON)
#
# A FILE is a script that set or generate variables used by template files.
# A FILE is set as "setenv.sh", if not present.

_var=$(set | awk -F'=' '{print $1"="}')
. ${1:-setenv.sh}

out_dir=${out_dir:-.}
mkdir -p $out_dir 2>/dev/null

# https://stackoverflow.com/a/42386902
# https://wiki.kldp.org/HOWTO/html/Adv-Bash-Scr-HOWTO/parameter-substitution.html
ls *.tpl | while read tpl; do
  out=${out_dir}/${tpl%.tpl}.yaml
  eval "echo \"$(cat $tpl)\"" > $out
done

# https://ops.tips/gists/shell-replacing-variable-by-file-content/
# https://www.linuxquestions.org/questions/programming-9/bash-how-to-list-all-variables-305017/#td_post_1550513
ls *.tpl2 | while read tpl; do
  out=${out_dir}/${tpl%.tpl2}.yaml
  cp $tpl $out

  for var in $(set | grep -v "$_var" | cut -d'=' -f1); do
    sed -i '' "s|##${var}##|${!var}|g" "$out"
  done
done
