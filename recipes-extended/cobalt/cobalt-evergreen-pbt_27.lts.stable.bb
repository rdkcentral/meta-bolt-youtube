SUMMARY = "Evergreen Cobalt Core library."
HOMEPAGE = "https://cobalt.dev"

LICENSE = "BSD-3-Clause"
# See https://github.com/youtube/cobalt/blob/master/LICENSE for governing license.
# This license has been stored locally as COBALT_LICENSE
LIC_FILES_CHKSUM = "file://../COBALT_LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d"

inherit features_check
REQUIRED_DISTRO_FEATURES = "cobalt-27"

FILESEXTRAPATHS:prepend := "${THISDIR}/evergreen:"
DEPENDS += "unzip-native breakpad-native"
OVERRIDES:append = ":${TARGET_FPU}:${@bb.utils.filter('DISTRO_FEATURES', 'cobalt-qa', d)}"

PACKAGE_ARCH = "${MIDDLEWARE_ARCH}"

CRX_FILE:arm:hard = "cobalt_evergreen_7.10.2_arm-hardfp_sbversion-18_release_compressed.crx"
DBG_FILE:arm:hard = "libcobalt_7.10.2_unstripped_arm-hardfp_sbversion-18_release_51fb4bbbcb29d7bf49cc76cc4b742ee1c3e35cca.tar.gz"
CRX_FILE_SHA256SUM:arm:hard = "bcfc52e4f8b1f348e5a2af80ae9c610ce62f07e2e655a41f09998939fb18ccbe"
DBG_FILE_SHA256SUM:arm:hard = "285c2126f5bf5d8e4b358be0cf845b011f2b6b5c84296332fe776412158d4806"

CRX_FILE:arm:hard:cobalt-qa = "cobalt_evergreen_7.10.2_arm-hardfp_sbversion-18_qa_compressed.crx"
DBG_FILE:arm:hard:cobalt-qa = "libcobalt_7.10.2_unstripped_arm-hardfp_sbversion-18_qa_facc5c10d2a778c1a1efc60468c2db517adfe3cf.tar.gz"
CRX_FILE_SHA256SUM:arm:hard:cobalt-qa = "a53a3ab0bcc35cea46a12f9010928918a00d95507eb7ae75edfc6dd2323461d8"
DBG_FILE_SHA256SUM:arm:hard:cobalt-qa = "513a41879dd351f739f0b1efc7c4e54d8f05e15ed154ea79c04653c44b9613e9"

CRX_FILE:aarch64 = "cobalt_evergreen_7.10.2_arm64_sbversion-18_release_compressed.crx"
DBG_FILE:aarch64 = "libcobalt_7.10.2_unstripped_arm64_sbversion-18_release_fa3ff4c4d74f258f39ea433c9ee9d448745a0ec0.tar.gz"
CRX_FILE_SHA256SUM:aarch64 = "48b79df5dd80bb513b33eb87220d67fd19531af67731c9facd0b36edc2f9a59f"
DBG_FILE_SHA256SUM:aarch64 = "bd610baeb7235934e7a6657394f8c99e96ea39c2383e856fd6b3459acfe6d186"

CRX_FILE:aarch64:cobalt-qa = "cobalt_evergreen_7.10.2_arm64_sbversion-18_qa_compressed.crx"
DBG_FILE:aarch64:cobalt-qa = "libcobalt_7.10.2_unstripped_arm64_sbversion-18_qa_2d8cfacde4db824ba5b63afca3469fd7a96787d6.tar.gz"
CRX_FILE_SHA256SUM:aarch64:cobalt-qa = "1ab78442e0c216dd14f07b587bbe4791aed48944b8a9aa7fd381d21f4ebf66e6"
DBG_FILE_SHA256SUM:aarch64:cobalt-qa = "8cfdb8aa1df344f10a240b0132add9868f91131e2b55aef1603ffa9c9311f9f3"

PV = "7.10.2"
YT_BASE_URI = "https://github.com/youtube/cobalt/releases/download/27.lts.10"

SRC_URI  = "${YT_BASE_URI}/${CRX_FILE};name=cobalt"
SRC_URI += "${YT_BASE_URI}/${DBG_FILE};name=cobalt_debug;subdir=debug_syms"
SRC_URI += "file://COBALT_LICENSE"
SRC_URI[cobalt.sha256sum] = "${CRX_FILE_SHA256SUM}"
SRC_URI[cobalt_debug.sha256sum] = "${DBG_FILE_SHA256SUM}"

COBALT_APP_DIR = "/content/data/app/cobalt"

inherit breakpad-wrapper
breakpad_package_preprocess () {
    machine_dir="${@d.getVar('MACHINE', True)}"

    binary="$(readlink -m "${D}${datadir}${COBALT_APP_DIR}/lib/.debug/libcobalt.so")"
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
    install -d "${D}${datadir}${COBALT_APP_DIR}"

    err_code=0

    set +e
    unzip -q -o -d "${D}${datadir}${COBALT_APP_DIR}" "${WORKDIR}/${CRX_FILE}" || err_code=$?
    set -e

    case $err_code in
     0) bbnote "All good";;
     1) bbwarn "Ignore unzip warnings";;
     *) bbfatal "Unzip failed, exit code: $err_code"
    esac

    install -d "${D}${datadir}${COBALT_APP_DIR}/lib/.debug"
    install -m 0755 ${WORKDIR}/debug_syms/libcobalt.so ${D}${datadir}${COBALT_APP_DIR}/lib/.debug
}

FILES:${PN}  = "${datadir}${COBALT_APP_DIR}/content/*"
FILES:${PN} += "${datadir}${COBALT_APP_DIR}/manifest.json"
FILES:${PN} += "${datadir}${COBALT_APP_DIR}/lib/libcobalt.lz4"
FILES:${PN}-dbg += "${datadir}${COBALT_APP_DIR}/lib/.debug/libcobalt.so"
FILES_SOLIBSDEV = ""

INSANE_SKIP:${PN} += "dev-so "
INSANE_SKIP:${PN}-dbg += "dev-so libdir "

PROVIDES = "virtual/cobalt-evergreen"
RPROVIDES:${PN} = "virtual/cobalt-evergreen"
