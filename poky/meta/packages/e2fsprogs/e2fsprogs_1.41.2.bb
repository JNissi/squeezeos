require e2fsprogs.inc

PR = "r11"

SRC_URI += "file://no-hardlinks.patch;patch=1"

PARALLEL_MAKE = ""

EXTRA_OECONF += " --sbindir=${base_sbindir} --enable-elf-shlibs"
EXTRA_OECONF_darwin = "--enable-dynamic-e2fsck --sbindir=${base_sbindir} --enable-bsd-shlibs"
EXTRA_OECONF_darwin8 = "--enable-dynamic-e2fsck --sbindir=${base_sbindir} --enable-bsd-shlibs"

do_compile_prepend () {
	find ./ -print|xargs chmod u=rwX
	( cd util; ${BUILD_CC} subst.c -o subst )
}

ext2fsheaders = "ext2_ext_attr.h bitops.h ext2_err.h \
		 ext2_types.h ext2_fs.h ext2_io.h \
		 ext2fs.h ext3_extents.h"
e2pheaders = "e2p.h"

do_stage () {
	oe_libinstall -a -C lib libblkid ${STAGING_LIBDIR}/
	oe_libinstall -a -C lib libe2p ${STAGING_LIBDIR}/
	oe_libinstall -a -C lib libext2fs ${STAGING_LIBDIR}/
	oe_libinstall -a -C lib libuuid ${STAGING_LIBDIR}/
	install -d ${STAGING_INCDIR}/e2p
	for h in ${e2pheaders}; do
		install -m 0644 lib/e2p/$h ${STAGING_INCDIR}/e2p/ || die "failed to install $h"
	done
	install -d ${STAGING_INCDIR}/ext2fs
	for h in ${ext2fsheaders}; do
		install -m 0644 lib/ext2fs/$h ${STAGING_INCDIR}/ext2fs/ || die "failed to install $h"
	done
	install -d ${STAGING_INCDIR}/blkid
	for h in blkid.h blkid_types.h; do
		install -m 0644 lib/blkid/$h ${STAGING_INCDIR}/blkid/ || die "failed to install $h"
	done
	install -d ${STAGING_INCDIR}/uuid
        install -m 0644 lib/uuid/uuid.h ${STAGING_INCDIR}/uuid/ || die "failed to install $h"
        
	install -d ${STAGING_LIBDIR}/pkgconfig
	for pc in lib/*/*.pc; do
		install -m 0644 $pc ${STAGING_LIBDIR}/pkgconfig/ || die "failed to install $h"
	done
}

# blkid used to be part of e2fsprogs but is useful outside, add it
# as an RDEPENDS so that anything relying on it being in e2fsprogs
# still works
RDEPENDS_e2fsprogs = "e2fsprogs-blkid e2fsprogs-uuidgen e2fsprogs-badblocks"

PACKAGES =+ "e2fsprogs-blkid e2fsprogs-uuidgen e2fsprogs-e2fsck e2fsprogs-mke2fs e2fsprogs-fsck e2fsprogs-tune2fs e2fsprogs-badblocks libuuid"
FILES_e2fsprogs-blkid = "${base_sbindir}/blkid"
FILES_e2fsprogs-uuidgen = "${bindir}/uuidgen"
FILES_e2fsprogs-fsck = "${base_sbindir}/fsck"
FILES_e2fsprogs-e2fsck = "${base_sbindir}/e2fsck ${base_sbindir}/fsck.ext*"
FILES_e2fsprogs-mke2fs = "${base_sbindir}/mke2fs ${base_sbindir}/mkfs.ext*"
FILES_e2fsprogs-tune2fs = "${base_sbindir}/tune2fs ${base_sbindir}/e2label ${base_sbindir}/findfs"
FILES_e2fsprogs-badblocks = "${base_sbindir}/badblocks"
FILES_libuuid = "${libdir}/libuuid.so.*"
