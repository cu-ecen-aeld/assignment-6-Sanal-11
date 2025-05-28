# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8ed1a118f474eea5e159b560c339329b \
                    file://assignment-autotest/LICENSE;md5=cde0fddafb4332f35095da3d4fa989dd \
                    file://assignment-autotest/Unity/LICENSE.txt;md5=b7dd0dffc9dda6a87fa96e6ba7f9ce6c"

#SRC_URI = "gitsm://github.com/cu-ecen-aeld/assignments-3-and-later-Sanal-11.git;protocol=https;branch=main \
#           file://0001-bitbake-build-changes-cflags.patch \
#           "

SRC_URI = "gitsm://github.com/cu-ecen-aeld/assignments-3-and-later-Sanal-11.git;protocol=https;branch=main"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "90cebc73a3d346f817f16f77359513b2ddc75a2b"

DEPENDS += "ruby-native"

S = "${WORKDIR}/git"

inherit cmake

# Specify any options you want to pass to cmake using EXTRA_OECMAKE:
# EXTRA_OECMAKE = "PREFIX=${D}${bindir} CC='${CC}' CFLAGS='${CFLAGS} -Wl, --hash-style=gnu'"
# TARGET_LDFLAGS += "-Wl,--hash-style=gnu -pthread -lrt"

TARGET_LDFLAGS += "-pthread -lrt"

do_configure () {
	:
}

do_compile () {
	oe_runmake -C ${S}/server
}

do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
	install -d ${D}${bindir}
	install -m 0755 ${S}/server/aesdsocket ${D}${bindir}/

	# Install the init script
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/server/aesdsocket-start-stop ${D}${sysconfdir}/init.d/aesdsocket-start-stop.sh
}
