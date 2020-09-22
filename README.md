# sls-spellchecker
**A Lambda wrapper of [LanguageTool](https://languagetool.org/).**
[![Build Status](https://travis-ci.org/isahb/sls-spellchecker.svg?branch=master)](https://travis-ci.org/isahb/sls-spellchecker)

Limit set to max 1024 character texts. (`com.isahb.slsspellchecker.Handler.CHARACTER_LIMIT`)

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




CURL (replace `api-gateway-id` with your generated id after stack is deployed)

``
    curl --location --request POST 'https://[api-gateway-id].execute-api.eu-central-1.amazonaws.com/dev/checkSpelling' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "text": "Please not that this sentence has erors"
    }'
``

** Request/Response format**
```
{
   "text": "Please not that this sentence has erors"
}
```
```
{
    "error": null,
    "spellCheckSuggestions": [
        {
            "startPos": 7,
            "endPos": 10,
            "message": "Did you mean <suggestion>note</suggestion>?",
            "shortMessage": "Possible typo",
            "type": "Other",
            "suggestedReplacements": [
                "note"
            ],
            "suggestionMeta": {
                "categoryId": "TYPOS",
                "categoryName": "Possible Typo",
                "correctExamples": [],
                "incorrectExamples": [
                    {
                        "example": "Please <marker>not</marker> that saying “Open Source” does not mean very much.",
                        "corrections": [
                            "note"
                        ]
                    }
                ]
            }
        },
        {
            "startPos": 34,
            "endPos": 39,
            "message": "Possible spelling mistake found.",
            "shortMessage": "Spelling mistake",
            "type": "Other",
            "suggestedReplacements": [
                "errors",
                "Eros",
                "errs"
            ],
            "suggestionMeta": {
                "categoryId": "TYPOS",
                "categoryName": "Possible Typo",
                "correctExamples": [
                    {
                        "example": "This <marker>sentence</marker> contains a spelling mistake."
                    }
                ],
                "incorrectExamples": [
                    {
                        "example": "This <marker>sentenc</marker> contains a spelling mistake.",
                        "corrections": [
                            "sentence"
                        ]
                    }
                ]
            }
        }
    ]
}
```
**American English tests**



Cold starts for American English with a 2GB configuration take up to 10s. 

A warm lambda takes a few hundred milliseconds but never under 200 milliseconds in my tests.
