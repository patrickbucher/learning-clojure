# Debian Setup

Install Clojure:

    sudo apt install -y clojure
    clojure
    => (println "Hello, Clojure!")

Install Leiningen:

    sudo apt install -y leiningen
    lein --version
    lein repl
    => (println "Hello, Leiningen!")

Create a Project Leiningen:

    lein new app hello
    cd hello
    lein run

Install Emacs:

    sudo apt install -y emacs-nox

Configure Emacs:

    emacs ~/.emacs

Add the following:

    (require 'package)
    (add-to-list 'package-archives
                 '("melpa-stable" . "https://stable.melpa.org/packages/"))
    (package-initialize)

Then run in Emacs:

    M-x package-install [RET] clojure-mode [RET]
    M-x package-install [RET] cider [RET]
    M-x package-install [RET] material-theme [RET]

Extend the (changed) configuration:

    (load-theme 'material)
    (global-display-line-numbers-mode)

Return to `hello` project:

    emacs src/hello/core.clj

Allow theme loading twice using `y`.

Activate CIDER:

    M-x cider-jack-in

Or short:

    M-x -jac

Demonstrate:

- `C-c C-k`: evaluate current buffer
- `C-c C-e`: evaluate expression before point
- `C-c C-d d`: show doc of symbol at point
- `C-c C-d j`: show javadoc of symbol at point
- `C-c C-d w`: show doc of symbol at point in web browser
- `C-c C-t t`: run test at point
- `C-c M-p`: switch to REPL
- `M-<TAB>`: complete expression below point

