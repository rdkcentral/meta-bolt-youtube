SUMMARY = "Cobalt Evergreen Loader"
HOMEPAGE = "https://cobalt.dev"

LICENSE = "BSD-3-Clause & Apache-2.0-with-LLVM-exception"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=c408a301e3407c3803499ce9290515d6 \
    file://../../larboard/LICENSE;md5=a1045f140d2e71b4e089875cd5d07e42 \
"

require larboard_revision.inc

PATCHTOOL = "git"
TOOLCHAIN = "gcc"
PACKAGE_ARCH = "${MIDDLEWARE_ARCH}"

COBALT_BRANCH = "27.lts"
LARBOARD_BRANCH ?= "develop"
CR = "3"
PR = "r${CR}"
SRCREV_cobalt   ?= "27.lts.${CR}"
SRCREV_larboard ?= "23ababa8730988801d6a505f0e35b3a33b341f94"
SRCREV_cobalt:develop = "${AUTOREV}"
SRCREV_larboard:develop = "${AUTOREV}"

SRC_URI  = "git://github.com/youtube/cobalt.git;protocol=https;name=cobalt;branch=${COBALT_BRANCH};destsuffix=chromium/src"
SRC_URI += "${LARBOARD_SRC_URI};protocol=${CMF_GITHUB_PROTOCOL};destsuffix=larboard;name=larboard;branch=${LARBOARD_BRANCH}"
SRC_URI += "${COBALT_PATCHES}"

COBALT_PATCHES = "file://27/0001-Include-RDK-platforms.patch"
COBALT_PATCHES:in-tree = " \
    file://27/0001-Fix-assignment-with-no-effect-for-rdk_build_with_yoc.patch \
    file://27/0002-Fix-sysroot-for-rdk_build_with_yocto-builds.patch \
    file://27/0003-Fix-invalid-conversion.patch \
    file://27/0004-Let-args-configure-the-loader-app-target-type.patch \
    file://27/0005-Fix-namespaces-in-firebolt_interface.cc.patch \
    file://27/0006-Fix-libcobalt.cc-build-without-rdkservices-API.patch \
"

SRCREV_FORMAT = "cobalt_larboard"
PV .= "+git${SRCPV}"

do_fetch[vardeps] += "SRCREV_FORMAT SRCREV_cobalt SRCREV_larboard"
S = "${WORKDIR}/chromium/src"
B = "${S}/out"

DEPENDS += "virtual/libgles2 virtual/egl essos gstreamer1.0 gstreamer1.0-plugins-base"
DEPENDS += "wpeframework entservices-apis wpeframework-clientlibraries"
DEPENDS += "depot-tools-native"
DEPENDS += "xz-native"
DEPENDS += "curl-native"
DEPENDS += "ca-certificates-native"

RDEPENDS:${PN} += "gstreamer1.0-plugins-base-app gstreamer1.0-plugins-base-playback"

TUNE_CCARGS:remove = "-fno-omit-frame-pointer -fno-optimize-sibling-calls"

def get_cobalt_platform(d):
    target_arch = d.getVar('TARGET_ARCH', True)
    if target_arch == 'arm':
        return 'evergreen-arm-hardfp-rdk'
    else:
        bb.fatal("Unsupported target architecture: {}".format(target_arch))

COBALT_PLATFORM ?= "${@get_cobalt_platform(d)}"
COBALT_BUILD_TYPE ?= "${@bb.utils.contains('DISTRO_FEATURES', 'cobalt-qa', 'qa', 'gold', d)}"
COBALT_OUT_DIR = "${B}/${COBALT_PLATFORM}_${COBALT_BUILD_TYPE}"

PACKAGECONFIG ?= ""
PACKAGECONFIG:append = " ${COBALT_BUILD_TYPE}"
PACKAGECONFIG:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'opencdm', 'opencdm', '', d)}"
PACKAGECONFIG:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'thunder_security_disable', '', 'securityagent', d)}"
PACKAGECONFIG:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'enable_asan', 'asan', '', d)}"
PACKAGECONFIG:append = " wpecryptography rdkservices"

PACKAGECONFIG[opencdm]       = "rdk_enable_ocdm=true,rdk_enable_ocdm=false,rialto-ocdm-link"
PACKAGECONFIG[securityagent] = "rdk_enable_securityagent=true,rdk_enable_securityagent=false,,"
PACKAGECONFIG[qa]            = ",,nodejs-native,"
PACKAGECONFIG[asan]          = "use_asan=true,,gcc-sanitizers"
PACKAGECONFIG[gold]          = ""
PACKAGECONFIG[firebolt]      = "rdk_enable_firebolt_api=true,,firebolt-cpp-client firebolt-cpp-transport"
PACKAGECONFIG[fb_rpc_v1]     = "rdk_enable_firebolt_legacy_rpc_v1=true,,"
PACKAGECONFIG[firebolt_lifecycle] = "rdk_enable_firebolt_lifecycle=true,,"
PACKAGECONFIG[wpecryptography]     = ",rdk_enable_wpecryptography=false,"
PACKAGECONFIG[rdkservices]   = ",rdk_enable_rdkservices_api=false,"

