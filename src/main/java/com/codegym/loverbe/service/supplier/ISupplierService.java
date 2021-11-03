package com.codegym.loverbe.service.supplier;

import com.codegym.loverbe.model.Supplier;
import com.codegym.loverbe.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISupplierService extends IService<Supplier>{
    List<Supplier> top6();
}
