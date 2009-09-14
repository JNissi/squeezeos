DESCRIPTION = "Speex is an Open Source/Free Software patent-free audio compression format designed for speech."
SECTION = "libs"
LICENSE = "BSD"
HOMEPAGE = "http://www.speex.org"
DEPENDS = "libogg"

SRC_URI = "http://downloads.us.xiph.org/releases/speex/speex-1.2beta3.tar.gz"
S = "${WORKDIR}/${PN}-1.2beta3"

PARALLEL_MAKE = ""

inherit autotools_stage pkgconfig

EXTRA_OECONF = " --enable-fixed-point --with-ogg-libraries=${STAGING_LIBDIR} \
		 --disable-float-api --disable-vbr \
                 --with-ogg-includes=${STAGING_INCDIR} --disable-oggtest"

PACKAGES += "${PN}-bin"
FILES_${PN} = "${libdir}/libspeex.so.*"
FILES_${PN}-dev += "${libdir}/libspeex.so.*"
FILES_${PN}-bin = "${bindir}"
