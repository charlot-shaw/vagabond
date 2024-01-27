{
    description = "WebApp for Knave 2e character generation.";

    inputs = {
        nixpkgs.url = "github:NixOs/nixpkgs";

		flake-utils.url = "github:numtide/flake-utils";

    };

    outputs = {self,nixpkgs,flake-utils}: 
    flake-utils.lib.eachDefaultSystem (system: 
    let
        pkgs = nixpkgs.legacyPackages.${system}; in 
     {
        packages = rec {
            default = vagabond;

            vagabond = pkgs.stdenv.mkDerivation rec {
                pname = "vagabond";
                version = "0.0.1";

                src = ./.;

                buildInputs = with pkgs; [
                    nodejs_20
                    babashka
                    openjdk17-bootstrap
                    clojure
                ];
            };

            vagabondnpm = pkgs.buildNpmPackage {
                pname = "vagabondnpm";
                version = "0.0.1";

                buildInputs = with pkgs; [
                    nodejs_20
                    babashka
                    openjdk17-bootstrap
                    clojure
                ];

                src = ./.;

                npmDepsHash = "sha256-l9JMOPDuL2x8wMKVagRE/gQD3wEEen7m66LqSZatpMc=";

                buildPhase = ''
                mkdir $out
                npx shadow-cljs compile frontend
                cp dist/index.html $out
                '';
            };
        };
    }
    );  
}