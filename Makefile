.PHONY: all clean

all: notes.pdf notes.epub notes.html notes.odt notes.docx

OPTS=--toc -s -N

notes.pdf: notes.md
	pandoc -t pdf $(OPTS) $^ -o $@

notes.epub: notes.md
	pandoc -t epub $(OPTS) $^ -o $@

notes.html: notes.md
	pandoc -t html5 $(OPTS) $^ -o $@

notes.odt: notes.md
	pandoc -t odt $(OPTS) $^ -o $@

notes.docx: notes.md
	pandoc -t docx $(OPTS) $^ -o $@

clean:
	rm -f *.pdf *.epub *.html *.odt *.docx
