# nix-prefetch-url --type sha256 --unpack https://...
{ pkgs ? import (builtins.fetchTarball {
    name = "nixpkgs-22.05pre352484.1882c6b7368";
    url = "https://releases.nixos.org/nixpkgs/nixpkgs-22.05pre352484.1882c6b7368/nixexprs.tar.xz";
    sha256 = "03amwzc9j67c86dmxnknchfk30a63jjky9hhw2wilzdrya07w0kr";
  }) {} }:

let
  jdkVersion = pkgs.openjdk17;
in
  pkgs.mkShell {

    # build-time dependencies
    buildInputs = [
      jdkVersion
      # pkgs.sbt
      (pkgs.sbt.overrideAttrs (old: old // { jre = jdkVersion.home; }))
      pkgs.scalafmt
      pkgs.ncurses
      pkgs.envsubst
      pkgs.flyway
      pkgs.nodejs-16_x
    ];

    # Runtime dependencies
    propagatedBuildInputs = [
      # C libraries required to build
    ];


    shellHook = ''
      export JAVA_HOME='${jdkVersion.home}'
      export SBT_HOME='${pkgs.sbt}'
      export PATH="$PWD/node_modules/.bin/:$PATH"
    '';
  }
