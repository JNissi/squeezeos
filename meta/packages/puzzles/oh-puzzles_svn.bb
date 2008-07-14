DESCRIPTION = "Portable Puzzle Collection"
LICENSE = "MIT"
SECTION = "x11"
DEPENDS = "gtk+ gconf intltool-native librsvg libowl"

PV = "0.1+svnr${SRCREV}"
PR = "r7"

bindir = "/usr/games"

inherit autotools pkgconfig

SRC_URI = "svn://svn.o-hand.com/repos/;module=oh-puzzles;proto=http \
	file://oh-puzzles-owl-menu.patch;patch=1;pnum=0"
S = "${WORKDIR}/${PN}"

EXTRA_OEMAKE += "GCONF_DISABLE_MAKEFILE_SCHEMA_INSTALL=1"

do_install_append () {
    install -d ${D}/${datadir}/applications/

    cd ${D}/${prefix}/games
    for prog in *; do
	if [ -x $prog ]; then
            # Convert prog to Title Case
            title=$(echo $prog | sed 's/\(^\| \)./\U&/g')
	    echo "making ${D}/${datadir}/applications/$prog.desktop"
	    cat <<STOP > ${D}/${datadir}/applications/$prog.desktop
[Desktop Entry]
Name=$title
Exec=${prefix}/games/$prog
Icon=applications-games
Terminal=false
Type=Application
Categories=Game;
StartupNotify=true
X-MB-SingleInstance=true
Comment=Play $title.
STOP
        fi
    done
}

PACKAGES += ${PN}-extra
RDEPENDS_${PN}-extra += "oh-puzzles"

FILES_${PN} = "/usr/share/pixmaps /usr/share/oh-puzzles/"
FILES_${PN}-dbg += "/usr/games/.debug/*"
FILES_${PN}-extra = "/usr/games/ /usr/share/applications /etc/gconf/schemas"

python __anonymous () {
    import bb
    var = bb.data.expand("FILES_${PN}", d, 1)
    data = bb.data.getVar(var, d, 1)
    for name in ("bridges", "fifteen", "inertia", "map", "samegame", "slant"):
        data = data + " /usr/games/%s" % name
        data = data + " /usr/share/applications/%s.desktop" % name
        data = data + " /etc/gconf/schemas/%s.schemas" % name
    bb.data.setVar(var, data, d)
}
