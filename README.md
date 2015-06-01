# SELinuxModify
SELinuxModify is an app to enable/disable android selinux, a wrapper to setenforce command. Device must be rooted first.

[![Google Play](http://developer.android.com/images/brand/en_generic_rgb_wo_45.png)](https://play.google.com/store/apps/details?id=org.xdty.selinuxmodify)

[![Build Status](https://travis-ci.org/xdtianyu/SELinuxModify.svg?branch=master)](https://travis-ci.org/xdtianyu/SELinuxModify)

[![download status](https://img.shields.io/github/downloads/xdtianyu/SelinuxModify/latest/SelinuxModify-v1.0.1.apk.svg)](https://github.com/xdtianyu/SELinuxModify/releases/download/v1.0.1/SelinuxModify-v1.0.1.apk)

# Why create this app
I'm using samba to share files on my router and using CifsManager to mount smaba at android side. But SElinux prevent files access from other app, SElinux shall be set to Permissive. I can use "terminal for android" to do this, but bothered with input commands everytime. I tried "SELinux Mode Changer" and I'm curious about the permissions it request, so I create my own.
