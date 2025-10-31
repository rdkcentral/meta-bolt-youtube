# meta-bolt-youtube

Bitbake meta layer extending the **bolt** distro with recipes allowing to build Cobalt OCI image.

# Setup and building

See [Setup and building](https://github.com/rdkcentral/meta-bolt-distro/blob/develop/README.md#setup-and-building)
section in the [meta-bolt-distro](https://github.com/rdkcentral/meta-bolt-distro) documentation.

## Cobalt OCI image building instructions

* Download this repository and enter its root directory.
```
git clone https://github.com/rdkcentral/meta-bolt-youtube.git
cd meta-bolt-youtube
```

* Setup the build environment.
```
source setup-environment
```

* Start building the Cobalt OCI image.
```
bitbake cobalt-bolt-image
```
