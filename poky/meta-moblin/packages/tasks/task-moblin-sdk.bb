#
# Copyright (C) 2008 Intel Corporation.
#

DESCRIPTON = "Software Development Tasks for Moblin"
DEPENDS = "task-moblin"
PR = "r6"

ALLOW_EMPTY = "1"
#PACKAGEFUNCS =+ 'generate_sdk_pkgs'

PACKAGES = "\
    task-moblin-sdk \
    task-moblin-sdk-dbg \
    task-moblin-sdk-dev"

RDEPENDS_task-moblin-sdk = "\
    autoconf \
    automake \
    binutils \
    binutils-symlinks \
    coreutils \
    cpp \
    cpp-symlinks \
    diffutils \
    gcc \
    gcc-symlinks \
    g++ \
    g++-symlinks \
    git \
    gettext \
    make \
    intltool \
    libstdc++ \
    libstdc++-dev \
    libtool \
    perl-module-re \
    perl-module-text-wrap \
    pkgconfig \
    subversion \
    findutils \
    quilt \
    less \
    distcc"

#python generate_sdk_pkgs () {
#    moblin_pkgs = read_pkgdata('task-moblin', d)['PACKAGES']
#    pkgs = bb.data.getVar('PACKAGES', d, 1).split()
#    for pkg in moblin_pkgs.split():
#        newpkg = pkg.replace('task-moblin', 'task-moblin-sdk')
#
#        # for each of the task packages, add a corresponding sdk task
#        pkgs.append(newpkg)
#
#        # for each sdk task, take the rdepends of the non-sdk task, and turn
#        # that into rrecommends upon the -dev versions of those, not unlike
#        # the package depchain code
#        spkgdata = read_subpkgdata(pkg, d)
#
#        rdepends = explode_deps(spkgdata.get('RDEPENDS_%s' % pkg) or '')
#        rreclist = []
#
#        for depend in rdepends:
#            split_depend = depend.split(' (')
#            name = split_depend[0].strip()
#            if packaged('%s-dev' % name, d):
#                rreclist.append('%s-dev' % name)
#            else:
#                deppkgdata = read_subpkgdata(name, d)
#                rdepends2 = explode_deps(deppkgdata.get('RDEPENDS_%s' % name) or '')
#                for depend in rdepends2:
#                    split_depend = depend.split(' (')
#                    name = split_depend[0].strip()
#                    if packaged('%s-dev' % name, d):
#                        rreclist.append('%s-dev' % name)
#
#            oldrrec = bb.data.getVar('RRECOMMENDS_%s' % newpkg, d) or ''
#            bb.data.setVar('RRECOMMENDS_%s' % newpkg, oldrrec + ' ' + ' '.join(rreclist), d)
#            # bb.note('RRECOMMENDS_%s = "%s"' % (newpkg, bb.data.getVar('RRECOMMENDS_%s' % newpkg, d)))
#
#    # bb.note('pkgs is %s' % pkgs)
#    bb.data.setVar('PACKAGES', ' '.join(pkgs), d)
#}
#
#PACKAGES_DYNAMIC = "task-moblin-sdk-*"
