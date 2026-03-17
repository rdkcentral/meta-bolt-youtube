SUMMARY = "Cobalt bolt image with experimental extensions"

require cobalt-bolt-image.bb

IMAGE_INSTALL:remove = "libloader-app"
IMAGE_INSTALL += "libloader-app-exp"
