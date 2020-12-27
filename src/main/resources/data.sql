INSERT INTO LOCATIONS ( id, location_identifier, description, creation_time, modification_time) values
(1, 'REGION%5E239', 'Buckingham', current_timestamp, current_timestamp);

INSERT INTO SEARCH_CRITERIA (id, creation_time, modification_time, area_size_unit, channel, currency_code, index, is_fetching, max_price, min_bedrooms, number_of_properties_per_page, radius, sort_type, view_type, location_id)
values (1, current_timestamp, current_timestamp, 'sqft', 'RENT', 'GBP', '0', 'false', '25000', '3', '24', '0.0', '6', 'LIST', 1);

INSERT INTO LETTINGS_SEARCH_CRITERIA (id, include_let_agreed)
values (1, true);

-- Create properties
INSERT INTO PROPERTIES (ID, CREATION_TIME, MODIFICATION_TIME, AMOUNT, BEDROOMS, CHANNEL, DAYS_ON_MARKET, DISPLAY_ADDRESS, DISPLAY_PRICE, DISPLAY_STATUS, FIRST_VISIBLE_DATE, LAST_PROPERTY_UPDATE_RECEIVED, MAIN_MAP_IMAGE_SRC, OFF_MARKET_DATE, ON_MARKET, PROPERTY_ID, PROPERTY_SUB_TYPE, PROPERTY_TYPE, PROPERTY_URL, REGISTERED, SUMMARY)
values (800001, current_timestamp, current_timestamp, '20000', '3', 'RENT', '10', 'Buckingham', '£20,000', '', TO_DATE('2020-06-30','yyyy-MM-dd'), TO_DATE('2020-10-30','yyyy-MM-dd'), '',TO_DATE('2020-10-30','yyyy-MM-dd'),'false', '1111', '', '', '', true, '');

INSERT INTO PROPERTIES (ID, CREATION_TIME, MODIFICATION_TIME, AMOUNT, BEDROOMS, CHANNEL, DAYS_ON_MARKET, DISPLAY_ADDRESS, DISPLAY_PRICE, DISPLAY_STATUS, FIRST_VISIBLE_DATE, LAST_PROPERTY_UPDATE_RECEIVED, MAIN_MAP_IMAGE_SRC, OFF_MARKET_DATE, ON_MARKET, PROPERTY_ID, PROPERTY_SUB_TYPE, PROPERTY_TYPE, PROPERTY_URL, REGISTERED, SUMMARY)
values (800002, current_timestamp, current_timestamp, '40000', '4', 'RENT', '10', 'Buckingham', '£200,000', '', TO_DATE('2020-06-30','yyyy-MM-dd'), TO_DATE('2020-10-30','yyyy-MM-dd'), '',TO_DATE('2020-10-30','yyyy-MM-dd'),'false', '2222', '', '', '', true, '');

-- Add properties update
INSERT INTO PROPERTY_UPDATE_RECORD_MODEL (id, creation_time, modification_time, field, new_value, old_value, property_id)
values (900001, current_timestamp, current_timestamp, 'displayStatus', 'Let Agreed', '', 800001 );

INSERT INTO PROPERTY_UPDATE_RECORD_MODEL (id, creation_time, modification_time, field, new_value, old_value, property_id)
values (900002, current_timestamp, current_timestamp, 'displayStatus', 'Let Agreed', '', 800002 );
