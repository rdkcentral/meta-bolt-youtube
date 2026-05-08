SUMMARY = "nplb evergreen compat test bolt image"

inherit base-bolt-image

IMAGE_INSTALL += "nplb-evergreen-compat-tests-launcher"

# Install ssl certificates to /usr/share/content/data/app/cobalt/content/
IMAGE_INSTALL += "virtual/cobalt-evergreen"

# Add required fonts in rootfs, /usr/share/content/data/fonts/
IMAGE_INSTALL += "libloader-app"
