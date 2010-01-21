SECTION = "libs"
DEPENDS = "libogg"
DESCRIPTION = "Ogg Vorbis is a high-quality lossy audio codec \
that is free of intellectual property restrictions. libvorbis \
is the main vorbis codec library."
LICENSE = "BSD"
PR = "r6"

SRC_URI = "http://downloads.xiph.org/releases/vorbis/libvorbis-${PV}.tar.gz"

inherit autotools_stage pkgconfig

# vorbisfile.c reveals a problem in the gcc register spilling for the
# thumb instruction set...
FULL_OPTIMIZATION_thumb = "-O0"

EXTRA_OECONF = "--with-ogg-libraries=${STAGING_LIBDIR} \
	        --with-ogg-includes=${STAGING_INCDIR}"
