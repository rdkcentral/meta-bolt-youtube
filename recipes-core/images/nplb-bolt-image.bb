SUMMARY = "nplb bolt image"

inherit base-bolt-image

IMAGE_INSTALL += "libloader-app"
IMAGE_INSTALL += "virtual/cobalt-evergreen"
IMAGE_INSTALL += "cobalt-keymap"
IMAGE_INSTALL += "nplb"
IMAGE_INSTALL += "libloader-app-tools"
