DESCRIPTION = "LibTomCrypt is a fairly comprehensive, modular \
and portable cryptographic toolkit that provides developers \
with a vast array of well known published block ciphers, \
one-way hash functions, chaining modes, pseudo-random number \
generators, public key cryptography and a plethora of other \
routines."
SECTION = "libs/network"
PRIORITY = "optional"
DEPENDS = "libtfm"
LICENSE = "PD"
PR = "r0"

ARM_INSTRUCTION_SET = "arm"

SRC_URI = "http://libtom.org/files/crypt-${PV}.tar.bz2"

inherit autotools

CFLAGS_prepend = "-DTFM_DESC -DUSE_TFM"

EXTRA_OEMAKE = "library"

#FIXME add patch to make it shared

do_stage() {
	oe_libinstall -a libtomcrypt ${STAGING_LIBDIR}/
	install -m 0644 ${S}/src/headers/*.h ${STAGING_INCDIR}/
}

do_install() {
	:
}

