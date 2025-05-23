package com.technokratos.kirillakhmetov.util.db;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlParameters {
    public static final class Finance {
        public static final String OWNER_ID = "p_owner_id";
        public static final String TYPE = "p_type";
        public static final String AMOUNT = "p_amount";
        public static final String CATEGORY = "p_category";
        public static final String DATE = "p_date";
    }
}