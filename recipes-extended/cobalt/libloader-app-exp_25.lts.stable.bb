require libloader-app_25.lts.stable.bb

SUMMARY = "Evergreen Cobalt loader_app library with experimental extensions"

SRC_URI += "file://exp/0001-RDKEVL-7397-Add-support-for-checking-EGL-surfaceless.patch;patchdir=../larboard"
SRC_URI += "file://25/exp/0002-Add-Firebolt-lifecycle-support.patch;patchdir=../larboard"
SRC_URI += "file://25/exp/0003-RDKEAPPRT-762-Add-reading-of-cert-scope-and-secret-k.patch;patchdir=../larboard"
SRC_URI += "file://25/exp/0004-RDKEAPPRT-615-Add-experimental-DIAL-support.patch;patchdir=../larboard"

PACKAGECONFIG:remove = "securityagent"
DEPENDS:remove = "wpeframework-clientlibraries"
