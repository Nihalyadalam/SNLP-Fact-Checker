# SNLP Fact Checker

## Goal
Given a list of sentences test the correctness of each sentence

## Method Overview
- Extract Subject, Relation, Object triples from the input sentence
- Find Wikipedia article of the extracted Subject
- Parse Wikipedia Infobox of the Subject to extract Subject, Releation, Object triples
- Compare Wikipedia triples against the input triples
- Generate ttl file of results
