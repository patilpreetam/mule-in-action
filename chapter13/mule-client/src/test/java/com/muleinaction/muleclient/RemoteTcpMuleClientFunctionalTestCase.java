package com.muleinaction.muleclient;

import org.mule.api.FutureMessageResult;
import org.mule.module.client.MuleClient;
import org.mule.module.client.RemoteDispatcher;
import org.mule.tck.FunctionalTestCase;

/**
 * @author David Dossot (david@dossot.net)
 */
public class RemoteTcpMuleClientFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "conf/remote-tcp-muleclient-config.xml";
    }

    public void testTickerLookup() throws Exception {
        final RemoteDispatcher remoteDispatcher =
                new MuleClient(false).getRemoteDispatcher("tcp://localhost:5555");

        final FutureMessageResult asyncResponse =
                remoteDispatcher.sendAsyncRemote("TickerLookupChannel", "GOOG",
                        null);

        // in a real application we would do something else and check for
        // availability of the response message from time to time
        final String response =
                asyncResponse.getMessage(10000).getPayloadAsString();

        assertNotNull(response);
        assertTrue(response.contains("Date,Open,High,Low,Close,Volume"));
    }
}