DESCRIPTION = "Matchbox stroke recogniser"
LICENSE = "GPL"
DEPENDS = "libfakekey expat libxft"
SECTION = "x11/wm"
PV = "0.0+svnr${SRCREV}"

SRC_URI = "svn://svn.o-hand.com/repos/matchbox/trunk;module=${PN};proto=http \
           file://configure_fix.patch;patch=1;maxrev=1819 "

S = "${WORKDIR}/${PN}"

inherit autotools pkgconfig gettext

FILES_${PN} = "${bindir}/* \
	       ${datadir}/applications \
	       ${datadir}/pixmaps \
		${datadir}/matchbox-stroke"
