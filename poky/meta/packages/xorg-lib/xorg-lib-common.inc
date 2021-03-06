HOMEPAGE = "http://www.x.org"
SECTION = "x11/libs"
LICENSE = "MIT-X"
DEPENDS = "util-macros"

XORG_PN = "${BPN}"

SRC_URI = "${XORG_MIRROR}/individual/lib/${XORG_PN}-${PV}.tar.bz2"

S = "${WORKDIR}/${XORG_PN}-${PV}"

inherit autotools_stage pkgconfig

EXTRA_OECONF = "--enable-malloc0returnsnull"
