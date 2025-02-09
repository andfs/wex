package com.wex.mapper;

import com.wex.entity.Purchase;
import com.wex.vo.request.PurchaseRequestVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    Purchase toPurchase(PurchaseRequestVO purchaseRequestVO);
}
