package com.pinguela.yourpc.service;

import java.io.Serializable;
import java.util.List;

import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.ImageEntry;

public interface ImageFileService {
	
	String PRODUCT_TYPE = "product";
	String EMPLOYEE_TYPE = "employee";
	
	public List<String> getFilePaths(String type, Serializable pk) throws ServiceException;
	
	public List<ImageEntry> getFiles(String type, Serializable pk) throws ServiceException;
	
	public Integer add(String type, Serializable pk, ImageEntry imageFile) throws ServiceException;
	
	public Integer add(String type, Serializable pk, List<ImageEntry> imageFiles) throws ServiceException;
	
	public Boolean update(String type, Serializable pk, ImageEntry imageFile) throws ServiceException;
	
	public Boolean update(String type, Serializable pk, List<ImageEntry> imageFiles) throws ServiceException;
	
	public Boolean delete(String filePath) throws ServiceException;

}
