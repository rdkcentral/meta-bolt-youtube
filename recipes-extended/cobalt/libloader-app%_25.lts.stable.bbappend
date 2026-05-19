DEPENDS += "rialto-ocdm-link"

DEPENDS:remove = "virtual/vendor-secapi2-adapter"
DEPENDS:remove = "virtual/vendor-gst-drm-plugins"

# Add package config to switch larboard implementation to firebolt.
# An equivalent solution is planned for larboard release 1.0.6. This will be
# removed once the larboard release 1.0.6 is ready.
PACKAGECONFIG[firebolt]      = "rdk_enable_wpecryptography=false rdk_enable_rdkservices_api=false rdk_enable_firebolt_api=true,,firebolt-cpp-client firebolt-cpp-transport"
