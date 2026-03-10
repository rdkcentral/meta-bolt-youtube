require libloader-app_25.lts.stable.bb

SUMMARY = "Evergreen Cobalt loader_app library with experimental extensions"

SRC_URI += "file://exp/0001-RDKEVL-7397-Add-support-for-checking-EGL-surfaceless.patch;patchdir=../larboard"
SRC_URI += "file://25/exp/0002-Prepare-kSbSystemPropertyPlatformName-from-Firebolt-.patch;patchdir=../larboard"
SRC_URI += "file://25/exp/0003-Return-integrator-name-and-model-year-from-environme.patch;patchdir=../larboard"
SRC_URI += "file://25/exp/0004-Set-TZ-from-Firebolt-Localization.timeZone.patch;patchdir=../larboard"
SRC_URI += "file://25/exp/0005-Set-LANG-from-Firebolt-Localization.presentationLang.patch;patchdir=../larboard"
SRC_URI += "file://25/exp/0006-Implement-FireboltDevice-firmware_version-with-Devic.patch;patchdir=../larboard"
SRC_URI += "file://25/exp/0007-Implement-FireboltDevice-video_resolution-and-diagon.patch;patchdir=../larboard"
SRC_URI += "file://25/exp/0008-Implement-FireboltDevice-is_disconnected-with-Networ.patch;patchdir=../larboard"
SRC_URI += "file://25/exp/0009-Add-Firebolt-lifecycle-support.patch;patchdir=../larboard"
SRC_URI += "file://25/exp/0010-RDKEAPPRT-762-Add-reading-of-cert-scope-and-secret-k.patch;patchdir=../larboard"
SRC_URI += "file://25/exp/0011-RDKEAPPRT-615-Add-experimental-DIAL-support.patch;patchdir=../larboard"
SRC_URI += "file://25/exp/0012-RDKEAPPRT-762-Hard-code-model-properties-for-certifi.patch;patchdir=../larboard"

SRC_URI:remove = "file://25/0001-RDKEAPPRT-925-Implement-more-FireboltDevice-function.patch;patchdir=../larboard"

PACKAGECONFIG:remove = "securityagent"
DEPENDS:remove = "wpeframework-clientlibraries"
