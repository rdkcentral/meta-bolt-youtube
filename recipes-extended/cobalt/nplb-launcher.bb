SUMMARY = "Script which launches NPLB"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI += "file://nplb-launcher"

RDEPENDS:${PN} += "libloader-app-tools"

do_install() {
  install -d ${D}${bindir}
  install -m 0555 ${WORKDIR}/nplb-launcher ${D}${bindir}
}

FILES:${PN} = "${bindir}"
