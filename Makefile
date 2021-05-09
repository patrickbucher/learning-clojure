.PHONY: all clean

all: notes.pdf notes.epub notes.html

notes.pdf: notes.md
	pandoc -t ms -s $^ -o $@

notes.epub: notes.md
	pandoc -t epub -s $^ -o $@

notes.html: notes.md
	pandoc -t html5 -s $^ -o $@

clean:
	rm -f *.pdf *.epub *.html
