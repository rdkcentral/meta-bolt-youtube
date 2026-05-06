SUMMARY = "nplb evergreen compat test bolt image"

inherit base-bolt-image

IMAGE_INSTALL += "nplb-evergreen-compat-tests-launcher"

# Add required fonts in rootfs, /usr/share/content/data/fonts/
IMAGE_INSTALL += "libloader-app"
