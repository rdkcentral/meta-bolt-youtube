DEPENDS:remove = "breakpad-wrapper"
DEPENDS:remove = "rfc"
DEPENDS:remove = "thunder-hang-recovery"

PROVIDES += "${PN}-corelib"
PACKAGES =+ "${PN}-corelib"
FILES:${PN}-corelib = "${libdir}/libWPEFrameworkCore.so.*"
