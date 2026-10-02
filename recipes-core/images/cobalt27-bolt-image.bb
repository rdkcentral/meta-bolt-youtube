SUMMARY = "Cobalt27 bolt image"

inherit base-bolt-image

IMAGE_INSTALL += "loader-app"
IMAGE_INSTALL += "cobalt-keymap"
IMAGE_INSTALL += "rialto-gstreamer"
IMAGE_INSTALL += "gstreamer1.0-plugins-base-audioresample"
IMAGE_INSTALL += "gstreamer1.0-plugins-base-audioconvert"
IMAGE_INSTALL += "gstreamer1.0-plugins-base-typefindfunctions"
IMAGE_INSTALL += "gstreamer1.0-plugins-good-autodetect"
IMAGE_INSTALL += "virtual/cobalt-evergreen"

TOOLCHAIN_HOST_TASK:append = " nativesdk-gn nativesdk-git"
TOOLCHAIN_TARGET_TASK:remove = "virtual/cobalt-evergreen"
TOOLCHAIN_TARGET_TASK:append = " ocdm-headers nlohmann-json"
