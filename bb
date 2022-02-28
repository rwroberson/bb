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
BLOG=~/documents/website/blog
BLOCKS=~/documents/website/blocks

# Retrieve title
TITLE="$(grep '<h1' $1 | sed 's/<[^>]*>//g;s/\s/\\ /g')"

# Add title to head
sed "s/~/$TITLE/" $BLOCKS/head.html >> head.html

# Build blog post from blocks and draft
cat head.html "$BLOCKS/nav.html" $1 "$BLOCKS/footer.html" > $BLOG/$1

# Cleanup
rm head.html
