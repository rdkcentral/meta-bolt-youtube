RDEPENDS:${PN}:remove:rpi = "userland"
RDEPENDS:${PN}:remove = "thunderhangrecovery"

DEPENDS:remove = "breakpad-wrapper"
DEPENDS:remove = "rfc thunderhangrecovery"

DEPENDS += "curl"
PACKAGECONFIG:remove = " sdnotify"

SRC_URI:remove = "file://r4.4/0001-RDK-28534-Security-Agent-Utility-and-Logging.patch"
EXTRA_VERSIONS_PATH ??= "${TMPDIR}/versions"
