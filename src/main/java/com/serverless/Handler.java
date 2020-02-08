package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.model.ApiGatewayResponse;
import com.serverless.model.Response;
import com.serverless.model.SpellCheckResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.*;

import com.serverless.model.SpellCheckResult.SpellCheckSuggestion;

/**
 * @author isahb
 */
public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger LOG = LogManager.getLogger(Handler.class);
    public static final Map<String, String> HEADERS = Collections.singletonMap("X-Powered-By", "Languagetool");
    private JLanguageTool langTool = new JLanguageTool(new AmericanEnglish());
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        LOG.info("received: {}", input);
        try {
            String bodyJson = input.getOrDefault("body", "{}").toString();
            Map<String, String> requestBody = objectMapper.readerFor(Map.class).readValue(bodyJson);
            String text = requestBody.get("text");
            if (text == null) {
                return ApiGatewayResponse.builder().setStatusCode(400).setHeaders(HEADERS).build();
            }
            List<RuleMatch> matches = langTool.check(text);
            SpellCheckResult spellCheckResult = new SpellCheckResult();
            matches.stream().forEach(match -> {
                SpellCheckSuggestion spellCheckSuggestion = new SpellCheckSuggestion(match.getFromPos(),
                        match.getToPos(), match.getMessage(), match.getSuggestedReplacements());
                spellCheckResult.addCorrectionSuggestion(spellCheckSuggestion);
            });
            return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(spellCheckResult)
                    .setHeaders(HEADERS).build();
        } catch (IOException e) {
            LOG.error("Error", e);
            return ApiGatewayResponse.builder().setStatusCode(500).setObjectBody(new Response("IO Error", new HashMap<>()))
                    .setHeaders(HEADERS).build();
        }
    }
}
