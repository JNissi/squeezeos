DESCRIPTION = "Various benchmarning tests for X"
HOMEPAGE = "http://www.o-hand.com"
SECTION = "devel"
LICENSE = "GPL"
DEPENDS = "pango libxext libxft virtual/libx11 gtk+"
PV = "0.0+svnr${SRCREV}"
PR = "r1"

inherit autotools

SRC_URI = "svn://svn.o-hand.com/repos/misc/trunk;module=fstests;proto=http"

S = "${WORKDIR}/fstests/tests"

do_install() {
    install -d ${D}${bindir}
    find . -name "test-*" -type f -perm -755 -exec install -m 0755 {} ${D}${bindir} \;   
}
