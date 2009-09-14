DESCRIPTION =	"Galago is a desktop presence framework, designed to transmit presence information between programs."
HOMEPAGE =	"http://www.galago-project.org/"
LICENSE =	"LGPL"
DEPENDS = 	"gettext dbus glib-2.0 dbus-glib"

SRC_URI =	"http://www.galago-project.org/files/releases/source/${PN}/${P}.tar.gz \
                 file://pkgconfig.patch;patch=1 "
PR = "r1"

inherit autotools_stage pkgconfig

EXTRA_OECONF = "--disable-tests --disable-check"
