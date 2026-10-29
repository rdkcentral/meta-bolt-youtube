SUMMARY = "NPLB bolt image"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit base-bolt-image

IMAGE_INSTALL += "nplb"
IMAGE_INSTALL += "nplb-test-data"
IMAGE_INSTALL += "nplb27-launcher"
IMAGE_INSTALL += "nplb-tools"
IMAGE_INSTALL += "rialto-gstreamer"
IMAGE_INSTALL += "gstreamer1.0-plugins-base-audioresample"
IMAGE_INSTALL += "gstreamer1.0-plugins-base-audioconvert"
IMAGE_INSTALL += "gstreamer1.0-plugins-base-typefindfunctions"
IMAGE_INSTALL += "gstreamer1.0-plugins-good-autodetect"
