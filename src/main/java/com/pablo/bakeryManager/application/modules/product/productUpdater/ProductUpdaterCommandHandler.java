package com.pablo.bakeryManager.application.modules.product.productUpdater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pablo.bakeryManager.application.exception.BadRequestExceptionHandler;
import com.pablo.bakeryManager.application.exception.NotFoundExceptionHandler;
import com.pablo.bakeryManager.application.modules.Id;
import com.pablo.bakeryManager.application.modules.product.ProductResponse;
import com.pablo.bakeryManager.application.modules.product.ProductResponseConverter;
import com.pablo.bakeryManager.application.serviceResponse.ResourceType;
import com.pablo.bakeryManager.application.serviceResponse.ServiceResponse;
import com.pablo.bakeryManager.application.serviceResponse.ServiceResponseBadRequest;
import com.pablo.bakeryManager.application.serviceResponse.ServiceResponseCreated;
import com.pablo.bakeryManager.application.serviceResponse.ServiceResponseNotFound;
import com.pablo.bakeryManager.application.serviceResponse.ServiceResponseServerError;
import com.pablo.bakeryManager.dominio.interfaces.CommandHandler;
import com.pablo.bakeryManager.dominio.models.Product;

@Component
public class ProductUpdaterCommandHandler implements CommandHandler<ProductUpdaterCommand, ServiceResponse> {
	
	@Autowired
	private ProductUpdater productUpdater;
	
	@Autowired
	private ProductResponseConverter productResponseConverter;
	
	@Override
	public ServiceResponse handle(ProductUpdaterCommand command) {
		
		try {
			
			Id id = new Id(command.idProduct);
			
			ProductUpdaterRequest request = ProductUpdaterRequest.create(command.request);
			
			Product product = productUpdater.update(id.value(), request);
			
			ProductResponse productResponse = productResponseConverter.convert(product);
			
			return new ServiceResponseCreated(ResourceType.PRODUCT, productResponse);
			
		} catch (BadRequestExceptionHandler e) {
			return new ServiceResponseBadRequest(ResourceType.PRODUCT, e.exceptionInfo());
			
		} catch (NotFoundExceptionHandler e) {
			return new ServiceResponseNotFound(ResourceType.PRODUCT, e.exceptionInfo());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ServiceResponseServerError();
	}
}
