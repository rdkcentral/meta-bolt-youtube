require libloader-app_25.lts.stable.bb

SUMMARY = "Evergreen Cobalt loader_app library with experimental extensions"

SRC_URI += "file://exp/0001-RDKEVL-7397-Add-support-for-checking-EGL-surfaceless.patch;patchdir=../larboard"
