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

Load the Material theme automatically (add this line to `~/.emacs`):

```elisp
(load-theme 'material)
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

Start LISPing.
