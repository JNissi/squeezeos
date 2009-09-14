DESCRIPTION = "Sato Bootable Live Image"

require poky-image-live.inc

LABELS += "boot install"

ROOTFS = "${DEPLOY_DIR_IMAGE}/poky-image-sato-${MACHINE}.ext3"

do_bootimg[depends] += "poky-image-sato:do_rootfs"
