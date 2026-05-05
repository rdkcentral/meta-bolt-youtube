SUMMARY = "Script which launches NPLB evergreen compat tests"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI += "file://nplb-evergreen-compat-tests-launcher"

RDEPENDS:${PN} += "libloader-app-tools"

do_install() {
  install -d ${D}${bindir}
  install -m 0555 ${WORKDIR}/nplb-evergreen-compat-tests-launcher ${D}${bindir}
}

FILES:${PN} = "${bindir}/nplb-evergreen-compat-tests-launcher"
