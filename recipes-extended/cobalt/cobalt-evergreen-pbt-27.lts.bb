SUMMARY = "Evergreen Cobalt Core library."
HOMEPAGE = "https://cobalt.dev"

LICENSE = "BSD-3-Clause"
# See https://github.com/youtube/cobalt/blob/master/LICENSE for governing license.
# This license has been stored locally as COBALT_LICENSE
LIC_FILES_CHKSUM = "file://../COBALT_LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"

FILESEXTRAPATHS:prepend := "${THISDIR}/evergreen:"
DEPENDS += "unzip-native breakpad-native"
OVERRIDES:append = ":${TARGET_FPU}:${@bb.utils.filter('DISTRO_FEATURES', 'cobalt-qa', d)}"

PACKAGE_ARCH = "${MIDDLEWARE_ARCH}"

CRX_FILE:arm:hard:cobalt-qa = "cobalt_evergreen_7.2.2_arm-hardfp_sbversion-18_qa_compressed_20260729215815.crx"
CRX_FILE_SHA256SUM:arm:hard:cobalt-qa = "6a66c9e2e0ba9e999ff190d030667307e6cbf7bf771e0127be6563ed29e8fd9a"

CRX_FILE:arm:hard = "cobalt_evergreen_7.2.2_arm-hardfp_sbversion-18_release_compressed_20260729215815.crx"
CRX_FILE_SHA256SUM:arm:hard = "11e6b5972d466192c1fdeead6df30800e23634aac3865f609c02dad2dee25375"

CRX_FILE:aarch64:cobalt-qa = "cobalt_evergreen_7.2.2_arm64_sbversion-18_qa_compressed_20260729215815.crx"
CRX_FILE_SHA256SUM:aarch64:cobalt-qa = "d5775511ce58fef8dbe3a22ff5ff515dac62d1c1d53970f63b9a0b3f97b4266c"

CRX_FILE:aarch64 = "cobalt_evergreen_7.2.2_arm64_sbversion-18_release_compressed_20260729215815.crx"
CRX_FILE_SHA256SUM:aarch64 = "71052aafda90ea90deeab9d0f9646a9cf287ad5690ea47c9ef3093f9fe719f59"

DBG_FILE:arm:hard:cobalt-qa = "libcobalt_7.2.2_unstripped_arm-hardfp_sbversion-18_qa_1d725365ef6a5356ad6b38bd5c7854b92158889a.tar.gz"
DBG_FILE_SHA256SUM:arm:hard:cobalt-qa = "daca93f861a670fbc40154fb994a0196a156c8550694b188bd708bac61b5c25a"

DBG_FILE:arm:hard = "libcobalt_7.2.2_unstripped_arm-hardfp_sbversion-18_release_0d9ea40e10857dbbd8e92da4b8dcd5810f38690e.tar.gz"
DBG_FILE_SHA256SUM:arm:hard = "17c827c98613139ae4813b4f8d7d5ac6cf9d19c5238d0bdc6db7eb022a483407"

DBG_FILE:aarch64:cobalt-qa = "libcobalt_7.2.2_unstripped_arm64_sbversion-18_qa_fddcd287ae9542f2bfd09ea3465756eb54addd05.tar.gz"
DBG_FILE_SHA256SUM:aarch64:cobalt-qa = "84ebb8dd55ceb9f9f2d39d60622ac1c04cb1860f1183213b5529cb3affcfe2f9"

DBG_FILE:aarch64 = "libcobalt_7.2.2_unstripped_arm64_sbversion-18_release_40b1769a7a6f5715afec7fcdb9458faccee0fb07.tar.gz"
DBG_FILE_SHA256SUM:aarch64 = "2b0e64b79918efcc852b9b6bff6b35e1f69f5e40febf51f5c4d427c5a574e22a"


PV = "7.2.2"
YT_BASE_URI = "https://github.com/youtube/cobalt/releases/download/27.lts.2"

SRC_URI  = "${YT_BASE_URI}/${CRX_FILE};name=cobalt"
SRC_URI += "${YT_BASE_URI}/${DBG_FILE};name=cobalt_debug;subdir=debug_syms"
SRC_URI += "file://COBALT_LICENSE"
SRC_URI[cobalt.sha256sum] = "${CRX_FILE_SHA256SUM}"
SRC_URI[cobalt_debug.sha256sum] = "${DBG_FILE_SHA256SUM}"

COBALT_APP_DIR = "${bindir}/app/cobalt"
COBALT_APP_DIR:develop = "${datadir}/content/data/app/cobalt"

inherit breakpad-wrapper
breakpad_package_preprocess () {
    machine_dir="${@d.getVar('MACHINE', True)}"

    binary="$(readlink -m "${D}${COBALT_APP_DIR}/lib/.debug/libcobalt.so")"
    bbnote "Dumping symbols from $binary -> ${TMPDIR}/deploy/breakpad_symbols/$machine_dir/libcobalt.lz4.sym"

    mkdir -p ${TMPDIR}/deploy/breakpad_symbols/$machine_dir
    dump_syms -n libcobalt.lz4 "${binary}" > "${TMPDIR}/deploy/breakpad_symbols/$machine_dir/libcobalt.lz4.sym" || echo "dump_syms finished with errorlevel $?"
}

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_preunpack_cleanup() {
    bbnote "cleanup debug syms"
    rm -rf ${WORKDIR}/debug_syms
}
addtask preunpack_cleanup after do_fetch before do_unpack

do_install() {
    install -d "${D}${COBALT_APP_DIR}"

    err_code=0

    set +e
    unzip -q -o -d "${D}${COBALT_APP_DIR}" "${WORKDIR}/${CRX_FILE}" || err_code=$?
    set -e

    case $err_code in
     0) bbnote "All good";;
     1) bbwarn "Ignore unzip warnings";;
     *) bbfatal "Unzip failed, exit code: $err_code"
    esac

    install -d "${D}${COBALT_APP_DIR}/lib/.debug"
    install -m 0755 ${WORKDIR}/debug_syms/libcobalt.so ${D}${COBALT_APP_DIR}/lib/.debug
}

do_install:append:develop() {
    rm -f "${D}${COBALT_APP_DIR}/content/fonts/fonts.xml"
}

FILES:${PN}  = "${COBALT_APP_DIR}/content/*"
FILES:${PN} += "${COBALT_APP_DIR}/manifest.json"
FILES:${PN} += "${COBALT_APP_DIR}/lib/libcobalt.lz4"
FILES:${PN}-dbg += "${COBALT_APP_DIR}/lib/.debug/libcobalt.so"

INSANE_SKIP:${PN}-dbg += "libdir"
