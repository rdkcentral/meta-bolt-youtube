SUMMARY = "NPLB bolt image"

LICENSE = "CLOSED"
#require cobalt-common.inc

inherit base-bolt-image

IMAGE_INSTALL += "nplb"
IMAGE_INSTALL += "nplb-test-data"
IMAGE_INSTALL += "nplb27-launcher"
IMAGE_INSTALL += "nplb-tools"
#IMAGE_INSTALL += "cobalt-keymap"
IMAGE_INSTALL += "rialto-gstreamer"
IMAGE_INSTALL += "gstreamer1.0-plugins-base-audioresample"
IMAGE_INSTALL += "gstreamer1.0-plugins-base-audioconvert"
IMAGE_INSTALL += "gstreamer1.0-plugins-base-typefindfunctions"
IMAGE_INSTALL += "gstreamer1.0-plugins-good-autodetect"
