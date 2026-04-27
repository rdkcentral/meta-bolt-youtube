require libloader-app_25.lts.stable.bb

SUMMARY = "Evergreen Cobalt loader_app library with experimental extensions"

SRC_URI += "file://exp/0001-RDKEVL-7397-Add-support-for-checking-EGL-surfaceless.patch;patchdir=../larboard"
SRC_URI += "file://exp/0001-RDKEAPPRT-762-Add-reading-of-cert-scope-and-secret-k.patch;patchdir=../larboard"
SRC_URI += "file://exp/0001-RDKEAPPRT-615-Add-experimental-DIAL-support.patch;patchdir=../larboard"

LARBOARD_SRCREV_DEV = "${AUTOREV}"
SRC_URI := "${@d.getVar('SRC_URI').replace('name=larboard;branch=develop', 'name=larboard;branch=feature/lifecycle-2024Q2')}"

PACKAGECONFIG:remove = "securityagent"
DEPENDS:remove = "wpeframework-clientlibraries"
