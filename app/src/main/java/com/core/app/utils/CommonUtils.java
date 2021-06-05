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

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;

import com.core.app.data.db.Topic;
import com.core.app.data.db.WordByTopic;
import com.core.app.data.model.SubTopicModel;
import com.core.app.data.wrapper.TopicWrapper;
import com.core.app.data.wrapper.WordWrapper;
import com.core.ssvapp.BuildConfig;
import com.core.ssvapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by CuongCK on 13/02/17.
 */

public final class CommonUtils {

    private static final String TAG = "CommonUtils";

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName) throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }

    public static ArrayList<TopicWrapper> convertToTopicWapper(ArrayList<Topic> topics, boolean hasAds) {
        ArrayList<TopicWrapper> topicWrappers = new ArrayList<>();

        for (int i = 0; i < topics.size(); i++) {
            if ((i + 2) % AppConstants.ADS_DELTA == 0 && hasAds) {
                TopicWrapper itemAds = new TopicWrapper(null, null, false);
                topicWrappers.add(itemAds);
            }

            TopicWrapper itemDat = new TopicWrapper(topics.get(i), null, true);
            topicWrappers.add(itemDat);
        }

        return topicWrappers;
    }

    public static ArrayList<WordWrapper> convertToWordWrapper(ArrayList<WordByTopic> wordByTopics) {
        ArrayList<WordWrapper> wordWrappers = new ArrayList<>();

        for (int i = 0; i < wordByTopics.size(); i++) {
            if ((i + 2) % AppConstants.ADS_DELTA == 0) {
                WordWrapper item = new WordWrapper(null, null, false);
                wordWrappers.add(item);
            }
            WordWrapper item = new WordWrapper(null, wordByTopics.get(i), true);
            wordWrappers.add(item);
        }

        return wordWrappers;
    }

    public static String getTopicLang(String lang, Topic topic) {
        switch (lang) {
            case AppConstants.LANG_CHI:
                return topic.getChin();
            case AppConstants.LANG_ESP:
                return topic.getEsp();
            case AppConstants.LANG_FRA:
                return topic.getFra();
            case AppConstants.LANG_GER:
                return topic.getGer();
            case AppConstants.LANG_HIN:
                return topic.getHin();
            case AppConstants.LANG_ITA:
                return topic.getIta();
            case AppConstants.LANG_JAP:
                return topic.getJap();
            case AppConstants.LANG_KOR:
                return topic.getKor();
            case AppConstants.LANG_POR:
                return topic.getPor();
            case AppConstants.LANG_RUS:
                return topic.getRus();
            case AppConstants.LANG_TUR:
                return topic.getTur();
            default:
                return "";
        }
    }

    public static String getSubTopicLang(String lang, SubTopicModel topic) {
        switch (lang) {
            case AppConstants.LANG_CHI:
                return topic.getChin();
            case AppConstants.LANG_ESP:
                return topic.getEsp();
            case AppConstants.LANG_FRA:
                return topic.getFra();
            case AppConstants.LANG_GER:
                return topic.getGer();
            case AppConstants.LANG_HIN:
                return topic.getHin();
            case AppConstants.LANG_ITA:
                return topic.getIta();
            case AppConstants.LANG_JAP:
                return topic.getJap();
            case AppConstants.LANG_KOR:
                return topic.getKor();
            case AppConstants.LANG_POR:
                return topic.getPor();
            case AppConstants.LANG_RUS:
                return topic.getRus();
            case AppConstants.LANG_TUR:
                return topic.getTur();

            default:
                return "";
        }
    }

    public static int getCourseName(int courseId) {
        switch (courseId) {
            case AppConstants.COURSE_LEARN:
                return R.string.bot_learn;
            case AppConstants.COURSE_FLASH:
                return R.string.bot_flashcard;
            case AppConstants.COURSE_TEST:
                return R.string.bot_test;
            case AppConstants.COURSE_WRITING:
                return R.string.bot_writing;
            case AppConstants.COURSE_LISTENING:
                return R.string.bot_listening;
            case AppConstants.COURSE_SPEAKING:
                return R.string.bot_speaking;
            case AppConstants.COURSE_TEST_2:
                return R.string.bot_test2;
            case AppConstants.COURSE_LISTENING_2:
                return R.string.bot_listening2;
            default:
                return R.string.bot_learn;
        }
    }

    public static String getWordLang(String lang, WordByTopic topic) {
        switch (lang) {
            case AppConstants.LANG_CHI:
                return topic.getChin();
            case AppConstants.LANG_ESP:
                return topic.getEsp();
            case AppConstants.LANG_FRA:
                return topic.getFra();
            case AppConstants.LANG_GER:
                return topic.getGer();
            case AppConstants.LANG_HIN:
                return topic.getHin();
            case AppConstants.LANG_ITA:
                return topic.getIta();
            case AppConstants.LANG_JAP:
                return topic.getJap();
            case AppConstants.LANG_KOR:
                return topic.getKor();
            case AppConstants.LANG_POR:
                return topic.getPor();
            case AppConstants.LANG_RUS:
                return topic.getRus();
            case AppConstants.LANG_TUR:
                return topic.getTur();
            default:
                return "";
        }
    }

    public static String getCommnet(Context context, int correctCount, int wrongCount) {
        int total = correctCount + wrongCount;
        int step = total / 4;

        if (correctCount < step) {
            return context.getString(R.string.not_good_at_all);
        } else if (correctCount < step * 2) {
            return context.getString(R.string.result_test_poor);
        } else if (correctCount < step * 3) {
            return context.getString(R.string.result_test_fairly);
        } else if (correctCount < step * 4) {
            return context.getString(R.string.result_test_good);
        } else {
            return context.getString(R.string.result_test_ex);
        }

    }

    //Check App is TOPACTIVITY
    public static boolean isTopActivity(Context context) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            assert am != null;
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

            if (taskInfo != null && taskInfo.size() > 0) {
                LogUtils.eTag(AppConstants.APP_TAG, "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClassName());
                ComponentName componentInfo = taskInfo.get(0).topActivity;

                String topAcPack = componentInfo.getPackageName();
                LogUtils.eTag(AppConstants.APP_TAG, "PackageName" + componentInfo.getPackageName());
                if (topAcPack.equalsIgnoreCase(BuildConfig.APPLICATION_ID)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
