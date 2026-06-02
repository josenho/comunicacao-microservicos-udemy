package br.com.curso_udemy.product_api.modules.supplier.service;

import br.com.curso_udemy.product_api.config.exception.ValidationException;
import br.com.curso_udemy.product_api.modules.supplier.dto.SupplierRequest;
import br.com.curso_udemy.product_api.modules.supplier.dto.SupplierResponse;
import br.com.curso_udemy.product_api.modules.supplier.model.Supplier;
import br.com.curso_udemy.product_api.modules.supplier.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<SupplierResponse> findAll() {
        return supplierRepository
                .findAll()
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public List<SupplierResponse> findByName(String name) {
        if (isEmpty(name)){
            throw new ValidationException("The supplier name must be informed.");
        }
        return supplierRepository
                .findByNameIgnoreCaseContaining(name)
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public SupplierResponse findByIdResponse(Integer id){
        return SupplierResponse.of(findById(id));
    }

    public Supplier findById(Integer id){
        if (isEmpty(id)){
            throw new ValidationException("The supplier ID must be informed.");
        }
        return supplierRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There's no supplier for the given ID."));
    }

    public SupplierResponse save(SupplierRequest request){
        ValidateSupplierNameInformed(request);
        var supplier = supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }

    private void ValidateSupplierNameInformed (SupplierRequest request){
        if(isEmpty(request.getName())) {
            throw new ValidationException("The supplier's name description was not informed.");
        }
    }
}
