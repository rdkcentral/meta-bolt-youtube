SUMMARY = "nplb evergreen compat test bolt image"

inherit base-bolt-image

IMAGE_INSTALL += "nplb-evergreen-compat-tests-launcher"
IMAGE_INSTALL += "libloader-app"
