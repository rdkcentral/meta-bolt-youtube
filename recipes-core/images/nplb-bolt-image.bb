SUMMARY = "NPLB bolt image"

inherit base-bolt-image

IMAGE_INSTALL += "libloader-app"
IMAGE_INSTALL += "cobalt-keymap"
IMAGE_INSTALL += "nplb"
IMAGE_INSTALL += "nplb-test-data"
IMAGE_INSTALL += "nplb-launcher"
