# sls-spellchecker
**A Lambda wrapper of [LanguageTool](https://languagetool.org/).**
[![Build Status](https://travis-ci.org/isahb/sls-spellchecker.svg?branch=master)](https://travis-ci.org/isahb/sls-spellchecker)

Accepts max 1024 character texts.

The sample uses `AmericanEnglish` but others can be included easily by adding the necessary dependency in the pom.xml, for example for German language spoken in Germany:

    <dependency>
        <groupId>org.languagetool</groupId>
        <artifactId>language-de</artifactId>
        <version>4.8</version>
    </dependency>

In the `Handler`:

    private JLanguageTool langTool = new JLanguageTool(new GermanyGerman());

Serverless Framework is used for deployment and an API Gateway stack with `checkSpelling` resource is created.

To build: 
> mvn clean install

To deploy with Servlerless Framework in Frankfurt region:
> sls deploy --region eu-central-1


**Example request/response format**

   
```
{
   	"text": "The text to chek for spelling"
}
```
```
{
  "spellCheckSuggestions": [
    {
      "startPos": 12,
      "endPos": 16,
      "message": "Possible spelling mistake found",
      "suggestedReplacements": [
        "check",
        "Chen",
        "cheek",
        "chef",
        "Che",
        "chew",
        "CHK",
        "chem"
      ]
    }
  ]
}
```
**American English tests**



Cold starts for American English with a 2GB configuration take up to 10s. 

A warm lambda takes a few hundred milliseconds but never under 200 milliseconds in my tests.
