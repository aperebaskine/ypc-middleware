package com.pinguela.yourpc.dao.transformer;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;

import com.pinguela.yourpc.dao.impl.TableDefinition;
import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.model.ProductDTO;

public class ProductTransformer 
implements TupleTransformer<ProductDTO>, ResultListTransformer<ProductDTO> {

	private Map<Long, ProductDTO> productMap;
	private AttributeTransformer attributeTransformer;

	public ProductTransformer() {
		productMap = new LinkedHashMap<>();
		attributeTransformer = new AttributeTransformer();
	}

	@Override
	public ProductDTO transformTuple(Object[] tuple, String[] aliases) {

		Long id = (Long) tuple[0];
		
		ProductDTO dto = productMap.computeIfAbsent(id, idKey -> {
			int index = 1;
			ProductDTO newDto = new ProductDTO();
			newDto.setId(id);
			newDto.setName((String) tuple[index++]);
			newDto.setCategoryId((Short) tuple[index++]);
			newDto.setDescription((String) tuple[index++]);
			newDto.setLaunchDate((Date) tuple[index++]);
			newDto.setDiscontinuationDate((Date) tuple[index++]);
			newDto.setStock((Integer) tuple[index++]);
			newDto.setPurchasePrice((Double) tuple[index++]);
			newDto.setSalePrice((Double) tuple[index++]);
			newDto.setReplacementId((Long) tuple[index++]);
			newDto.setReplacementName((String) tuple[index++]);

			return newDto;
		});
		
		Attribute<?> attribute = attributeTransformer.transformTuple(
				TableDefinition.PRODUCT_COLUMNS.size(), tuple, aliases);
		
		if (attribute != null) {
			dto.getAttributes().putIfAbsent(attribute.getName(), attribute);
		}
		
		return dto;
	}
	
	@Override
	public List<ProductDTO> transformList(List<ProductDTO> resultList) {
		return new ArrayList<>(productMap.values());
	}

}
