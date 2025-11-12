SUMMARY = "nplb bolt image"

inherit base-bolt-image

IMAGE_INSTALL += "libloader-app"
IMAGE_INSTALL += "nplb nplb-test-data"
IMAGE_INSTALL += "libloader-app-tools"
