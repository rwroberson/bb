#!/bin/sh

# README!
# 
# You need a /blog directory for your blog posts
# and a /blocks directory for you head/nav/footer files.
# 
# Place this script in a directory called .drafts inside your /blog directory
# (it would be "/blog/.drafts").
# You will also place your body text in this directory with the same filename
# so that you have "blog/.drafts/test.html" and "blog/test.html".

# Define directories directory
BLOG=$HOME/doc/website/blog
BLOCKS=$HOME/doc/website/.blocks
INDEX=$HOME/doc/website/blogindex.html

# Make temp file

HEAD=$(mktemp)

# Retrieve title and date
TITLE="$(grep '<h1' $1 | sed 's/<[^>]*>//g;s/\s/\\ /g')"
DATE=$(date +%Y-%m-%d)

# Add title and date to head
sed "s/~/$TITLE/;s/&/$DATE/" $BLOCKS/head.html >> $HEAD

# Build blog post from blocks and draft
cat $HEAD "$BLOCKS/nav.html" $1 "$BLOCKS/footer.html" > $BLOG/$1

# Add to blog index

BI="<li>$DATE <a href=\"blog\/$1\">$TITLE<\/a><\/li>"

sed -i "s/<!--BB-->.*/<!--BB-->$BI/" $INDEX
