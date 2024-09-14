package com.pinguela.yourpc.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Objects;

public class ImageEntry
extends AbstractValueObject
implements Serializable, Comparable<ImageEntry> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2555817817842034894L;

	private BufferedImage image;
	private String path;
	private boolean isModified = false;
	
	public ImageEntry() {
	}
	
	public ImageEntry(String path) {
		this(null, path);
	}
	
	public ImageEntry(BufferedImage image, String path) {
		this.image = image;
		this.path = path;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		isModified = path != null;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public boolean isModified() {
		return isModified;
	}

	@Override
	public int hashCode() {
		return Objects.hash(path);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageEntry other = (ImageEntry) obj;
		return Objects.equals(path, other.path);
	}

	@Override
	public int compareTo(ImageEntry o) {
		if (this.path == null && o.getPath() == null) {
			return 0; 
		} 
		if (this.path == null) {
			return -1; 
		} 
		if (o.getPath() == null) {
			return 1; 
		} 
		return this.path.compareTo(o.getPath()); 
	}

}
