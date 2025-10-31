SUMMARY = "Recipe to install cobalt specific keymap"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESEXTRAPATHS:prepend := "${THISDIR}/files/:"
PACKAGE_ARCH = "${APP_LAYER_ARCH}"

SRC_URI += " file://keymapping.json"
S = "${WORKDIR}"

#cobalt-keymap, no need for configuration/compilation
do_compile[noexec] = "1"
do_configure[noexec] = "1"
do_patch[noexec] = "1"

do_install() {
        install -d ${D}/usr/share/content/data/etc
        install -m 0644 ${S}/keymapping.json ${D}/usr/share/content/data/etc/keymapping.json
# left for backward compatibility
        install -d ${D}/usr/share/content/data/app/cobalt/content/etc
        install -m 0644 ${S}/keymapping.json ${D}/usr/share/content/data/app/cobalt/content/etc/keymapping.json
}

FILES:${PN} += "/usr/share/content/data"
