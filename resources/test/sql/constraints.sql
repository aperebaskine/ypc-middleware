ALTER TABLE ATTRIBUTE_VALUE
ADD CONSTRAINT SINGLE_VALUE_TYPE
CHECK (
	(VALUE_VARCHAR IS NOT NULL AND VALUE_BIGINT IS NULL AND VALUE_DECIMAL IS NULL AND VALUE_BOOLEAN IS NULL)
    OR
    (VALUE_VARCHAR IS NULL AND VALUE_BIGINT IS NOT NULL AND VALUE_DECIMAL IS NULL AND VALUE_BOOLEAN IS NULL)
    OR
    (VALUE_VARCHAR IS NULL AND VALUE_BIGINT IS NULL AND VALUE_DECIMAL IS NOT NULL AND VALUE_BOOLEAN IS NULL)
    OR
    (VALUE_VARCHAR IS NULL AND VALUE_BIGINT IS NULL AND VALUE_DECIMAL IS NULL AND VALUE_BOOLEAN IS NOT NULL)
);

ALTER TABLE ATTRIBUTE_VALUE 
ADD CONSTRAINT UNIQUE_ATTRIBUTE_VALUE_VCHAR 
UNIQUE (ATTRIBUTE_TYPE_ID, VALUE_VARCHAR);

ALTER TABLE ATTRIBUTE_VALUE 
ADD CONSTRAINT UNIQUE_ATTRIBUTE_VALUE_BIGINT 
UNIQUE (ATTRIBUTE_TYPE_ID, VALUE_BIGINT);

ALTER TABLE ATTRIBUTE_VALUE 
ADD CONSTRAINT UNIQUE_ATTRIBUTE_VALUE_DECIMAL 
UNIQUE (ATTRIBUTE_TYPE_ID, VALUE_DECIMAL);

ALTER TABLE ATTRIBUTE_VALUE 
ADD CONSTRAINT UNIQUE_ATTRIBUTE_VALUE_BOOLEAN 
UNIQUE (ATTRIBUTE_TYPE_ID, VALUE_BOOLEAN);

ALTER TABLE EMPLOYEE 
ADD CONSTRAINT EMPLOYEE_EMAIL_PATTERN
CHECK (EMAIL LIKE '%@%.%');

ALTER TABLE CUSTOMER 
ADD CONSTRAINT CUSTOMER_EMAIL_PATTERN
CHECK (EMAIL LIKE '%@%.%');

ALTER TABLE ADDRESS 
ADD CONSTRAINT ADDRESS_HAS_OWNER
CHECK (
	(EMPLOYEE_ID IS NULL AND CUSTOMER_ID IS NOT NULL AND IS_DEFAULT IS NOT NULL AND IS_BILLING IS NOT NULL)
    OR
    (EMPLOYEE_ID IS NOT NULL AND CUSTOMER_ID IS NULL)
);

ALTER TABLE TICKET_MESSAGE 
ADD CONSTRAINT TICKET_MESSAGE_HAS_SENDER
CHECK (
	(EMPLOYEE_ID IS NULL AND CUSTOMER_ID IS NOT NULL)
    OR
    (EMPLOYEE_ID IS NOT NULL AND CUSTOMER_ID IS NULL)
);