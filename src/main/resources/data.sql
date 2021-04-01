INSERT INTO LOCATIONS ( id, location_identifier, description, creation_time, modification_time) values
(1, 'REGION%5E239', 'Buckingham', current_timestamp, current_timestamp);

INSERT INTO SEARCH_CRITERIA (id, creation_time, modification_time, area_size_unit, channel, currency_code, index, is_fetching, max_price, min_bedrooms, number_of_properties_per_page, radius, sort_type, view_type, location_id)
values (1, current_timestamp, current_timestamp, 'sqft', 'RENT', 'GBP', '0', 'false', '25000', '3', '24', '0.0', '6', 'LIST', 1);

INSERT INTO SEARCH_CRITERIA (id, creation_time, modification_time, area_size_unit, channel, currency_code, index, is_fetching, max_price, min_bedrooms, number_of_properties_per_page, radius, sort_type, view_type, location_id)
values (2, current_timestamp, current_timestamp, 'sqft', 'BUY', 'GBP', '0', 'false', '10000000', '3', '24', '0.0', '6', 'LIST', 1);

INSERT INTO LETTINGS_SEARCH_CRITERIA (id, include_let_agreed)
values (1, true);

INSERT INTO SALES_SEARCH_CRITERIA (id, includesstc)
values (2, true);

-- Create properties for let
INSERT INTO PROPERTIES (ID, CREATION_TIME, MODIFICATION_TIME, AMOUNT, BEDROOMS, CHANNEL, DAYS_ON_MARKET, DISPLAY_ADDRESS, DISPLAY_PRICE, DISPLAY_STATUS, FIRST_VISIBLE_DATE, LAST_PROPERTY_UPDATE_RECEIVED, MAIN_MAP_IMAGE_SRC, OFF_MARKET_DATE, ON_MARKET, PROPERTY_ID, PROPERTY_SUB_TYPE, PROPERTY_TYPE, PROPERTY_URL, REGISTERED, SUMMARY)
values (800001, current_timestamp, current_timestamp, '20000', '3', 'RENT', '10', 'Buckingham', '£20,000', 'Let agreed', CURRENT_DATE - 20, CURRENT_DATE - 10, '',CURRENT_DATE - 10,'false', '800001', '', '', '', true, '');

INSERT INTO PROPERTIES (ID, CREATION_TIME, MODIFICATION_TIME, AMOUNT, BEDROOMS, CHANNEL, DAYS_ON_MARKET, DISPLAY_ADDRESS, DISPLAY_PRICE, DISPLAY_STATUS, FIRST_VISIBLE_DATE, LAST_PROPERTY_UPDATE_RECEIVED, MAIN_MAP_IMAGE_SRC, OFF_MARKET_DATE, ON_MARKET, PROPERTY_ID, PROPERTY_SUB_TYPE, PROPERTY_TYPE, PROPERTY_URL, REGISTERED, SUMMARY)
values (800002, current_timestamp, current_timestamp, '40000', '4', 'RENT', '10', 'Buckingham', '£200,000', 'Let agreed', CURRENT_DATE - 22, CURRENT_DATE - 12, '',CURRENT_DATE - 12,'false', '800002', '', '', '', true, '');

-- Create properties for sale

INSERT INTO PROPERTIES (ID, CREATION_TIME, MODIFICATION_TIME, AMOUNT, BEDROOMS, CHANNEL, DAYS_ON_MARKET, DISPLAY_ADDRESS, DISPLAY_PRICE, DISPLAY_STATUS, FIRST_VISIBLE_DATE, LAST_PROPERTY_UPDATE_RECEIVED, MAIN_MAP_IMAGE_SRC, OFF_MARKET_DATE, ON_MARKET, PROPERTY_ID, PROPERTY_SUB_TYPE, PROPERTY_TYPE, PROPERTY_URL, REGISTERED, SUMMARY)
values (800003, current_timestamp, current_timestamp, '585000', '4', 'BUY', '2', 'Woodlands Crescent, Buckingham', '£585,000', 'Sold STC', CURRENT_DATE - 5, CURRENT_DATE - 1, '',CURRENT_DATE - 1,'false', '800003', 'Detached', '', '', true, '');

INSERT INTO PROPERTIES (ID, CREATION_TIME, MODIFICATION_TIME, AMOUNT, BEDROOMS, CHANNEL, DAYS_ON_MARKET, DISPLAY_ADDRESS, DISPLAY_PRICE, DISPLAY_STATUS, FIRST_VISIBLE_DATE, LAST_PROPERTY_UPDATE_RECEIVED, MAIN_MAP_IMAGE_SRC, OFF_MARKET_DATE, ON_MARKET, PROPERTY_ID, PROPERTY_SUB_TYPE, PROPERTY_TYPE, PROPERTY_URL, REGISTERED, SUMMARY)
values (800004, current_timestamp, current_timestamp, '350000', '4', 'BUY', '2', 'Cecil''s Yard, Buckingham', '£350,000', 'Sold STC', CURRENT_DATE - 6, CURRENT_DATE - 2, '',CURRENT_DATE - 2,'false', '800004', 'Detached', '', '', true, '');

-- Link location and properties
INSERT INTO LOCATIONS_PROPERTIES(LOCATION_MODEL_ID,PROPERTIES_ID)
VALUES (1, 800001);
INSERT INTO LOCATIONS_PROPERTIES(LOCATION_MODEL_ID,PROPERTIES_ID)
VALUES (1, 800002);
INSERT INTO LOCATIONS_PROPERTIES(LOCATION_MODEL_ID,PROPERTIES_ID)
VALUES (1, 800003);
INSERT INTO LOCATIONS_PROPERTIES(LOCATION_MODEL_ID,PROPERTIES_ID)
VALUES (1, 800004);


-- Add properties update
INSERT INTO PROPERTY_UPDATE_RECORD_MODEL (id, creation_time, modification_time, field, new_value, old_value, property_id)
values (900001, current_timestamp, current_timestamp, 'displayStatus', 'Let Agreed', '', 800001 );

INSERT INTO PROPERTY_UPDATE_RECORD_MODEL (id, creation_time, modification_time, field, new_value, old_value, property_id)
values (900002, current_timestamp, current_timestamp, 'displayStatus', 'Let Agreed', '', 800002 );

INSERT INTO PROPERTY_UPDATE_RECORD_MODEL (id, creation_time, modification_time, field, new_value, old_value, property_id)
values (900003, current_timestamp, current_timestamp, 'displayStatus', 'Sold STC', '', 800003 );

INSERT INTO PROPERTY_UPDATE_RECORD_MODEL (id, creation_time, modification_time, field, new_value, old_value, property_id)
values (900004, current_timestamp, current_timestamp, 'displayStatus', 'Sold STC', '', 800004 );
