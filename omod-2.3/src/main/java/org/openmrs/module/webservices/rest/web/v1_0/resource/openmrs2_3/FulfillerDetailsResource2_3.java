/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs2_3;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.StringProperty;
import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.docs.swagger.core.property.EnumProperty;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.annotation.SubResource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubResource;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs2_2.OrderResource2_2;

/**
 * {@link Resource} for Order, supporting standard CRUD operations on fulfiller_comment and
 * fulfiller_status and accession number (supplants FulfillerDetails2_2 which only supported
 * updating fulfiller_comment and fulfiller_status)
 */
@SubResource(parent = OrderResource2_2.class, path = "fulfillerdetails", supportedClass = FulfillerDetails2_3.class, supportedOpenmrsVersions = {
        "2.3.*", "2.4.*", "2.5.*", "2.6.*" })
public class FulfillerDetailsResource2_3 extends DelegatingSubResource<FulfillerDetails2_3, Order, OrderResource2_2> {

    @Override
    public FulfillerDetails2_3 newDelegate() {
        return new FulfillerDetails2_3();
    }

    @Override
    public FulfillerDetails2_3 save(FulfillerDetails2_3 delegate) {
        Context.getOrderService().updateOrderFulfillerStatus(delegate.getOrder(), delegate.getFulfillerStatus(),
                delegate.getFulfillerComment(), delegate.getAccessionNumber());
        return null;
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription delegatingResourceDescription = new DelegatingResourceDescription();
        delegatingResourceDescription.addProperty("fulfillerStatus");
        delegatingResourceDescription.addProperty("fulfillerComment");
        delegatingResourceDescription.addProperty("accessionNumber");
        return delegatingResourceDescription;
    }

    @Override
    public Model getCREATEModel(Representation rep) {
        return new ModelImpl()
                .property("fulfillerComment", new StringProperty())
                .property("fulfillerStatus", new EnumProperty(Order.FulfillerStatus.class))
                .property("accessionNumber", new StringProperty());
    }

    @Override
    public Order getParent(FulfillerDetails2_3 instance) {
        return instance.getOrder();
    }

    @Override
    public void setParent(FulfillerDetails2_3 instance, Order parent) {
        instance.setOrder(parent);
    }

    @Override
    public PageableResult doGetAll(Order parent, RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public FulfillerDetails2_3 getByUniqueId(String uniqueId) {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    protected void delete(FulfillerDetails2_3 delegate, String reason, RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public void purge(FulfillerDetails2_3 delegate, RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() {
        return getRepresentationDescription(null);
    }

}
