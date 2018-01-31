# SNLP Fact Checker

## Goal
Given a list of sentences test the correctness of each sentence

## Method Overview
- Extract Subject, Relation, Object triples from the input sentence
- Find Wikipedia article of the extracted Subject
- Parse Wikipedia Infobox of the Subject to extract Subject, Releation, Object triples
- Compare Wikipedia triples against the input triples
- Generate ttl file of results

### Example
- Input:
```
3347316	Nobel Peace Prize is Henry Dunant's honour.
```

- **Subject:** "Henry Dunant" **Object:** "Nobel Peace Prize" **Relation:** "AWARD"
- Related Wikipedia article: `en.wikipedia.org/wiki/Henry_Dunant`
- Infobox: 

![alt text](https://github.com/semicolonMissing/SNLP-Fact-Checker/blob/master/infobox.PNG)

- Triples extracted from Wikipedia:
  1. "Henry Dunant", "BORN_IN", "Geneva, Switzerland"
  2. "Henry Dunant", "DIE_IN", "Heiden, Switzerland"
  3. "Henry Dunant", "AWARD", "Nobel Peace Prize"

- Comparing Input triple against Wikipedia triples yields the fact "Henry Dunant", "AWARD", "Nobel Peace Prize" is correct
- Output 1.0 for this sentence

- Output example
```
<http://swc2017.aksw.org/task2/dataset/3347316><http://swc2017.aksw.org/hasTruthValue>"1.0"^^<http://www.w3.org/2001/XMLSchema#double> .
```
### Running the application
- create a folder called `data` and put `test.tsv` file in this folder
- set VM arguments as follows: `-Xmx3060m -Dfile.encoding=UTF-8`
- run Main file `de.upb.snlp.scm.Main`
- `result.ttl` file will be created in `data` folder
- use `conf.properties` file to manage configuration (NER classifiers should be downloaded from [nlp.stanford.edu](https://nlp.stanford.edu/software/CRF-NER.shtml#Download)) 

## Presentation
[Presentation of the process](https://github.com/semicolonMissing/SNLP-Fact-Checker/blob/master/presentation.pdf)

## License

[MIT](https://github.com/semicolonMissing/SNLP-Fact-Checker/blob/master/LICENSE)

