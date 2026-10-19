REQUIRED_DISTRO_FEATURES:remove = "cobalt-27"

DEPENDS:remove = "virtual/vendor-secapi2-adapter virtual/vendor-gst-drm-plugins"

DEPENDS += "rialto-ocdm-link"

DEPENDS:remove = "entservices-opencdmi"
RDEPENDS:${PN}:remove = "entservices-opencdmi"
