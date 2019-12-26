package org.hrp.serve.services;

import org.hrp.serve.IntegrationTest;
import org.hrp.serve.Utils;
import org.hrp.lib.models.HttpRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class ConditionServiceTest extends IntegrationTest {

    @Autowired
    ConditionService conditionService;


    @Test
    public void test_testRequest() {
        doTest_testRequest("$path === '/test'", true);
        doTest_testRequest("$method === 'post'", true);
        doTest_testRequest("$queryParams.x === 'hello'", true);
        doTest_testRequest("$queryParams.y === 343", true);
        doTest_testRequest("$queryParams.z[0] === 'world'", true);
        doTest_testRequest("$bodyString === '{\"a\":123, \"b\":\"efg\"}'", true);
        doTest_testRequest("$body.a === 123 && $body.b === 'efg'", true);
        doTest_testRequest("$headers.TOKEN === 'abcdefg12345'", true);
        doTest_testRequest("a = $headers.TOKEN === 'abcdefg12345'; a;", true);

        doTest_testRequest("$path === '/test' && $method === 'post' && $queryParams.y >= 343", true);
        doTest_testRequest("function check() { return $path === '/test' && $method === 'post' && $queryParams.y >= 343 }; check();", true);
        doTest_testRequest("if ($path === '/test') {true} else {false}", true);
        doTest_testRequest("if ($path === '/test') {'false'} else {'true'}", false);

        doTest_testRequest("$path === '/test1'", false);
        doTest_testRequest("$method === 'get'", false);
        doTest_testRequest("$queryParams.x === 'hello1'", false);
        doTest_testRequest("$queryParams.y === 3431", false);
        doTest_testRequest("$queryParams.z[0] === 'world1'", false);
        doTest_testRequest("$bodyString === '{\"a\":1231, \"b\":\"efg\"}'", false);
        doTest_testRequest("$body.a === 1231 && $body.b === 'efg'", false);
        doTest_testRequest("$headers.TOKEN === 'abcdefg123451'", false);
        doTest_testRequest("headers.TOKEN === 'abcdefg123451'", false);
        doTest_testRequest("headers.TOKEN === 'abcdefg123451'", false);
    }

    public void doTest_testRequest(String condition, boolean expectedResult) {
        HttpRequest request = Utils.createDefaultHttpRequest();
        boolean actualResult = conditionService.testRequest(request, condition);
        assertEquals(expectedResult, actualResult);
    }
}
