SUMMARY = "Cobalt bolt image"

inherit base-bolt-image

IMAGE_INSTALL += "libloader-app"
IMAGE_INSTALL += "virtual/cobalt-evergreen"