GN_ARGS_EXTRA ?= ""
GN_ARGS_EXTRA:append = " starboard_level_final_executable_type=\"executable\""
GN_ARGS_EXTRA:append = " use_sysroot=false"
GN_ARGS_EXTRA:append = " sb_enable_cpp20_audit=false"
GN_ARGS_EXTRA:append:arm = " rdk_arm_call_convention=\"${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', 'hardfp', 'softfp', d)}\""
GN_ARGS_EXTRA:append = " ${PACKAGECONFIG_CONFARGS}"
GN_ARGS_EXTRA:append:in-tree = " rdk_build_with_yocto=true"
GN_ARGS_EXTRA:append:in-tree = " pkg_config=\"pkg-config\""

inherit python3native pkgconfig breakpad-wrapper ccache

BREAKPAD_BIN = "loader_app crashpad_handler"

CFLAGS:append:in-tree = " -I${S}/starboard/contrib/rdk/src"
CXXFLAGS:append:in-tree = " -I${S}/starboard/contrib/rdk/src"

PATH:prepend = "${STAGING_DATADIR_NATIVE}/depot_tools:"

export RDK_HOME = "${RECIPE_SYSROOT}"
export CURL_CA_BUNDLE = "${STAGING_ETCDIR_NATIVE}/ssl/certs/ca-certificates.crt"
export SSL_CERT_FILE = "${STAGING_ETCDIR_NATIVE}/ssl/certs/ca-certificates.crt"
export DEPOT_TOOLS_METRICS = "0"

python() {
    """
    Cobalt uses its own wrapper for ccache. Disable bitbake setup.
    """
    d.delVar("CCACHE")
}

GIT_CACHE_PATH ?= ""
CIPD_CACHE_DIR ?= ""
VPYTHON_VIRTUALENV_ROOT ?= "${WORKDIR}/.vpython-root"
GCLIENT_JOBS ?= "4"

do_gclient_sync[network] = "1"
do_gclient_sync[vardepsexclude] = "GIT_CACHE_PATH CIPD_CACHE_DIR VPYTHON_VIRTUALENV_ROOT"

do_gclient_sync() {
    [ -n "${GIT_CACHE_PATH}" ] && export GIT_CACHE_PATH="${GIT_CACHE_PATH}"
    [ -n "${CIPD_CACHE_DIR}" ] && export CIPD_CACHE_DIR="${CIPD_CACHE_DIR}"
    [ -n "${VPYTHON_VIRTUALENV_ROOT}" ] && export VPYTHON_VIRTUALENV_ROOT="${VPYTHON_VIRTUALENV_ROOT}"

    bash -c '. ${STAGING_DATADIR_NATIVE}/depot_tools/bootstrap_python3 && bootstrap_python3'

    cd ${S}/..
    gclient config --name=src https://github.com/youtube/cobalt.git --custom-var=checkout_configuration=small
    cd src
    gclient sync -j ${GCLIENT_JOBS} --no-history --reset -r $(git merge-base HEAD origin/${COBALT_BRANCH})
    build/linux/sysroot_scripts/install-sysroot.py --arch=${TARGET_ARCH}
}
addtask gclient_sync after do_prepare_recipe_sysroot do_unpack before do_patch

do_patch_extra() {
    bbnote "replacing in-tree starboard/contrib/rdk with larboard"

    bbnote "copy larboard"
    ( cd "${S}/third_party/" && ln -sfr ${WORKDIR}/larboard/src/third_party/starboard . )
}

do_patch_extra:in-tree() {
    bbnote "using in-tree starboard/contrib/rdk"
}
addtask patch_extra after do_gclient_sync before do_configure

do_configure[cleandirs] = "${B}"

do_configure() {
    cd ${S}
    ${PYTHON} ${S}/cobalt/build/gn.py -p ${COBALT_PLATFORM} -c ${COBALT_BUILD_TYPE} --no-rbe --no-check ${COBALT_OUT_DIR}
    echo "${GN_ARGS_EXTRA}" | tr ' ' '\n' >> ${COBALT_OUT_DIR}/args.gn
    gn gen ${COBALT_OUT_DIR} --check
}

do_compile() {
    autoninja -C ${COBALT_OUT_DIR} loader_app
}

FONTS_DIR = "${datadir}/content/data/app/cobalt/content"

do_install() {
    install -d ${D}${bindir}/native_target
    install -m 0755 ${COBALT_OUT_DIR}/native_target/crashpad_handler ${D}${bindir}/native_target
    install -m 0755 ${COBALT_OUT_DIR}/loader_app ${D}${bindir}

    chrpath -d ${D}${bindir}/loader_app ${D}${bindir}/native_target/crashpad_handler

    install -d "${D}${FONTS_DIR}"
    cp -av --no-preserve=ownership ${COBALT_OUT_DIR}/fonts "${D}${FONTS_DIR}"
}

FILES:${PN} += "${FONTS_DIR}"
