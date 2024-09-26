package com.pinguela.yourpc.model;

import java.util.Comparator;

import com.pinguela.yourpc.model.Address;

public class AddressComparator implements Comparator<Address> {

    @Override
    public int compare(Address a1, Address a2) {
        // First, compare by id
        int idComparison = Integer.compare(a1.getId(), a2.getId());
        if (idComparison != 0) {
            return idComparison; // Return if IDs are not equal
        }
        
        // If IDs are equal, compare by each field in order
        int customerComparison = compareNullable(a1.getCustomer(), a2.getCustomer());
        if (customerComparison != 0) {
            return customerComparison;
        }
        
        int employeeComparison = compareNullable(a1.getEmployee().getId(), a2.getEmployee());
        if (employeeComparison != 0) {
            return employeeComparison;
        }
        
        int streetNameComparison = a1.getStreetName().compareTo(a2.getStreetName());
        if (streetNameComparison != 0) {
            return streetNameComparison;
        }
        
        int streetNumberComparison = compareNullable(a1.getStreetNumber(), a2.getStreetNumber());
        if (streetNumberComparison != 0) {
            return streetNumberComparison;
        }
        
        int floorComparison = compareNullable(a1.getFloor(), a2.getFloor());
        if (floorComparison != 0) {
            return floorComparison;
        }
        
        int doorComparison = compareNullable(a1.getDoor(), a2.getDoor());
        if (doorComparison != 0) {
            return doorComparison;
        }
        
        int zipCodeComparison = a1.getZipCode().compareTo(a2.getZipCode());
        if (zipCodeComparison != 0) {
            return zipCodeComparison;
        }
        
        int cityComparison = Integer.compare(a1.getCity().getId(), a2.getCity().getId());
        if (cityComparison != 0) {
            return cityComparison;
        }
        
        int isDefaultComparison = Boolean.compare(a1.isDefault(), a2.isDefault());
        if (isDefaultComparison != 0) {
            return isDefaultComparison;
        }
        
        int isBillingComparison = Boolean.compare(a1.isBilling(), a2.isBilling());
        if (isBillingComparison != 0) {
            return isBillingComparison;
        }
        
        // Compare creation date
        int creationDateComparison = a1.getCreationDate().compareTo(a2.getCreationDate());
        if (creationDateComparison != 0) {
            return creationDateComparison;
        }

        // Finally, compare deletion date
        return compareNullable(a1.getDeletionDate(), a2.getDeletionDate());
    }

    private <T extends Comparable<T>> int compareNullable(T obj1, T obj2) {
        if (obj1 == null && obj2 == null) return 0;
        if (obj1 == null) return -1; // Null comes before non-null
        if (obj2 == null) return 1; // Non-null comes after null
        return obj1.compareTo(obj2);
    }
}
