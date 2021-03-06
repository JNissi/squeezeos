DESCRIPTION = "Startup notification support"
LICENSE = "LGPL"
SECTION = "libs"
PRIORITY = "optional"
DEPENDS = "virtual/libx11 libsm"

inherit autotools_stage pkgconfig 

SRC_URI = "http://www.freedesktop.org/software/startup-notification/releases/${PN}-${PV}.tar.gz"

do_configure_prepend () {
        export X_LIBS=" -L${STAGING_LIBDIR}"
}
