SUMMARY = "Tools for working with Chromium development"
HOMEPAGE = "https://chromium.googlesource.com/chromium/tools/depot_tools"

LICENSE = "BSD-3-Clause & Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=dc17b2650f2be2e1a49dac28a7997209 \
                    file://third_party/repo/COPYING;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://chromium.googlesource.com/chromium/tools/depot_tools;branch=main;protocol=https"
SRCREV = "93919990d65a94fd62a5b1bae4e2909df6996e4a"
PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${datadir}
    cp -r ${S} ${D}${datadir}/depot_tools
    touch ${D}${datadir}/depot_tools/.disable_auto_update
}

python __anonymous() {
    if not bb.data.inherits_class('native', d):
        raise bb.parse.SkipRecipe("only -native variant is supported")
}

BBCLASSEXTEND = "native"
