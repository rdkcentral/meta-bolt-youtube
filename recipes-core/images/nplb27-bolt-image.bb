require cobalt27-bolt-image.bb

SUMMARY = "NPLB bolt image"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

IMAGE_INSTALL += "nplb"
IMAGE_INSTALL += "nplb-test-data"
IMAGE_INSTALL += "nplb27-launcher"
IMAGE_INSTALL += "nplb-tools"

ROOTFS_POSTPROCESS_COMMAND:append = " replace_loader_app_with_nplb_launcher;"

replace_loader_app_with_nplb_launcher() {
    ln -sf nplb-launcher ${IMAGE_ROOTFS}${bindir}/loader_app
}
