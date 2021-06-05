/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.core.app.utils;

/**
 * Created by amitshekhar on 08/01/17.
 */

public final class AppConstants {

    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String STATUS_CODE_FAILED = "failed";

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "ssv_android_app.db";
    public static final String PREF_NAME = "ssv_android_app";

    public static final long NULL_INDEX = -1L;

    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";
    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final String TAG = "ImoLog";
    public static final int ADS_DELTA = 4;

    public static final String LANG_CHI = "chin";
    public static final String LANG_ESP = "esp";
    public static final String LANG_FRA = "fra";
    public static final String LANG_GER = "ger";
    public static final String LANG_HIN = "hin";
    public static final String LANG_ITA = "ita";
    public static final String LANG_JAP = "jap";
    public static final String LANG_KOR = "kor";
    public static final String LANG_POR = "por";
    public static final String LANG_RUS = "rus";
    public static final String LANG_TUR = "tur";
    public static final String LANG_EN = "en";

    public static final String EXTRA_TOPIC_ID = "EXTRA_TOPIC_ID";
    public static final String EXTRA_TOPIC_TITLE = "EXTRA_TOPIC_TITLE";
    public static final String EXTRA_COURSE_TYPE = "EXTRA_COURSE_TYPE";
    public static final String EXTRA_SUBTOPIC_ID = "EXTRA_SUBTOPIC_ID";

    public static final int COURSE_LEARN = 1;
    public static final int COURSE_FLASH = 2;
    public static final int COURSE_TEST = 3;
    public static final int COURSE_WRITING = 4;
    public static final int COURSE_LISTENING = 5;
    public static final int COURSE_SPEAKING = 6;
    public static final int COURSE_TEST_2 = 7;
    public static final int COURSE_LISTENING_2 = 8;

    public static final int REQUEST_PURCHASE = 1234;
    public static final int OPEN_APP_PERIOD = 5;

    public static boolean IS_REMOVE_ADS;
    public static String APP_TAG = "VOC";

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
