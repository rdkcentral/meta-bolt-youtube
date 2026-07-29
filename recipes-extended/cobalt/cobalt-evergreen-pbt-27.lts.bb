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

CRX_FILE:arm:hard:cobalt-qa = "cobalt_evergreen_7.1.2_arm-hardfp_sbversion-18_qa_compressed_20260627021609.crx"
CRX_FILE_SHA256SUM:arm:hard:cobalt-qa = "8992dc5055fd6b64142d69f872d0da49760a917c4828a8fca1f056841bd1f770"

CRX_FILE:arm:hard = "cobalt_evergreen_7.1.2_arm-hardfp_sbversion-18_release_compressed_20260627021609.crx"
CRX_FILE_SHA256SUM:arm:hard = "98a336319ce71f192932ddd9e62c927d3a3d2f6c5a49584cbf63b359d2dca1f1"

CRX_FILE:aarch64:cobalt-qa = "cobalt_evergreen_7.1.2_arm64_sbversion-18_qa_compressed_20260627021609.crx"
CRX_FILE_SHA256SUM:aarch64:cobalt-qa = "9bab0db81e65384b41453cb8c22777a0a0fd808c8b03b9c57b21c4199b430d67"

CRX_FILE:aarch64 = "cobalt_evergreen_7.1.2_arm64_sbversion-18_release_compressed_20260627021609.crx"
CRX_FILE_SHA256SUM:aarch64 = "5096e62cdad52da7143c0baf19fc0216ef4c1eedc3c7b08f50ef6af4e45f93bc"

DBG_FILE:arm:hard:cobalt-qa = "libcobalt_7.1.2_unstripped_arm-hardfp_sbversion-18_qa_6b88c1e38d3be99b095f53f3b6ea09b2c65ef09f.tar.gz"
DBG_FILE_SHA256SUM:arm:hard:cobalt-qa = "6998130a4c7f6f4375c0dbc665196330dd8a52633c580c22f43cb0a45ee9ed0f"

DBG_FILE:arm:hard = "libcobalt_7.1.2_unstripped_arm-hardfp_sbversion-18_release_1e4d660cc5efb6a816c615645da1e72c961351d7.tar.gz"
DBG_FILE_SHA256SUM:arm:hard = "faa482d335bb64a8d0e6a62206b6e5e3138aafa2cecd4e1d7314ee57eb033e4f"

DBG_FILE:aarch64:cobalt-qa = "libcobalt_7.1.2_unstripped_arm64_sbversion-18_qa_19a1eb2f65b92097edb497f7543c4274967edc23.tar.gz"
DBG_FILE_SHA256SUM:aarch64:cobalt-qa = "ba14e81aba4060691653587654ce5fdd6300905826f68723ce76fb973fb89c63"

DBG_FILE:aarch64 = "libcobalt_7.1.2_unstripped_arm64_sbversion-18_release_738ddb4e8034a7a2376abb38e2b0ff5df2ab89b4.tar.gz"
DBG_FILE_SHA256SUM:aarch64 = "c084c17cca7e9ba0d86e19064c0a87752a55f031aade9e3de50e7d0fbbf52a12"


PV = "7.1.2"
YT_BASE_URI = "https://github.com/youtube/cobalt/releases/download/27.lts.1"

SRC_URI  = "${YT_BASE_URI}/${CRX_FILE};name=cobalt"
SRC_URI += "${YT_BASE_URI}/${DBG_FILE};name=cobalt_debug;subdir=debug_syms"
SRC_URI += "file://COBALT_LICENSE"
SRC_URI[cobalt.sha256sum] = "${CRX_FILE_SHA256SUM}"
SRC_URI[cobalt_debug.sha256sum] = "${DBG_FILE_SHA256SUM}"

COBALT_APP_DIR = "${bindir}/app/cobalt"

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

FILES:${PN}  = "${COBALT_APP_DIR}/content/*"
FILES:${PN} += "${COBALT_APP_DIR}/manifest.json"
FILES:${PN} += "${COBALT_APP_DIR}/lib/libcobalt.lz4"
FILES:${PN}-dbg += "${COBALT_APP_DIR}/lib/.debug/libcobalt.so"

INSANE_SKIP:${PN}-dbg += "libdir"
