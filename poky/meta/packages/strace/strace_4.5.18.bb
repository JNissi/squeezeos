DESCRIPTION = "strace is a system call tracing tool."
SECTION = "console/utils"
LICENSE = "GPL"
PR = "r0"

SRC_URI = "${SOURCEFORGE_MIRROR}/strace/strace-${PV}.tar.bz2 \
           file://strace-fix-arm-bad-syscall.patch;patch=1 \
           file://strace-undef-syscall.patch;patch=1"
inherit autotools

export INCLUDES = "-I. -I./linux"
