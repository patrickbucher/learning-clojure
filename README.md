# Learning Clojure

## Setup (Arch Linux)

JDK, Clojure, and Leiningen:

    sudo pacman -S jdk-openjdk clojure leiningen

Emacs:

    sudo pacman -S emacs-nox

### Emacs Configuration

Configure MELPA package repository in `~/.emacs`:

```elisp
(require 'package)
(add-to-list 'package-archives
             '("melpa-stable" . "https://stable.melpa.org/packages/"))
(package-initialize)
```

Re-open Emacs to install Clojure Mode, CIDER, and the Material theme (personal choice):

    M-x package-install [RET] clojure-mode [RET]
    M-x package-install [RET] cider [RET]
    M-x package-install [RET] material-theme [RET]

Load the Material theme automatically  and show line numbers automatically (add
this line to `~/.emacs`):

```elisp
(load-theme 'material)
(global-display-line-numbers-mode)
```

### Leiningen, CIDER, and Emacs Interplay

Create a new demo project:

```bash
lein new app hello-world
cd hello-world
emacs src/hello_world/core.clj
```

Run in Emacs:

    M-x cider-jack-in

Or shorter:

    M-x -jac

Some useful key bindings:

- `C-c C-k`: evaluate current buffer
- `C-c C-e`: evaluate expression before point
- `C-c C-d d`: show doc of symbol at point
- `C-c C-d j`: show javadoc of symbol at point
- `C-c C-d w`: show doc of symbol at point in web browser
- `C-c C-t t`: run test at point
- `C-c M-p`: switch to REPL
- `M-<TAB>`: complete expression below point

## Links

- [Clojure](https://clojure.org/)
    - [Reference](https://clojure.org/reference/reader)
- [CIDER](https://cider.mx/)
    - [CIDER Docs](https://docs.cider.mx/cider/index.html)
- [Emacs](https://www.gnu.org/software/emacs/)
    - [Emacs Documentation](https://www.gnu.org/software/emacs/documentation.html)
