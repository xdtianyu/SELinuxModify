# SELinuxModify
SELinuxModify is an app to enable/disable android selinux, a wrapper to setenforce command. Device must be root first.

# The story, why create this app
I'm using samba to share files on my router and using CifsManager to mount smaba at android side. But SElinux prevent files access from other app, SElinux shall be set to Permissive. I can use "terminal for android" to do this, but bothered with input commands everytime. I tried "SELinux Mode Changer" and I'm curious about the permissions it request, so I create my own.
