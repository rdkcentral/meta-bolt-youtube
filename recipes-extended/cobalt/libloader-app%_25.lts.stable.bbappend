DEPENDS += "rialto-ocdm-link"

DEPENDS:remove = "virtual/vendor-secapi2-adapter"
DEPENDS:remove = "virtual/vendor-gst-drm-plugins"

SRC_URI += "file://0001-Set-default-THUNDER_ACCESS-value.patch;patchdir=../larboard"
