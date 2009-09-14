DESCRIPTION = "The  fonts - TTF Edition"
SECTION = "x11/fonts"
PRIORITY = "optional"
LICENSE = "GPLv2"
PACKAGE_ARCH = "all"
RDEPENDS = "fontconfig-utils"
PE = "1"

SRC_URI = "https://fedorahosted.org/releases/l/i/liberation-fonts/liberation-fonts-${PV}.tar.gz \
           file://30-liberation-aliases.conf"

do_install () { 
        install -d ${D}${datadir}/fonts/ttf/ 
        for i in *.ttf; do 
                install -m 0644 $i ${D}${prefix}/share/fonts/ttf/${i} 
        done 

	install -d ${D}${sysconfdir}/fonts/conf.d/
	install -m 0644 ${WORKDIR}/30-liberation-aliases.conf ${D}${sysconfdir}/fonts/conf.d/

        install -d ${D}${prefix}/share/doc/${PN}/ 
	install -m 0644 License.txt ${D}${datadir}/doc/${PN}/
} 

pkg_postinst () {
#!/bin/sh
fc-cache
}

PACKAGES = "${PN}"
FILES_${PN} += "${sysconfdir} ${datadir}"
