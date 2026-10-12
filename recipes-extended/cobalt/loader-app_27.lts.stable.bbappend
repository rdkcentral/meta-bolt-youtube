REQUIRED_DISTRO_FEATURES:remove = "cobalt-27"

DEPENDS += "rialto-ocdm-link"

DEPENDS:remove = "entservices-opencdmi"
RDEPENDS:${PN}:remove = "entservices-opencdmi"
