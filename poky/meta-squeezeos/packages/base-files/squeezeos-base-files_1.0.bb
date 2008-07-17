DESCRIPTION = "Miscellaneous files for the base system."
SECTION = "base"
PRIORITY = "required"
LICENSE = "GPL"

PR = "r7"

SRC_URI = " \
	file://config \
	file://firstboot \
	file://fstab \
	file://group \
	file://hostname \
	file://hosts \
	file://if_mapping \
	file://inetd.conf \
	file://inittab \
	file://interfaces \
	file://issue \
	file://keep-after-upgrade \
	file://linuxrc \
	file://mdev.conf \
	file://motd \
	file://nsswitch.conf \
	file://passwd \
	file://protocols \
	file://rcS \
	file://rcS.local.example \
	file://services \
	file://shadow \
	file://suspend \
	file://udhcpc_action \
	file://wlan \
	file://wpa_action \
	file://wpa_supplicant.conf \
	file://dropbear_dss_host_key \
	file://dropbear_rsa_host_key \
	"
S = "${WORKDIR}"

dirs1777 = "/tmp"
dirs2775 = ""
dirs755 = "/bin /dev ${sysconfdir} ${sysconfdir}/default \
	   ${sysconfdir}/skel /lib /mnt /proc /home/root /sbin \
	   ${prefix} ${bindir} ${docdir} \
	   ${libdir} ${sbindir} ${datadir} \
	   ${localstatedir} \
	   ${localstatedir}/lib ${localstatedir}/log ${localstatedir}/run \
	   /sys ${localstatedir}/lib/misc ${localstatedir}/spool \
	   /mnt /media /media/card /media/cf /media/net /media/ram \
	   /media/union /media/realroot /media/hdd /media/mmc1 \
	   /mnt/storage /mnt/overlay /mnt/mmc ${sysconfdir}/init.d \
	   ${sysconfdir}/dropbear"


do_install () {
	for d in ${dirs755}; do
		install -m 0755 -d ${D}$d
	done
	for d in ${dirs1777}; do
		install -m 1777 -d ${D}$d
	done
	for d in ${dirs2775}; do
		install -m 2755 -d ${D}$d
	done

	# linuxrc, mounts overlay filesystem
	install -m 0755 ${WORKDIR}/linuxrc ${D}/linuxrc

	# base config
	install -m 0644 ${WORKDIR}/fstab ${D}${sysconfdir}/fstab
	install -m 0644 ${WORKDIR}/inetd.conf ${D}${sysconfdir}/inetd.conf
	install -m 0644 ${WORKDIR}/inittab ${D}${sysconfdir}/inittab
	install -m 0755 ${WORKDIR}/rcS ${D}${sysconfdir}/init.d/rcS
	install -m 0755 ${WORKDIR}/wlan ${D}${sysconfdir}/init.d/wlan
	install -m 0755 ${WORKDIR}/suspend ${D}${sysconfdir}/init.d/suspend
	install -m 0644 ${WORKDIR}/issue ${D}${sysconfdir}/issue
	install -m 0644 ${WORKDIR}/protocols ${D}${sysconfdir}/protocols
	install -m 0644 ${WORKDIR}/services ${D}${sysconfdir}/services
	install -m 0644 ${WORKDIR}/hosts ${D}${sysconfdir}/hosts
	install -m 0644 ${WORKDIR}/nsswitch.conf ${D}${sysconfdir}/nsswitch.conf
	install -m 0644 ${WORKDIR}/passwd ${D}${sysconfdir}/passwd
	install -m 0644 ${WORKDIR}/shadow ${D}${sysconfdir}/shadow
	install -m 0644 ${WORKDIR}/group ${D}${sysconfdir}/group
	install -m 0644 ${WORKDIR}/motd ${D}${sysconfdir}/motd
	install -m 0644 ${WORKDIR}/mdev.conf ${D}${sysconfdir}/mdev.conf
	install -m 0644 ${WORKDIR}/keep-after-upgrade ${D}${sysconfdir}/keep-after-upgrade
	install -m 0644 ${WORKDIR}/hostname ${D}${sysconfdir}/hostname
	ln -sf /proc/mounts ${D}${sysconfdir}/mtab

	# dropbear keys - these should be dynamically generated, but it takes too long
	install -m 0600 ${WORKDIR}/dropbear_dss_host_key ${D}${sysconfdir}/dropbear/dropbear_dss_host_key
	install -m 0600 ${WORKDIR}/dropbear_rsa_host_key ${D}${sysconfdir}/dropbear/dropbear_rsa_host_key

	# network scripts
	install -m 0755 -d ${D}${sysconfdir}/network/if-down.d
	install -m 0755 -d ${D}${sysconfdir}/network/if-post-down.d
	install -m 0755 -d ${D}${sysconfdir}/network/if-up.d
	install -m 0755 -d ${D}${sysconfdir}/network/if-pre-up.d
	install -m 0644 ${WORKDIR}/config ${D}${sysconfdir}/network/config
	install -m 0644 ${WORKDIR}/interfaces ${D}${sysconfdir}/network/interfaces
	install -m 0644 ${WORKDIR}/if_mapping ${D}${sysconfdir}/network/if_mapping
	install -m 0755 ${WORKDIR}/udhcpc_action ${D}${sysconfdir}/network/udhcpc_action
	install -m 0755 ${WORKDIR}/wpa_action ${D}${sysconfdir}/network/wpa_action

	# wlan config
	install -m 0644 ${WORKDIR}/wpa_supplicant.conf ${D}${sysconfdir}/wpa_supplicant.conf
}

PACKAGES = "${PN}-doc ${PN} ${PN}-dev ${PN}-dbg"
FILES_${PN} = "/"
FILES_${PN}-doc = "${docdir} ${datadir}/common-licenses"