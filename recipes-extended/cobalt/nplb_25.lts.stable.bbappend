SRC_URI += "file://nplb-test"

do_install:append() {
  install -d ${D}${bindir}
  install -m 0555 ${WORKDIR}/nplb-test ${D}${bindir}
}

FILES:${PN}  += "${bindir}"
