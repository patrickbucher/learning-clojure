.PHONY: all clean

all: getting-clojure.pdf getting-clojure.epub getting-clojure.html getting-clojure.odt getting-clojure.docx

OPTS=--toc -s -N
PDFOPTS=--pdf-engine=xelatex -V documentclass=scrartcl -V papersize=a4 -V lang=en
FONTOPTS=-V mainfont="DejaVu Serif" -V sansfont="DejaVu Sans" -V monofont="DejaVu Sans Mono" -V fontsize=10pt

getting-clojure.pdf: getting-clojure.md
	pandoc -t pdf $(OPTS) $(PDFOPTS) $(FONTOPTS) $^ -o $@

getting-clojure.epub: getting-clojure.md
	pandoc -t epub $(OPTS) $^ -o $@

getting-clojure.html: getting-clojure.md
	pandoc -t html5 $(OPTS) $^ -o $@

getting-clojure.odt: getting-clojure.md
	pandoc -t odt $(OPTS) $^ -o $@

getting-clojure.docx: getting-clojure.md
	pandoc -t docx $(OPTS) $^ -o $@

clean:
	rm -f *.pdf *.epub *.html *.odt *.docx
