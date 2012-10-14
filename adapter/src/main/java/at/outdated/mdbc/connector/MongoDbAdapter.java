/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.outdated.mdbc.connector;

import javax.resource.ResourceException;
import javax.resource.spi.*;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

/**
 *
 * @author ebirn
 */

@Connector(reauthenticationSupport=false,transactionSupport=TransactionSupport.TransactionSupportLevel.NoTransaction)
public class MongoDbAdapter implements ResourceAdapter {

    private BootstrapContext bootstrapContext;
    
    @Override
    public XAResource[] getXAResources(ActivationSpec[] ass) throws ResourceException {
        return null;
    }

    @Override
    public void endpointActivation(MessageEndpointFactory mef, ActivationSpec as) throws ResourceException {
    
    }

    @Override
    public void endpointDeactivation(MessageEndpointFactory mef, ActivationSpec as) {
    
    }

    @Override
    public void start(BootstrapContext bc) throws ResourceAdapterInternalException {
        this.bootstrapContext = bc;
    }

    public BootstrapContext getBootstrapContext() {
        return bootstrapContext;
    }

    
    @Override
    public void stop() {
    
    }
    
    
    
}
