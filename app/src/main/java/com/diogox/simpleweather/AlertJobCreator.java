package com.diogox.simpleweather;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class AlertJobCreator implements JobCreator {

    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        switch (tag) {
            case AlertJob.TAG:
                return new AlertJob();

            default:
                return null;
        }
    }
}
