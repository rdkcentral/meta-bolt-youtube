REQUIRED_DISTRO_FEATURES:remove = "cobalt-27"

DEPENDS += "rialto-ocdm-link"

DEPENDS:remove = "entservices-opencdmi"
RDEPENDS:${PN}:remove = "entservices-opencdmi"

do_install:append() {
    mv ${D}${datadir}${COBALT_APP_DIR}/content/test ${D}${datadir}/content/data/
    rm -rf ${D}${datadir}${COBALT_APP_DIR}/content/ssl
    rmdir ${D}${datadir}${COBALT_APP_DIR}/content
}

FILES:${PN}-test-data += "${datadir}/content/data/test"
