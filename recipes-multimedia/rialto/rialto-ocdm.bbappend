do_install:append() {
  install -d ${D}${libdir}/pkgconfig/
  ln -sf ocdmRialto.pc ${D}${libdir}/pkgconfig/ocdm.pc
}
