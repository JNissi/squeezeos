include libxcb.inc
PR = "r1"

DEFAULT_PREFERENCE = "-1"

DEPENDS += "libpthread-stubs xcb-proto-native"

PACKAGES =+ "libxcb-xinerama"
