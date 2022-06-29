.PHONY: all clean

all: getting-clojure.pdf getting-clojure.epub getting-clojure.html

OPTS=--toc -s -N -V date=`date +'%Y-%m-%d'` -V linkcolor=blue -V urlcolor=blue
PDFOPTS=--pdf-engine=xelatex -V documentclass=scrartcl -V papersize=a4 -V lang=en
FONTOPTS=-V mainfont="Crimson Pro" -V sansfont="Lato" -V monofont="Fantasque Sans Mono" -V fontsize=11pt

getting-clojure.pdf: getting-clojure.md
	pandoc -t pdf $(OPTS) $(PDFOPTS) $(FONTOPTS) $^ -o $@

getting-clojure.epub: getting-clojure.md
	pandoc -t epub $(OPTS) $^ -o $@

getting-clojure.html: getting-clojure.md
	pandoc -t html5 $(OPTS) $^ -o $@

clean:
	rm -f *.pdf *.epub *.html
