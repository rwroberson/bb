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

FILE=$(mktemp)

# Retrieve title and date
TITLE="$(grep '<h1' $1 | sed 's/<[^>]*>//g;s/\s/\\ /g')"
DATE=$(date +%Y-%m-%d)

# Build blog post from blocks and draft
sed "s/~/$TITLE/;s/&/$DATE/" $BLOCKS/head.html >> $FILE
echo "<nav>" >> $FILE
cat "$BLOCKS/nav.html" >> $FILE
echo "</nav>" >> $FILE
echo "<body>" >> $FILE
cat $1 >> $FILE
echo "</body>" >> $FILE
echo "<footer>" >> $FILE
cat "$BLOCKS/footer.html" >> $FILE
echo "</footer>" >> $FILE
cat $FILE > $BLOG/$1

# Add to blog index

BI="<li>$DATE <a href=\"blog\/$1\">$TITLE<\/a><\/li>"

sed -i "s/<!--BB-->.*/<!--BB-->\n$BI/" $INDEX
