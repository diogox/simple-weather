package com.diogox.simpleweather;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

import java.util.Set;
import java.util.concurrent.TimeUnit;

class AlertJob extends Job {
    public static final String TAG = "alert_job";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        // TODO: Check for alert triggers
        Log.d(TAG, "SUCCESS!!!");

        PersistableBundleCompat extras = params.getExtras();

        // TODO: Get alerts

        return Result.SUCCESS;
    }

    public static void scheduleJob() {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(AlertJob.TAG);
        if (!jobRequests.isEmpty()) {
            return;
        }

        PersistableBundleCompat extras = new PersistableBundleCompat();

        // TODO: Set alerts
        //extras.putString("key", "Hello world");

        new JobRequest.Builder(AlertJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(30), TimeUnit.MINUTES.toMillis(7))
                .setUpdateCurrent(true) // calls cancelAllForTag(NoteSyncJob.TAG) for you
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .build()
                .schedule();
    }
}
