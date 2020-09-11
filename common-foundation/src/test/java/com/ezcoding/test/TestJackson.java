package com.ezcoding.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-09 11:33
 */
public class TestJackson {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree("{\"body\":0,\"sysHead\":{\"transactionDate\":\"1599619977343\",\"version\":\"1.0\",\"providerId\":\"00\",\"providerSequenceNo\":\"501024341987688448\"},\"appHead\":{\"pageInfo\":{},\"returnCode\":\"0000000000\",\"returnMessage\":\"处理成功\"}}\n");
        JsonNode node = jsonNode.at("/appHead/returnMessage");

        String s = objectMapper.treeToValue(node, String.class);
        System.out.println(s);

        System.out.println(node);
    }

}
