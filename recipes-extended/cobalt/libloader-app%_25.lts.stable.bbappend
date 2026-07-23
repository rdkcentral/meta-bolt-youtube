FILESEXTRAPATHS:prepend := "${THISDIR}/files/:"

DEPENDS += "rialto-ocdm-link"

DEPENDS:remove = "virtual/vendor-secapi2-adapter"
DEPENDS:remove = "virtual/vendor-gst-drm-plugins"
DEPENDS:remove = "entservices-opencdmi"
RDEPENDS:${PN}:remove = "entservices-opencdmi"

SRC_URI += "file://25/0001-Workaround-for-YT-focus-issue-subscribe-to-OnStateCh.patch;patchdir=../larboard"
